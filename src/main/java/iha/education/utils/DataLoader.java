package iha.education.utils;

import static iha.education.utils.Utils.saveCards;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.javafx.application.LauncherImpl;

import iha.education.Application;
import iha.education.CurrentPreloader;
import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.service.CardsService;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;
import iha.education.ProgressBeanPostProcessor;
import javafx.collections.FXCollections;


public class DataLoader {
	
	private Logger logger = LoggerFactory.getLogger(DataLoader.class);
	private Application application;
	
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
	private PartSpeech ps;
	private SenseGroup sg;
	private SubGroup sug;
	private Cards crd;
	
	public DataLoader() {
		logger.info("Running data loader ...");
	}
	
	@PostConstruct
	public void inicialization() throws JAXBException {
		logger.info("Inicialization data source ...");
		
		switch (2) {
		
		case 1:
			generateData();
			break;
		case 2:
			File file = Utils.getVocabularyFile();
			cards = new ArrayList<>();
			wrapper = new CardsListWrapper();
			wrapper = Utils.loadCards(file, wrapper);
			transferToSpringContext();
			break;
		}
	}
	
	private void transferToSpringContext() {
		
		cards = new ArrayList<>();
		partSpeeches = new ArrayList<>();
		senseGroups = new ArrayList<>();
		subGroups = new ArrayList<>();
		
		wrapper.getPartSpeech().stream().forEach(item -> {
			ps = item;
			List<Cards> crd = ps.getCards();
			crd.stream().forEach(c -> {
				c.setModificated(false);
				if (cards.contains(c)) {
					cards.get(cards.indexOf(c)).setPartSpeech(ps);
				} else {
					c.setPartSpeech(ps);
					cards.add(c);
				}
				
				this.generateInfo("load part speech: " + ps.getName());
			});
			ps.getCards().clear();
			ps.setModificated(false);
			partSpeechService.save(ps);
			
			
		});

		wrapper.getSenseGroup().stream().forEach(item -> {
			sg = item;
			List<Cards> crd = sg.getCards();
			crd.stream().forEach(c -> {
				c.setModificated(false);
				if (cards.contains(c)) {
					cards.get(cards.indexOf(c)).setSenseGroup(sg);
				} else {
					c.setSenseGroup(sg);
					cards.add(c);
				}
				
				this.generateInfo("load sense group: " + sg.getName());
			});
			sg.getCards().clear();
			sg.setModificated(false);
			senseGroupService.save(sg);
		});
		
		wrapper.getSubGroup().stream().forEach(item -> {
			sug = item;
			List<Cards> crd = sug.getCards();
			crd.stream().forEach(c -> {
				if (cards.contains(c)) {
					cards.get(cards.indexOf(c)).setSubGroup(sug);
				} else {
					c.setSubGroup(sug);
					cards.add(c);
				}
				
				this.generateInfo("load sub group: " + sug.getName());
			});
			sug.getCards().clear();
			sug.setModificated(false);
			subGroupService.save(sug);
		});

		cards.stream().forEach(c ->{
			c.setModificated(false);
			cardsService.save(c);
			this.generateInfo("load word: " + c.getWord());
		});
		
		logger.info("count: "+ProgressBeanPostProcessor.getCounter());
		
	}
	
	private void generateTestData() {
		createRow("Verb", "Глагол", "Verb of Stage", "Глаголы стадии", "beginning", "Начало", "begin", "начало", "I begin a talk");
		createRow("Verb", "Глагол", "Verb of Stage", "Глаголы стадии", "beginning", "Начало", "start", "начинать", "I start a bussines");
	}

	private void createRow(String partSpeech,
			               String partSpeech_translate,	
			               String senseGroup,
			               String senseGroup_translate,
			               String subGroup,
			               String subGroup_translate,
			               String word, 
			               String translate, 
			               String example) {
		
		PartSpeech ps = null;
		SenseGroup sg = null;
		SubGroup sug = null;
		
		if (partSpeechService.findByName(partSpeech) != null) {
			ps = partSpeechService.findByName(partSpeech);
		} else {
			ps = new PartSpeech(partSpeech, partSpeech_translate);
			ps.getCards().clear();
		}
		
		if (senseGroupService.findByName(senseGroup) != null) {
			sg = senseGroupService.findByName(senseGroup);
		} else {
			sg = new SenseGroup(senseGroup, senseGroup_translate);
			sg.getCards().clear();
		}

		if (subGroupService.findByName(subGroup) != null) {
			sug = subGroupService.findByName(subGroup);
		} else {
			sug = new SubGroup(subGroup,subGroup_translate);
			sug.getCards().clear();
		}
		
		partSpeechService.save(ps);
		senseGroupService.save(sg);
		subGroupService.save(sug);

		Cards cds = new Cards(word, 
				              translate, 
				              example); 
		
		ps.getCards().add(cds);
		sg.getCards().add(cds);
		sug.getCards().add(cds);
		
		cds.setPartSpeech(ps);
		cds.setSenseGroup(sg);
		cds.setSubGroup(sug);
		
		cardsService.save(cds);
		
		
	}

	private void generateData() throws JAXBException {
		generateTestData();
		cards = cardsService.findAll();
		partSpeeches = partSpeechService.findAll();
		senseGroups = senseGroupService.findAll();
		subGroups = subGroupService.findAll();
		wrapper = new CardsListWrapper();
		//wrapper.setCards(cards);
		wrapper.setPartSpeech(partSpeeches);
		wrapper.setSenseGroup(senseGroups);
		wrapper.setSubGroup(subGroups);
		File file = Utils.getVocabularyFile();
		saveCards(file, wrapper);
	}
	
	public class Element {
		private Long id;
		private Object obj;
		private String type;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Object getObj() {
			return obj;
		}
		public void setObj(Object obj) {
			this.obj = obj;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
	class CompTypeComparator implements Comparator<Element> {
		  public int compare(Element o1, Element o2) {
		    return (o1.id < o2.id ? -1 : (o1.id == o2.id ? 0 : 1));
		  }
	}

    private void generateInfo(String info) {	
    	ProgressBeanPostProcessor.setCounter(ProgressBeanPostProcessor.getCounter()+1);
		LauncherImpl.notifyPreloader(application, new CurrentPreloader.PreloaderProgressNotification( ProgressBeanPostProcessor.getCounter(), info));
    }

	
    public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}
