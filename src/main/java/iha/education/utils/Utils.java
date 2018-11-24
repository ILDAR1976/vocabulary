package iha.education.utils;

import static iha.education.utils.Utils.loadCards;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;

import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.service.CardsService;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;
import javafx.collections.FXCollections;

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

	public static void write(String fileName, String text) {
		try {
			PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
