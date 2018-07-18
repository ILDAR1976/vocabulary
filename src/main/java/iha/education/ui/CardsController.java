package iha.education.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.control.cell.*;
import org.springframework.session.Session;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;

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
import iha.education.utils.HibernateUtils;
import iha.education.utils.Utils;
import static iha.education.utils.Utils.*;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "restriction" })
public class CardsController {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(CardsController.class);

	@Autowired
	private PartSpeechService partSpeechService;

	@Autowired
	private SenseGroupService senseGroupService;

	@Autowired
	private SubGroupService subGroupService;

	@Autowired
	private CardsService cardsService;

	@FXML
	private AnchorPane cardsAnchor;

	@FXML
	private TableView<Cards> table;
	
	@FXML
	private Label notApply;
	
	@FXML
	private TextField txtPartOfSpeech;
	
	@FXML
	private TextField txtSenseGroup;
	
	@FXML
	private TextField txtSubGroup;
	
	@FXML
	private TextField txtWord;
	
	@FXML 
	private TextField txtTranslate;
	
	@FXML
	private TextField txtExample;
	
	@FXML
	private Button closeButton;
	
	private Application mainApp;
	private CardsListWrapper wrapper;
	private ObservableList<Cards> cardsData;
	private List<Cards> cards =  new ArrayList<>();
	private List<PartSpeech> partSpeeches =  new ArrayList<>();;
	private List<SenseGroup> senseGroups =  new ArrayList<>();;
	private List<SubGroup> subGroups =  new ArrayList<>();;
	
	private PartSpeech currentPartSpeech;
	private SenseGroup currentSenseGroup;
	private SubGroup currentSubGroup;
	private String currentWord;
	private String currentTranslate;
	private String currentExample;
	
	@Autowired
	public AnchorPane getCardsAnchor() {
		return cardsAnchor;
	}

	@FXML
	public void handleCloseTable() {
		this.cardsAnchor.setVisible(false);
	}
	
	@FXML
	public void handleSaveData() throws JAXBException {
		File file = Utils.getVocabularyFile();
		//cardsData = FXCollections.observableArrayList(cards);
		cardsData.stream().forEach(e->{cardsService.save(e);});
		wrapper = new CardsListWrapper();
		wrapper.setCards(cardsData);
		saveCards(file, wrapper);
		notApply.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws JAXBException, URISyntaxException {
		File file = Utils.getVocabularyFile();

		switch (1) {
		case 1:
			generateTestData();
			cards = cardsService.findAll();
			cardsData = FXCollections.observableArrayList(cards);
			wrapper = new CardsListWrapper();
			wrapper.setCards(cardsData);
			saveCards(file, wrapper);
			break;
		case 2:
			cards = new ArrayList<>();
			wrapper = new CardsListWrapper(FXCollections.observableArrayList(cards));
			wrapper = loadCards(file, wrapper);
			transferToSpringContext();
			cardsData = FXCollections.observableArrayList(cards);
			break;
		}

		TableColumn<Cards, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setCellFactory(IdFieldTableCell.forTableColumn());
		idColumn.setOnEditStart(e -> {idColumn_OnEditCommit(e);});
		
		TableColumn<Cards, PartSpeech> partSpeechColumn = new TableColumn<>("Parts of Speech");
		partSpeechColumn.setCellValueFactory(new PropertyValueFactory<>("partSpeech"));
		partSpeechColumn.setCellFactory(PartSpeechFieldTableCell.forTableColumn());
		partSpeechColumn.setOnEditStart(e -> {partSpeechColumn_OnEditCommit(e);});
		
		TableColumn<Cards, SenseGroup> senseGroupColumn = new TableColumn<>("Sense group");
		senseGroupColumn.setCellValueFactory(new PropertyValueFactory<>("senseGroup"));
		senseGroupColumn.setCellFactory(SenseGroupFieldTableCell.forTableColumn());
		senseGroupColumn.setOnEditStart(e -> {senseGroup_OnEditCommit(e);});
		
		TableColumn<Cards, SubGroup> subGroupColumn = new TableColumn<>("Subgroup");
		subGroupColumn.setCellValueFactory(new PropertyValueFactory<>("subGroup"));
		subGroupColumn.setCellFactory(SubGroupFieldTableCell.forTableColumn());
		subGroupColumn.setOnEditStart(e -> {subGroupColumn_OnEditCommit(e);});
		
		TableColumn<Cards, String> wordColumn = new TableColumn<>("Word");
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
		wordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		wordColumn.setOnEditCommit(e -> wordColumn_OnEditCommit(e));
		
		TableColumn<Cards, String> translateColumn = new TableColumn<>("Translate");
		translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));
		translateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		translateColumn.setOnEditCommit(e -> translateColumn_OnEditCommit(e));
		
