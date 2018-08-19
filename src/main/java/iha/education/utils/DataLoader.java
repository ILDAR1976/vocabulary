package iha.education.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
	private PartSpeech ps;
	private SenseGroup sg;
	private SubGroup sug;
	
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
		List<Element> sequence = new ArrayList<>();
		
		wrapper.getCards().stream().forEach(item -> {
			Element elm = new Element();
			elm.setId(item.getId());
			elm.setObj(item);
			elm.setType(item.getClass().getName());
			sequence.add(elm);
		});

		
		wrapper.getPartSpeech().stream().forEach(item -> {
			Element elm = new Element();
			elm.setId(item.getId());
			elm.setObj(item);
			elm.setType(item.getClass().getName());
			sequence.add(elm);
		});

		wrapper.getSenseGroup().stream().forEach(item -> {
			Element elm = new Element();
			elm.setId(item.getId());
			elm.setObj(item);
			elm.setType(item.getClass().getName());
			sequence.add(elm);
		});

		wrapper.getSubGroup().stream().forEach(item -> {
			Element elm = new Element();
			elm.setId(item.getId());
			elm.setObj(item);
			elm.setType(item.getClass().getName());
			sequence.add(elm);
		});
		
		sequence.sort(new CompTypeComparator());
		sequence.stream().forEach(g -> {
			if (g.getType().equals("iha.education.entity.PartSpeech")) {
				ps = (PartSpeech) g.getObj();
				ps.getCards().clear();
				partSpeechService.save(ps);
			} if (g.getType().equals("iha.education.entity.SenseGroup")) {
				sg = (SenseGroup) g.getObj();
				sg.getCards().clear();
				senseGroupService.save(sg);	
			} if (g.getType().equals("iha.education.entity.SubGroup")) {
				sug = (SubGroup) g.getObj();
				sug.getCards().clear();
				subGroupService.save(sug);		
			} if (g.getType().equals("iha.education.entity.Cards")) {
				Cards crd = (Cards) g.getObj();
				crd.setPartSpeech(ps);
				crd.setSenseGroup(sg);
				crd.setSubGroup(sug);
				cardsService.save(crd);			
			}
		});

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

}
