package iha.education.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.service.CardsService;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;

public class DataLoader {
	
	private Logger logger = LoggerFactory.getLogger(DataLoader.class);
	
	@Autowired
	private PartSpeechService partSpeechService;

	@Autowired
	private SenseGroupService senseGroupService;

	@Autowired
	private SubGroupService subGroupService;

	@Autowired
	private CardsService cardsService;
	
	private CardsListWrapper wrapper;
	private List<Cards> cards =  new ArrayList<>();
	private List<PartSpeech> partSpeeches =  new ArrayList<>();;
	private List<SenseGroup> senseGroups =  new ArrayList<>();;
	private List<SubGroup> subGroups =  new ArrayList<>();;
	
	public DataLoader() {
		logger.info("Running data loader ...");
	}
	
	@PostConstruct
	public void inicialization() {
		logger.info("Inicialization data source ...");
		File file = Utils.getVocabularyFile();
		cards = new ArrayList<>();
		wrapper = new CardsListWrapper();
		wrapper = Utils.loadCards(file, wrapper);
		transferToSpringContext();
	}

	private void transferToSpringContext() {
		
		partSpeeches = new ArrayList<>();
		senseGroups = new ArrayList<>();
		subGroups = new ArrayList<>();
		
		wrapper.getCards().stream().forEach(item->{
			Cards crds = item;
			
			PartSpeech ps = item.getPartSpeech();
			ps.getCards().clear();
			
			SenseGroup sg = item.getSenseGroup();
			sg.getCards().clear();
			
			SubGroup sug = item.getSubGroup();
			sug.getCards().clear();
			
			crds.setPartSpeech(ps);
			crds.setSenseGroup(sg);
			crds.setSubGroup(sug);
			
			partSpeechService.save(ps);
			senseGroupService.save(sg);
			subGroupService.save(sug);
			cardsService.save(crds);
		});
		
	}
}
