package iha.education.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.control.cell.*;
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
	private TableViewSelectionModel<Cards> selectionModel;
	private List<Cards> cards;
	private List<PartSpeech> partSpeeches;
	private List<SenseGroup> senseGroups;
	private List<SubGroup> subGroups;
	
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
		cardsData = FXCollections.observableArrayList(cards);
		cardsData.stream().forEach(e->{cardsService.save(e);});
		wrapper = new CardsListWrapper();
		wrapper.setCards(cardsData);
		saveCards(file, wrapper);
		notApply.setVisible(false);
	}
	
	public void setFocus() {
		Node focusOwnerNode = mainApp.getScene().getFocusOwner();
		focusOwnerNode = table;
		focusOwnerNode.requestFocus();
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws JAXBException, URISyntaxException {
		File file = Utils.getVocabularyFile();

		switch (2) {
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
		
		if (this.cardsData.isEmpty()) {
			closeButton.setFocusTraversable(true);
		} else {
			TableViewFocusModel<Cards> fm = new TableViewFocusModel(table);
			fm.focus(0);
			TableViewSelectionModel<Cards> sm = table.getSelectionModel();
			sm.select(0);
			table.setSelectionModel(sm);
			table.setFocusModel(fm);
			
		}
	}

	private void transferToSpringContext() {
		
		partSpeeches = new ArrayList<>();
		senseGroups = new ArrayList<>();
		subGroups = new ArrayList<>();
		
		wrapper.getCards().stream().forEach(item->{
			if (!partSpeeches.contains(item.getPartSpeech())) {
				partSpeeches.add(item.getPartSpeech());
				partSpeechService.save(partSpeeches.get(partSpeeches.size()-1));
			}

			if (!getSenseGroups().contains(item.getSenseGroup())) {
				senseGroups.add(item.getSenseGroup());
				senseGroupService.save(senseGroups.get(senseGroups.size()-1));
			}
			
			if (!getSubGroups().contains(item.getSubGroup())) {
				subGroups.add(item.getSubGroup());
				subGroupService.save(subGroups.get(subGroups.size()-1));
			}
			
			cards.add(new Cards(
					partSpeeches.get(partSpeeches.size()-1),
					senseGroups.get(senseGroups.size()-1),
					subGroups.get(subGroups.size()-1), 
			item.getWord(), 
			item.getTranslate(),
			item.getExample()));
			
			cardsService.save(cards.get(cards.size()-1));
		});
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

	private void translateColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Cards, String> cellEvent;
		cellEvent = (TableColumn.CellEditEvent<Cards, String>) e;
		Cards cards = cellEvent.getRowValue();
		cards.setTranslate(cellEvent.getNewValue());
		notApply.setVisible(true);
		cards.setModificated(true);
		table.refresh();
	}

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
		partSpeechService.save(new PartSpeech("Verb", "Глагол"));
		senseGroupService.save(new SenseGroup("Verb of Stage", "Глаголы стадии"));
		subGroupService.save(new SubGroup("beginning", "Начало"));
		cardsService.save(new Cards(partSpeechService.findById(1), senseGroupService.findById(1),
				subGroupService.findById(1), "begin", "начало", "I begin a talk"));
		cardsService.save(new Cards(partSpeechService.findById(1), senseGroupService.findById(1),
				subGroupService.findById(1), "start", "начинать", "I start"));	
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
