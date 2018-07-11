package iha.education.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Utils {
	
	public static void saveCards(File file, CardsListWrapper wrapper) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(CardsListWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(wrapper, file);
	}
	
	public static CardsListWrapper loadCards(File file, CardsListWrapper wrapper) {
		
		try {
			
			System.out.println(Files.exists(file.toPath()));
	        JAXBContext context = JAXBContext
	                .newInstance(CardsListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();
	        wrapper = (CardsListWrapper) um.unmarshal(file);
	    
	        
		} catch (Exception e) { 
	    	System.out.println(e);
	    }
		
		return wrapper;
	}

	public static File getVocabularyFile() {
		Path path = Paths.get("./vocabulary");
		if (!(Files.exists(path) && Files.isDirectory(path))) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new File(path.toString() +  "/cards.xml");
		
	}
}