		TableColumn<Cards, String> exampleColumn = new TableColumn<>("Example");
		exampleColumn.setCellValueFactory(new PropertyValueFactory<>("example"));
		exampleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		exampleColumn.setOnEditCommit(e -> exampleColumn_OnEditCommit(e));
		
		TableColumn<Cards, String> modificatedColumn = new TableColumn<>("Modificated");
		modificatedColumn.setCellValueFactory(new PropertyValueFactory<>("modificated"));
		modificatedColumn.setVisible(false);
		
		table.getColumns().setAll(
				idColumn, 
				partSpeechColumn, 
				senseGroupColumn, 
				subGroupColumn, 
				wordColumn,
				translateColumn, 
				exampleColumn,
				modificatedColumn);
		table.setItems(cardsData);
		
		table.refresh();
		if (this.cardsData.isEmpty()) {
			closeButton.requestFocus();;
		} else {
			
			TableViewFocusModel<Cards> fm = table.getFocusModel();
			fm.focus(cardsData.size()-1);
			TableViewSelectionModel<Cards> sm = table.getSelectionModel();
			sm.select(cardsData.size()-1);
			table.setSelectionModel(sm);
			table.setFocusModel(fm);
			
		}
		
		table.requestFocus();
		
	}

	private void transferToSpringContext() {
		
		partSpeeches = new ArrayList<>();
		senseGroups = new ArrayList<>();
		subGroups = new ArrayList<>();
		
		wrapper.getCards().stream().forEach(item->{
			if (!partSpeeches.contains(item.getPartSpeech())) {
				partSpeeches.add(item.getPartSpeech());
				
			}

			partSpeechService.save(partSpeeches.get(partSpeeches.size()-1));
			
			if (!getSenseGroups().contains(item.getSenseGroup())) {
				senseGroups.add(item.getSenseGroup());
				
			}
			
			senseGroupService.save(senseGroups.get(senseGroups.size()-1));
			
			if (!getSubGroups().contains(item.getSubGroup())) {
				subGroups.add(item.getSubGroup());
				
			}
			
			subGroupService.save(subGroups.get(subGroups.size()-1));
			
			cards.add(new Cards(
					item.getPartSpeech(),
					item.getSenseGroup(),
					item.getSubGroup(), 
			item.getWord(), 
			item.getTranslate(),
			item.getExample()));
			
			cardsService.save(cards.get(cards.size()-1));
		});
	}

	private void idColumn_OnEditCommit(CellEditEvent<Cards, String> e) {
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		
		currentPartSpeech = cards.getPartSpeech();
		currentSenseGroup = cards.getSenseGroup();
		currentSubGroup = cards.getSubGroup();
		currentWord = cards.getWord();
		currentTranslate = cards.getTranslate();
		currentExample = cards.getExample();
		
		txtPartOfSpeech.setText(currentPartSpeech.getName());
		txtSenseGroup.setText(currentSenseGroup.getName());
		txtSubGroup.setText(currentSubGroup.getName());
		txtWord.setText(currentWord);
		txtTranslate.setText(currentTranslate);
		txtExample.setText(currentExample);
	}

	@SuppressWarnings("unchecked")
	private void wordColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		cards.setWord(cellEvent.getNewValue());
		notApply.setVisible(true);
		cards.setModificated(true);
		table.refresh();
	}

	@SuppressWarnings("unchecked")
	private void translateColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		cards.setTranslate(cellEvent.getNewValue());
		notApply.setVisible(true);
		cards.setModificated(true);
		table.refresh();
	}

	@SuppressWarnings("unchecked")
	private void exampleColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		cards.setExample(cellEvent.getNewValue());
		notApply.setVisible(true);
		cards.setModificated(true);
		table.refresh();
	}

	@SuppressWarnings("unchecked")
	private void partSpeechColumn_OnEditCommit(Event e) {
		EditCardController<PartSpeech,PartSpeechService,CardsController> editController = ((EditCardController<PartSpeech,PartSpeechService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setService(partSpeechService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		editController.setEntity(cellEvent.getRowValue().getPartSpeech());
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}
	
	@SuppressWarnings("unchecked")
	private void senseGroup_OnEditCommit(Event e) {
		EditCardController<SenseGroup,SenseGroupService,CardsController> editController = ((EditCardController<SenseGroup,SenseGroupService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setService(senseGroupService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		editController.setEntity(cellEvent.getRowValue().getSenseGroup());
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}
	
	@SuppressWarnings("unchecked")
	private void subGroupColumn_OnEditCommit(Event e) {
		EditCardController<SubGroup,SubGroupService,CardsController> editController = ((EditCardController<SubGroup,SubGroupService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setService(subGroupService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		editController.setEntity(cellEvent.getRowValue().getSubGroup());
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void handlePartSpeech() {
	
		EditCardController<PartSpeech,PartSpeechService,CardsController> editController = ((EditCardController<PartSpeech,PartSpeechService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setEntity(currentPartSpeech);
		editController.setService(partSpeechService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		editController.setInsertButton(true);
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}
	
	@FXML
	private void handleSenseGroup(Event e) {
		@SuppressWarnings("unchecked")
		EditCardController<SenseGroup,SenseGroupService,CardsController> editController = ((EditCardController<SenseGroup,SenseGroupService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setEntity(currentSenseGroup);
		editController.setService(senseGroupService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		editController.setInsertButton(true);
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}
	
	@FXML
	private void handleSubGroup() {
		@SuppressWarnings("unchecked")
		EditCardController<SubGroup,SubGroupService,CardsController> editController = ((EditCardController<SubGroup,SubGroupService,CardsController>) mainApp.getEditCardView().getController());
		MainController mainController = ((MainController) this.mainApp.getMainView().getController());
		editController.getEditPane().setVisible(true);
		editController.setEntity(currentSubGroup);
		editController.setService(subGroupService);
		editController.setContoller(this);
		editController.setMainController(mainController);
		editController.setInsertButton(true);
		editController.update();
		mainController.getMainFrame().setCenter(mainApp.getEditCardView().getView());
		editController.getTable().requestFocus();
	}
	
	@FXML
	private void handleInsertRow() {
		currentWord = txtWord.getText();
		currentTranslate = txtTranslate.getText();
		currentExample = txtExample.getText();
		
		cards.add(new Cards(currentPartSpeech, currentSenseGroup, currentSubGroup,
				            currentWord, currentTranslate, currentExample));
		Cards card = cards.get(cards.size()-1);
		cardsService.save(card);
		cardsData.add(card);
		table.refresh();
		TableViewSelectionModel<Cards> sm = table.getSelectionModel();
		sm.select(cards.size()-1);
		clearOldValue();
	}
	
	private void clearOldValue() {
		currentPartSpeech = null;
		currentSenseGroup = null;
		currentSubGroup = null;
		currentWord = "";
		currentTranslate = "";
		currentExample = "";
		
		txtPartOfSpeech.setText("");
		txtSenseGroup.setText("");
		txtSubGroup.setText("");
		txtWord.setText("");
		txtTranslate.setText("");
		txtExample.setText("");
		
	}
	
	private void generateTestData() {
		
		createRow("Verb", "Глагол", "Verb of Stage", "Глаголы стадии", "beginning", "Начало", "begin", "начало", "I begin a talk");
	
	
		createRow("Verb", "Глагол", "Verb of Stage", "Глаголы стадии", "beginning", "Начало", "start", "начинать", "I start a bussines");

	}

	@SuppressWarnings("unused")
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
			partSpeeches.add(ps);
		}
		
		if (senseGroupService.findByName(senseGroup) != null) {
			sg = senseGroupService.findByName(senseGroup);
		} else {
			sg = new SenseGroup(senseGroup, senseGroup_translate);
			sg.getCards().clear();
			senseGroups.add(sg);
		}

		if (subGroupService.findByName(subGroup) != null) {
			sug = subGroupService.findByName(subGroup);
		} else {
			sug = new SubGroup(subGroup,subGroup_translate);
			sug.getCards().clear();
			subGroups.add(sug);
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
		
		cards.add(cds);
	}

	public void setMainApp(Application mainApp) {
        this.mainApp = mainApp;
    }
	
	public ObservableList<Cards> getData() {
		return cardsData;
	}
	
	public void setData(ObservableList<Cards> data) {
		this.cardsData = data;
	}

	public TableView<Cards> getTable() {
		return table;
	}

	public void setTable(TableView<Cards> table) {
		this.table = table;
	}

	public Label getNotApply() {
		return notApply;
	}

	public void setNotApply(Label notApply) {
		this.notApply = notApply;
	}
	
	public List<Cards> getCards() {
		return cards;
	}

	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}

	public List<PartSpeech> getPartSpeeches() {
		return partSpeeches;
	}

	public void setPartSpeeches(List<PartSpeech> partSpeeches) {
		this.partSpeeches = partSpeeches;
	}

	public List<SenseGroup> getSenseGroups() {
		return senseGroups;
	}

	public void setSenseGroups(List<SenseGroup> senseGroups) {
		this.senseGroups = senseGroups;
	}

	public List<SubGroup> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<SubGroup> subGroups) {
		this.subGroups = subGroups;
	}

	public void update() {
		table.refresh();
		table.requestFocus();
	}

	public PartSpeech getCurrentPartSpeech() {
		return currentPartSpeech;
	}

	public void setCurrentPartSpeech(PartSpeech currentPartSpeech) {
		this.currentPartSpeech = currentPartSpeech;
	}

	public SenseGroup getCurrentSenseGroup() {
		return currentSenseGroup;
	}

	public void setCurrentSenseGroup(SenseGroup currentSenseGroup) {
		this.currentSenseGroup = currentSenseGroup;
	}

	public SubGroup getCurrentSubGroup() {
		return currentSubGroup;
	}

	public void setCurrentSubGroup(SubGroup currentSubGroup) {
		this.currentSubGroup = currentSubGroup;
	}

	public String getCurrentWork() {
		return currentWord;
	}

	public void setCurrentWork(String currentWork) {
		this.currentWord = currentWork;
	}

	public String getCurrentTranslate() {
		return currentTranslate;
	}

	public void setCurrentTranslate(String currentTranslate) {
		this.currentTranslate = currentTranslate;
	}

	public String getCurrentExample() {
		return currentExample;
	}

	public void setCurrentExample(String currentExample) {
		this.currentExample = currentExample;
	}

	public void setTxtPartOfSpeech(String txtPartSpeech) {
		this.txtPartOfSpeech.setText(txtPartSpeech);
	}

	public void setTxtSenseGroup(String txtSenseGroup) {
		this.txtSenseGroup.setText(txtSenseGroup);
	}

	public void setTxtSubGroup(String txtSubGroup) {
		this.txtSubGroup.setText(txtSubGroup);
	}

	public void setTxtWord(String txtWord) {
		this.txtWord.setText(txtWord);
	}

	public void setTxtTranslate(String txtTranslate) {
		this.txtTranslate.setText(txtTranslate);
	}

	public void setTxtExample(String txtExample) {
		this.txtExample.setText(txtExample);
	}

}
