package com.demo.textract.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.auth.policy.actions.SQSActions;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DocumentLocation;
import com.amazonaws.services.textract.model.DocumentMetadata;
import com.amazonaws.services.textract.model.GetDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.GetDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.GetDocumentTextDetectionResult;
import com.amazonaws.services.textract.model.NotificationChannel;
import com.amazonaws.services.textract.model.Relationship;
import com.amazonaws.services.textract.model.S3Object;
import com.amazonaws.services.textract.model.StartDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.StartDocumentAnalysisResult;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionResult;
import com.demo.textract.constant.TextractConstant;
import com.demo.textract.model.PDFProcessorDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PDFDocumentProcessor {


	private final static Logger LOGGER = LoggerFactory.getLogger(PDFDocumentProcessor.class);

	public enum ProcessType {
		DETECTION,ANALYSIS
	}

	public Map<Integer, Map<Integer, String>> processTable(String document) {
		try {
			LOGGER.info("started pdf processing");
			PDFProcessorDto pdfProcessorDto = new PDFProcessorDto();
			pdfProcessorDto.setRoleArn("arn:aws:iam::182255387291:role/TextractRole");
			pdfProcessorDto.setBucket("textract-console-ap-southeast-1-c23f1eee-3e73-4841-84ff-48d5828");
			pdfProcessorDto.setDocument(document);

			BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJLFBFRUVHNEMP6FQ", "r65fZJ4ItYTDp1Ro+sYMv+4kRl3f7qzPYJZ6QeiO");
		
			EndpointConfiguration snsEndpoint = new EndpointConfiguration(TextractConstant.SNSURL, TextractConstant.REGION);
			pdfProcessorDto.setSns(AmazonSNSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withEndpointConfiguration(snsEndpoint).build());

			LOGGER.debug("sns intialized");

			EndpointConfiguration sqsEndpoint = new EndpointConfiguration(TextractConstant.SQSURL,TextractConstant.REGION);
			pdfProcessorDto.setSqs(AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withEndpointConfiguration(sqsEndpoint).build());

			LOGGER.debug("sqs intialized");

			EndpointConfiguration endpoint = new EndpointConfiguration(TextractConstant.TEXTRACTURL,TextractConstant.REGION);
			pdfProcessorDto.setTextract(AmazonTextractClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withEndpointConfiguration(endpoint).build());

			LOGGER.debug("textract intialized");

			createTopicandQueue(pdfProcessorDto);
			Map<Integer, Map<Integer, String>> data = ProcessDocument(pdfProcessorDto,ProcessType.ANALYSIS);
			//DeleteTopicandQueue();
			LOGGER.debug("Done!!");
			return data;
		}catch (Exception e) {
			LOGGER.error("Error in process : {}",e);
			return null;
		}
	}

	private void createTopicandQueue(PDFProcessorDto pdfProcessorDto) {

		//create a new SNS topic
		/*pdfProcessorDto.setSnsTopicName("AmazonTextractTopic" + Long.toString(System.currentTimeMillis()));
		CreateTopicRequest createTopicRequest = new CreateTopicRequest(pdfProcessorDto.getSnsTopicName());
		CreateTopicResult createTopicResult = pdfProcessorDto.getSns().createTopic(createTopicRequest);
		pdfProcessorDto.setSnsTopicArn(createTopicResult.getTopicArn());*/

		pdfProcessorDto.setSnsTopicArn("arn:aws:sns:ap-southeast-1:182255387291:AmazonTextractTopic1578563856241");

		//Create a new SQS Queue
		/*pdfProcessorDto.setSqsQueueName("AmazonTextractQueue" + Long.toString(System.currentTimeMillis()));
  		final CreateQueueRequest createQueueRequest = new CreateQueueRequest(pdfProcessorDto.getSqsQueueName());
  		pdfProcessorDto.setSqsQueueUrl(pdfProcessorDto.getSqs().createQueue(createQueueRequest).getQueueUrl());*/

		/*String sqsQueueArn = pdfProcessorDto.getSqs().getQueueAttributes(pdfProcessorDto.getSqsQueueUrl(), Arrays.asList("QueueArn")).getAttributes().get("QueueArn");
		LOGGER.debug("SQS queue ARN: {} ",sqsQueueArn);
		pdfProcessorDto.setSqsQueueArn(sqsQueueArn);*/
		pdfProcessorDto.setSqsQueueUrl("https://sqs.ap-southeast-1.amazonaws.com/182255387291/AmazonTextractQueue1578563873563");
		pdfProcessorDto.setSqsQueueArn("arn:aws:sqs:ap-southeast-1:182255387291:AmazonTextractQueue1578563873563");

		//Subscribe SQS queue to SNS topic
		String sqsSubscriptionArn = pdfProcessorDto.getSns().subscribe(pdfProcessorDto.getSnsTopicArn(), "sqs",pdfProcessorDto.getSqsQueueArn()).getSubscriptionArn();

		// Authorize queue
		Policy policy = new Policy().withStatements(
				new Statement(Effect.Allow)
				.withPrincipals(Principal.AllUsers)
				.withActions(SQSActions.SendMessage)
				.withResources(new Resource(pdfProcessorDto.getSqsQueueArn()))
				.withConditions(new Condition().withType("ArnEquals").withConditionKey("aws:SourceArn").withValues(pdfProcessorDto.getSnsTopicArn()))
				);

		Map queueAttributes = new HashMap();
		queueAttributes.put(QueueAttributeName.Policy.toString(), policy.toJson());
		pdfProcessorDto.getSqs().setQueueAttributes(new SetQueueAttributesRequest(pdfProcessorDto.getSqsQueueUrl(), queueAttributes)); 

		LOGGER.debug("Topic arn: {}",pdfProcessorDto.getSnsTopicArn());
		LOGGER.debug("Queue arn: {}",pdfProcessorDto.getSqsQueueArn());
		LOGGER.debug("Queue url: {}",pdfProcessorDto.getSqsQueueUrl());
		LOGGER.debug("Queue sub arn: {}",sqsSubscriptionArn);

	}


	private Map<Integer, Map<Integer, String>> ProcessDocument(PDFProcessorDto pdfProcessorDto,ProcessType type) throws Exception
	{
		Map<Integer, Map<Integer, String>> data = null;
		switch(type)
		{
		case DETECTION:
			StartDocumentTextDetection(pdfProcessorDto);
			LOGGER.info("Processing type: Detection");
			break;
		case ANALYSIS:
			StartDocumentAnalysis(pdfProcessorDto);
			LOGGER.info("Processing type: Analysis");
			break;
		default:
			LOGGER.info("Invalid processing type. Choose Detection or Analysis");
			throw new Exception("Invalid processing type");

		}

		LOGGER.info("Waiting for job: {}",pdfProcessorDto.getJobId());
		//Poll queue for messages
		List<Message> messages=null;
		int dotLine=0;
		boolean jobFound=false;

		//loop until the job status is published. Ignore other messages in queue.
		do{
			messages = pdfProcessorDto.getSqs().receiveMessage(pdfProcessorDto.getSqsQueueUrl()).getMessages();
			if (dotLine++<40){
				System.out.print(".");
			}else{
				System.out.println();
				dotLine=0;
			}

			if (!messages.isEmpty()) {
				//Loop through messages received.
				for (Message message: messages) {
					String notification = message.getBody();
					LOGGER.debug("notification message : {}",notification);
					// Get status and job id from notification.
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonMessageTree = mapper.readTree(notification);
					LOGGER.debug("/// : {}",jsonMessageTree);
					JsonNode messageBodyText = jsonMessageTree.get("Message");
					ObjectMapper operationResultMapper = new ObjectMapper();
					JsonNode jsonResultTree = operationResultMapper.readTree(messageBodyText.textValue());
					JsonNode operationJobId = jsonResultTree.get("JobId");
					JsonNode operationStatus = jsonResultTree.get("Status");
					LOGGER.debug("Job found was : {}",operationJobId);
					// Found job. Get the results and display.
					if(operationJobId.asText().equals(pdfProcessorDto.getJobId())){
						jobFound=true;
						LOGGER.debug("Job id: {}",operationJobId);
						LOGGER.debug("Status : {}",operationStatus.toString());

						if (operationStatus.asText().equals("SUCCEEDED")){
							switch(type)
							{
							case DETECTION:
								GetDocumentTextDetectionResults(pdfProcessorDto);
								break;
							case ANALYSIS:
								data = GetDocumentAnalysisResults(pdfProcessorDto);
								break;
							default:
								LOGGER.debug("Invalid processing type. Choose Detection or Analysis");
								throw new Exception("Invalid processing type");

							}
						}
						else{
							LOGGER.debug("Analysis failed");
						}

						pdfProcessorDto.getSqs().deleteMessage(pdfProcessorDto.getSqsQueueUrl(),message.getReceiptHandle());
					}

					else{
						LOGGER.debug("Job received was not job : {}",pdfProcessorDto.getJobId());
						//Delete unknown message. Consider moving message to dead letter queue
						pdfProcessorDto.getSqs().deleteMessage(pdfProcessorDto.getSqsQueueUrl(),message.getReceiptHandle());
					}
				}
			}
			else {
				Thread.sleep(5000);
			}
		} while (!jobFound);
		LOGGER.debug("Finished processing document");
		return data;
	}


	private void StartDocumentAnalysis(PDFProcessorDto pdfProcessorDto) throws Exception{
		//Create notification channel 
		NotificationChannel channel= new NotificationChannel()
				.withSNSTopicArn(pdfProcessorDto.getSnsTopicArn())
				.withRoleArn(pdfProcessorDto.getRoleArn());

		StartDocumentAnalysisRequest req = new StartDocumentAnalysisRequest()
				.withFeatureTypes("TABLES")
				.withDocumentLocation(new DocumentLocation()
						.withS3Object(new S3Object()
								.withBucket(pdfProcessorDto.getBucket())
								.withName(pdfProcessorDto.getDocument())))
				.withJobTag("AnalyzingText")
				.withNotificationChannel(channel);

		StartDocumentAnalysisResult startDocumentAnalysisResult = pdfProcessorDto.getTextract().startDocumentAnalysis(req);
		pdfProcessorDto.setJobId(startDocumentAnalysisResult.getJobId());
		LOGGER.info("startJobId : {} ",pdfProcessorDto.getJobId());
	}

	private void StartDocumentTextDetection(PDFProcessorDto pdfProcessorDto) throws Exception{

		try {
			//Create notification channel 
			NotificationChannel channel= new NotificationChannel()
					.withSNSTopicArn(pdfProcessorDto.getSnsTopicArn())
					.withRoleArn(pdfProcessorDto.getRoleArn());

			StartDocumentTextDetectionRequest req = new StartDocumentTextDetectionRequest()
					.withDocumentLocation(new DocumentLocation()
							.withS3Object(new S3Object()
									.withBucket(pdfProcessorDto.getBucket())
									.withName(pdfProcessorDto.getDocument())))
					.withJobTag("DetectingText")
					.withNotificationChannel(channel);

			StartDocumentTextDetectionResult startDocumentTextDetectionResult = pdfProcessorDto.getTextract().startDocumentTextDetection(req);
			pdfProcessorDto.setJobId(startDocumentTextDetectionResult.getJobId());
			LOGGER.info("startJobId : {} ",pdfProcessorDto.getJobId());

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<Integer, Map<Integer, String>> GetDocumentAnalysisResults(PDFProcessorDto pdfProcessorDto) throws Exception{

		int maxResults=1000;
		String paginationToken=null;
		GetDocumentAnalysisResult response=null;
		Boolean finished=false;

		//loops until pagination token is null
		Map<String, Block> tableBlock = new LinkedHashMap<>();
		Map<String, Block> otherBlock = new LinkedHashMap<>();
		List<Integer> mergeList=new ArrayList<>();
		while (finished==false)
		{
			GetDocumentAnalysisRequest documentAnalysisRequest= new GetDocumentAnalysisRequest()
					.withJobId(pdfProcessorDto.getJobId())
					.withMaxResults(maxResults)
					.withNextToken(paginationToken);

			response = pdfProcessorDto.getTextract().getDocumentAnalysis(documentAnalysisRequest);
			LOGGER.debug("Document Analyses Result : {}",response);
			DocumentMetadata documentMetaData=response.getDocumentMetadata();

			LOGGER.debug("Pages: {} ",documentMetaData.getPages().toString());

			//Show blocks, confidence and detection times
			List<Block> blocks= response.getBlocks();
			generateBlockMap(blocks,tableBlock,otherBlock);
			paginationToken=response.getNextToken();
			if (paginationToken==null)
				finished=true;
		}
		Map<Integer, Map<Integer, String>> data = prepareFinalMap(tableBlock,otherBlock,mergeList);
		mergeData(data,mergeList);
		LOGGER.debug("Final Result : {} ",data);
		return data;
	}

	private void GetDocumentTextDetectionResults(PDFProcessorDto pdfProcessorDto) throws Exception{
		int maxResults=1000;
		String paginationToken=null;
		GetDocumentTextDetectionResult response=null;
		Boolean finished=false;
		
		Map<String, Block> tableBlock = new LinkedHashMap<>();
		Map<String, Block> otherBlock = new LinkedHashMap<>();
		List<Integer> mergeList = new ArrayList<>();
		
		while (finished==false)
		{
			GetDocumentTextDetectionRequest documentTextDetectionRequest= new GetDocumentTextDetectionRequest()
					.withJobId(pdfProcessorDto.getJobId())
					.withMaxResults(maxResults)
					.withNextToken(paginationToken);

			response = pdfProcessorDto.getTextract().getDocumentTextDetection(documentTextDetectionRequest);
			LOGGER.debug("Document text detection result : {}",response);
			DocumentMetadata documentMetaData=response.getDocumentMetadata();

			LOGGER.debug("Pages: {} ",documentMetaData.getPages().toString());
			//Show blocks information
			List<Block> blocks= response.getBlocks();
			
			generateBlockMap(blocks,tableBlock,otherBlock);
			paginationToken=response.getNextToken();
			if (paginationToken==null)
				finished=true;
		}
		Map<Integer, Map<Integer, String>> data = prepareFinalMap(tableBlock,otherBlock,mergeList);
		
	}
	
	private void generateBlockMap(List<Block> blocks,Map<String, Block> tableBlock,Map<String, Block> otherBlock) {

		for (Block block : blocks) {
			if(block.getBlockType().equals("TABLE")) {
				tableBlock.put(block.getId(), block);
			}else {
				otherBlock.put(block.getId(), block);
			}
		}
	}

	private void mergeData(Map<Integer, Map<Integer, String>> rowColumnMap,List<Integer> mergeList) {
		LOGGER.debug("Merged list : {}",mergeList);
		if(mergeList != null && !mergeList.isEmpty()) {
			for(int i=0;i<mergeList.size();i++) {
				int k = mergeList.get(i);
				Map<Integer, String> columnMap = rowColumnMap.get(k);
				int preIndex = k-1;
				while(!rowColumnMap.containsKey(preIndex)) {
					preIndex--;
				}
				Map<Integer, String> preColumnMap = rowColumnMap.get(preIndex);
				for(Integer column :columnMap.keySet()) {
					String preValue = preColumnMap.get(column);
					String curValue = columnMap.get(column);
					preColumnMap.put(column, preValue+" "+curValue);
				}
				rowColumnMap.put(k-1, preColumnMap);
				rowColumnMap.remove(k);
			}
		}
		
	}
	
	private Map<Integer, Map<Integer, String>> prepareFinalMap(Map<String, Block> tableBlock,Map<String, Block> otherBlock,List<Integer> mergeList) {
		Map<Integer, Map<Integer, String>> rowColumnMap = new LinkedHashMap<>();
		
		if(!tableBlock.isEmpty()) {
			for(String table : tableBlock.keySet()) {
				Block tBlock = tableBlock.get(table);
				for(Relationship relationShip : tBlock.getRelationships()) {
					
					Map<Integer, String> columnMap = null;
					for(int i=0; i<relationShip.getIds().size()-20;i++) {
						Block valueBlock = otherBlock.get(relationShip.getIds().get(i));
						
						if(valueBlock.getBlockType().equals("CELL")) {
							
							if(valueBlock == null || valueBlock.getRowIndex()==1) {
								continue;
							}
							
							int rowIndex = valueBlock.getRowIndex();
							if(valueBlock.getRelationships() == null || valueBlock.getRelationships().isEmpty()) {
								if(!mergeList.contains(rowIndex)) {
									mergeList.add(rowIndex);
								}
							}
							
							if(rowColumnMap.containsKey(rowIndex)) {
								columnMap = rowColumnMap.get(rowIndex);
							}else {
								columnMap = new HashMap<>();
							}
							
							if(valueBlock.getRelationships() != null) {
								for(Relationship cellRel : valueBlock.getRelationships()) {
									StringBuilder strBuilder= new StringBuilder();
									for(String cellRelId : cellRel.getIds()) {
										Block wordBlock = otherBlock.get(cellRelId);
										strBuilder.append(wordBlock.getText());
									}
									columnMap.put(valueBlock.getColumnIndex(), strBuilder.toString());
								}
							}
							rowColumnMap.put(valueBlock.getRowIndex(), columnMap);
						}
					}
				}
			}
		}
		LOGGER.debug("Result Before Merge : {} ",rowColumnMap);
		return rowColumnMap;
	}
	
	/*
	 * public static void main(String[] args) { new
	 * PDFDocumentProcessor().process("dpword_invoice.pdf"); }
	 */
}
