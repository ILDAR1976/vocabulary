package iha.education.ui;

//Underconstraction

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.FXCollections;

import org.hibernate.type.ClassType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.scene.control.TableView.TableViewFocusModel;
//import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TableView.TableViewSelectionModel;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.entity.Cards;
import iha.education.service.CardsService;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "restriction" })
/**
 * T - entity type, S - service type, C - the source event controller type 
 */
public class EditCardController<T,S,C> {

	private Logger logger = LoggerFactory.getLogger(EditCardController.class);
      
    @FXML private BorderPane editPane;
    
    @FXML private TableView<T> table;

    @FXML private Label information;
    
    private ObservableList<T> data;
	private TableViewSelectionModel<T> selectionModel;
    private Boolean insertButton = false;
    private Class<T> type;
    @Autowired
    private T entity;
    @Autowired
    private S service;
    @Autowired 
    private C controller;
    @Autowired
    private MainController mainController;
    @Autowired
    private PartSpeechService partSpeechService;
    @Autowired
    private CardsService cardsService;
    
    public BorderPane getEditPane() {
    	return editPane;
    }
    
    @FXML
    public void handleCloseTable() {
    	this.editPane.setVisible(false);
    	if (this.controller instanceof CardsController) {
    		CardsController cardsCntl = (CardsController) controller;
    		cardsCntl.getNotApply().setVisible(true);
    		cardsCntl.getTable().refresh();
    		mainController.getMainFrame().setCenter(cardsCntl.getCardsAnchor());
    		cardsCntl.getTable().requestFocus();
    	}
    	setInsertButton(false);
    }
    
    @FXML
    public void handleOk() {
    	if (this.controller instanceof CardsController) {
    		CardsController cardsCntl = (CardsController) controller;
    		entity = (T) table.getFocusModel().getFocusedItem();
    		
	    	if (getInsertButton()) {
	    		if (entity instanceof PartSpeech){
	    			cardsCntl.setTxtPartOfSpeech(entity.toString());
	    			cardsCntl.setCurrentPartSpeech((PartSpeech)entity);
	    		} else if (entity instanceof SenseGroup) {
	    			cardsCntl.setTxtSenseGroup(entity.toString());
	    			cardsCntl.setCurrentSenseGroup((SenseGroup) entity);
	    		} else if (entity instanceof SubGroup) {
	    			cardsCntl.setTxtSubGroup(entity.toString());
	    			cardsCntl.setCurrentSubGroup((SubGroup) entity);
	    		}
	    		cardsCntl.getNotApply().setVisible(true);
	    		cardsCntl.getTable().refresh();
	    	}
	    	mainController.getMainFrame().setCenter(cardsCntl.getCardsAnchor());
    	}
    	
    	setInsertButton(false);
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
    @PostConstruct
    public void init() {
 		  	
       	TableColumn<T, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<T, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(e -> nameColumn_OnEditCommit(e));

        TableColumn<T, String> translateColumn = new TableColumn<>("Translate");
        translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));
		translateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		translateColumn.setOnEditCommit(e -> translateColumn_OnEditCommit(e));
        table.getColumns().setAll(idColumn,  nameColumn, translateColumn);
        TableViewFocusModel<T> fm = new TableViewFocusModel(table);
		fm.focus(0);
        TableViewSelectionModel<T> sm = table.getSelectionModel();
		sm.select(0);
		
    }

    @SuppressWarnings("unchecked")
	public void update() {
    	if (service instanceof PartSpeechService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList(((CardsController) controller).getPartSpeeches());
    		this.table.setItems(data);
    		this.table.refresh();
     		this.information.setText("THE part of speech edit window");
    	} else if (service instanceof SenseGroupService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList(((CardsController) controller).getSenseGroups());
    		this.table.setItems(data);
    		this.table.refresh();
     		this.information.setText("THE sense group edit window");
    	} else if (service instanceof SubGroupService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList(((CardsController) controller).getSubGroups());
    		this.table.setItems(data);
    		this.table.refresh();
     		this.information.setText("THE subgroup edit window");
    	}

    	table.requestFocus();
		TableViewFocusModel<T> fm = new TableViewFocusModel(table);
		fm.focus(0);
		TableViewSelectionModel<T> sm = table.getSelectionModel();
		sm.select(0);
		table.setSelectionModel(sm);
		table.setFocusModel(fm);

    }
    
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public S getService() {
		return service;
	}

	public void setService(S service) {
		this.service = service;
	}

	public C getContoller() {
		return controller;
	}

	public void setContoller(C controller) {
		this.controller = controller;
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	public Boolean getInsertButton() {
		return insertButton;
	}

	public void setInsertButton(Boolean insertButton) {
		this.insertButton = insertButton;
	}
	
	public TableView<T> getTable() {
		return table;
	}
	

	@SuppressWarnings("unchecked")
	private void nameColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<T, String> ce;
		ce = (TableColumn.CellEditEvent<T, String>) e;
		T name = ce.getRowValue();
		if (name instanceof PartSpeech) {
			((PartSpeech) name).setName(ce.getNewValue());
		} else if (name instanceof SenseGroup) {
			((SenseGroup) name).setName(ce.getNewValue());
		} else if (name instanceof SubGroup) {
			((SubGroup) name).setName(ce.getNewValue());
		}
		
		//notApply.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void translateColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<T, String> ce;
		ce = (TableColumn.CellEditEvent<T, String>) e;
		T translate = ce.getRowValue();
		if (translate instanceof PartSpeech) {
			((PartSpeech) translate).setTranslate(ce.getNewValue());
		} else if (translate instanceof SenseGroup) {
			((SenseGroup) translate).setTranslate(ce.getNewValue());
		} else if (translate instanceof SubGroup) {
			((SubGroup) translate).setTranslate(ce.getNewValue());
		}
		
		//notApply.setVisible(true);
	}
	
}
