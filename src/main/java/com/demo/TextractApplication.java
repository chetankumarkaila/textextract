package com.demo;

//@SpringBootApplication
public class TextractApplication /* extends SpringBootServletInitializer */{

	public static void main(String[] args){
		//SpringApplication.run(TextractApplication.class, args);

		// Referring static method

		Messageable messageable = TextractApplication::new;
		messageable.getMessage("Chetan");
		
	}



	public TextractApplication(String arg) {
		System.out.println("Textextract contructor : "+arg);
	}

}

interface Messageable{  
	TextractApplication getMessage(String msg);  
}  

