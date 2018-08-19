package iha.education.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import iha.education.Application;
import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.service.CardsService;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;
import iha.education.utils.CardsListWrapper;

@SuppressWarnings({ "restriction" })
public class CheckCardController {

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(CheckCardController.class);

    @SuppressWarnings("unused")
	@Autowired 
    private PartSpeechService partSpeechService;
    
    @SuppressWarnings("unused")
	@Autowired 
    private SenseGroupService senseGroupService;
    
    @SuppressWarnings("unused")
	@Autowired 
    private SubGroupService subGroupService;
    
    @SuppressWarnings("unused")
	@Autowired 
    private CardsService cardsService;
    
	private Application mainApp;
    
    @FXML 
    private AnchorPane checkCardsAnchor;
    
    @FXML
    private TextArea info;
    
    @FXML 
    private TableView<Cards> checkTable;
 
    @SuppressWarnings("unused")
	private ObservableList<Cards> data;
    @SuppressWarnings("unused")
	private CardsListWrapper wrapper;
	private ObservableList<Cards> cardsData;
	private List<Cards> cards =  new ArrayList<>();
	private List<Statistics> statistics = new ArrayList<>();
	
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
    	logger.info("Running CHECK card controller ...");
    	
    	PartSpeech partSpeech = partSpeechService.findTop1By();
    	SenseGroup senseGroup = senseGroupService.findTop1By();
    	SubGroup subGroup = subGroupService.findTop1By();
    	
    	info.setText(
    			info.getText() + 
    			"Card: " + 
    			partSpeech.getName() + "-" +
    			senseGroup.getName() + "-" +
    			subGroup.getName() +
    			System.lineSeparator()
    			); 
    	
    	cards = cardsService.findByThirdFilter(partSpeech, senseGroup, subGroup);
    	
    	cardsData = FXCollections.observableArrayList(cards);
 		
		TableColumn<Cards, String> wordColumn = new TableColumn<>("Word");
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
		
		TableColumn<Cards, String> translateColumn = new TableColumn<>("Translate");
		translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));

		TableColumn<Cards, String> checkColumn = new TableColumn<>("Check word");
		checkColumn.setCellValueFactory(new PropertyValueFactory<>("checkWord"));
		checkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		checkColumn.setOnEditCommit(e -> checkColumn_OnEditCommit(e));
		
		TableColumn<Cards, String> modificatedColumn = new TableColumn<>("Modificated");
		modificatedColumn.setCellValueFactory(new PropertyValueFactory<>("modificated"));
		modificatedColumn.setVisible(false);
		
		checkTable.getColumns().setAll(
				wordColumn,
				translateColumn, 
				checkColumn,
				modificatedColumn);
		checkTable.setItems(cardsData);
		
    }

    private void checkColumn_OnEditCommit(Event e) {
    	logger.info(" Edit commit check column ...");
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		cards.setCheckWord(cellEvent.getNewValue());
		checkTable.refresh();
	}
    
    @Autowired 
    public AnchorPane getCheckCardAnchor() {
    	return checkCardsAnchor;
    }
    
    public void handleCloseTable() {
    	this.checkCardsAnchor.setVisible(false);
    }
    
    public void handleCheckButton() {
    	Statistics statistic = new Statistics();
    	
    	statistic.setCardName(
    			partSpeechService.findTop1By().getName() + "-" +
     			senseGroupService.findTop1By().getName() + "-" +
    			subGroupService.findTop1By().getName());
    	
    	statistic.setTimeStamp(new Date());
    	
    	cardsData.stream().forEach(x -> {
    		if (!(x.getCheckWord() == null))
    		if ((x.getCheckWord().equals(x.getWord())) || (x.getCheckWord().equals(x.getTranslate())  ) ) {
    			statistic.setMatchesCounter(statistic.getMatchesCounter()+1);
    		}
    	});
    	
    	info.setText(info.getText() + " " + statistic.getTimeStamp().toString() + " matches : " + statistic.getMatchesCounter() + System.lineSeparator());
    	
    	statistics.add(statistic);
    	info.selectEnd();
    }

    public void handleGetButton() {
    	
    	List<PartSpeech> ps = partSpeechService.findAll();
    	List<SenseGroup> sg = senseGroupService.findAll();
    	List<SubGroup> sug = subGroupService.findAll();

    	List<Cards> cards = null;
    	PartSpeech cps = null;
    	SenseGroup csg = null;
    	SubGroup csug = null;

    	Boolean flag = true;
    	
    	do {
    		
    	
	    	int rnd1 = (int)(Math.random() * ps.get(ps.size()-1).getId().intValue());
	    	int rnd2 = (int)(Math.random() * sg.get(sg.size()-1).getId().intValue());
	    	int rnd3 = (int)(Math.random() * sug.get(sug.size()-1).getId().intValue());
	       	
	    	cps = partSpeechService.findTop1By();
	    	csg = senseGroupService.findTop1By();
	    	csug = subGroupService.findTop1By();
    	
	    	int min1 = ps.get(ps.size()-1).getId().intValue();
	    	int min2 = sg.get(sg.size()-1).getId().intValue();
	    	int min3 = sug.get(sug.size()-1).getId().intValue();
	    	
	    	for (int i = 0; i < ps.size();i++) {
	    		if (Math.abs(ps.get(i).getId() - rnd1) <= min1) {
	    			min1 = (int) Math.abs(ps.get(i).getId() - rnd1);
	    			cps = ps.get(i);
	    		}
	    	}
	    	
	    	for (int i = 0; i < sg.size();i++) {
	    		if (Math.abs(sg.get(i).getId() - rnd2) <= min2) {
	    			min2 = (int) Math.abs(sg.get(i).getId() - rnd2);
	    			csg = sg.get(i);
	    		}
	    	}
	
	    	for (int i = 0; i < sug.size();i++) {
	    		if (Math.abs(sug.get(i).getId() - rnd3) <= min3) {
	    			min3 = (int) Math.abs(sug.get(i).getId() - rnd3);
	    			csug = sug.get(i);
	    		}
	    	}
	    	
	    	cards = cardsService.findByThirdFilter(cps, csg, csug);
	    	
	    	if (cards.size() != 0) {
	    		flag = false;
	    	}
	    	
    	} while (flag);
    	
    	cardsData = FXCollections.observableArrayList(cards);
    	checkTable.setItems(cardsData);
		checkTable.refresh();
		checkTable.requestFocus();
		
		
	   	info.setText(
    			info.getText() + 
    			"Card: " + 
    			cps.getName() + "-" +
    			csg.getName() + "-" +
    			csug.getName() +
    			System.lineSeparator()
    			); 
	   	info.selectEnd();
    }
    
	public void setMainApp(Application mainApp) {
        this.mainApp = mainApp;
    }

	private class Statistics{
		private String CardName;
		private int matchesCounter;
		private Date timeStamp;
		public String getCardName() {
			return CardName;
		}
		public void setCardName(String cardName) {
			CardName = cardName;
		}
		public int getMatchesCounter() {
			return matchesCounter;
		}
		public void setMatchesCounter(int matchesCounter) {
			this.matchesCounter = matchesCounter;
		}
		public Date getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(Date timeStamp) {
			this.timeStamp = timeStamp;
		}
		
		
	}

	public void update() {
		checkTable.requestFocus();
		checkTable.refresh();
	}
}
