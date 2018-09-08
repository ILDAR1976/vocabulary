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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.entity.Cards;
import iha.education.service.PartSpeechService;
import iha.education.service.SenseGroupService;
import iha.education.service.SubGroupService;
import javax.annotation.PostConstruct;
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
    
    @FXML private TextField txtName;
    
    @FXML private TextField txtTranslate;
    
    private ObservableList<T> data;
    private Boolean insertButton = false;

    @Autowired
    private CardsController cardsController;
    @Autowired
    private MainController mainController;
    @Autowired
    private PartSpeechService partSpeechService;
    @Autowired
    private SenseGroupService senseGroupService;
    @Autowired
    private SubGroupService subGroupService;

    private T entity;
    private S service;
    private C controller;
    

    public BorderPane getEditPane() {
    	return editPane;
    }
    
    @FXML
    public void handleCloseTable() {
    	this.editPane.setVisible(false);
    	if (this.controller instanceof CardsController) {
    		CardsController cardsCntl = (CardsController) controller;
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
	    	} else {
	    		
	    		Boolean flag = true;
	    		if (entity instanceof PartSpeech){
	    			PartSpeech oldValue = cardsCntl.getTable().getFocusModel().getFocusedItem().getPartSpeech();
	    			cardsCntl.getTable().getFocusModel().getFocusedItem().setPartSpeech((PartSpeech) entity);
	    			cardsCntl.getPartSpeechService().save((PartSpeech)entity);
	    			if ( ((PartSpeech) entity).getName().equals(oldValue.getName()) &&
	    				 ((PartSpeech) entity).getTranslate().equals(oldValue.getTranslate())	) {
	    				flag = false;
	    			}
	    		} else if (entity instanceof SenseGroup) {
	    			SenseGroup oldValue = cardsCntl.getTable().getFocusModel().getFocusedItem().getSenseGroup();
	    			cardsCntl.getTable().getFocusModel().getFocusedItem().setSenseGroup((SenseGroup) entity);
	    			cardsCntl.getSenseGroupService().save((SenseGroup) entity);
	    			if ( ((SenseGroup) entity).getName().equals(oldValue.getName()) &&
		    				 ((SenseGroup) entity).getTranslate().equals(oldValue.getTranslate())	) {
		    				flag = false;
		    			}
	    		} else if (entity instanceof SubGroup) {
	    			SubGroup oldValue = cardsCntl.getTable().getFocusModel().getFocusedItem().getSubGroup();
	    			cardsCntl.getTable().getFocusModel().getFocusedItem().setSubGroup((SubGroup) entity);
	    			cardsCntl.getSubGroupService().save((SubGroup) entity);
	    			if ( ((SubGroup) entity).getName().equals(oldValue.getName()) &&
		    				 ((SubGroup) entity).getTranslate().equals(oldValue.getTranslate())	) {
		    				flag = false;
		    			}
	    		}
	    		
	    		if (flag) {
	    			cardsCntl.getNotApply().setVisible(true);
	    			cardsCntl.getTable().getFocusModel().getFocusedItem().setModificated(true);
	    			cardsCntl.saveCurrentRow(cardsCntl.getTable().getFocusModel().getFocusedItem());
	    			cardsCntl.refresh();
	    		}
	    	}
	    	
	    	cardsCntl.getTable().refresh();
	    	mainController.getMainFrame().setCenter(cardsCntl.getCardsAnchor());
    	}
    	
    	setInsertButton(false);
    }
   
	@SuppressWarnings("unchecked")
	@FXML
	private void handleInsertRow() {
		CardsController cntrl = (CardsController) controller;
		int mainIndex = 0;
		if (service instanceof PartSpeechService) {
			List<PartSpeech> ps = cntrl.getPartSpeeches();
			ps.add(new PartSpeech(txtName.getText(), txtTranslate.getText()));
			int index = ps.size()-1;
			mainIndex = index;
			partSpeechService.save(ps.get(index));
			data.add((T) ps.get(index));
			refreshAndSelect(index);
		} else if (service instanceof SenseGroupService) {
			List<SenseGroup> sg = cntrl.getSenseGroups();
			sg.add(new SenseGroup(txtName.getText(), txtTranslate.getText()));
			int index = sg.size()-1;
			mainIndex = index;
			senseGroupService.save(sg.get(index));
			data.add((T) sg.get(index));			
			refreshAndSelect(index);
		} else if (service instanceof SubGroupService) {
			List<SubGroup> sug = cntrl.getSubGroups();
			sug.add(new SubGroup(txtName.getText(), txtTranslate.getText()));
			int index = sug.size()-1;
			mainIndex = index;
			subGroupService.save(sug.get(index));
			data.add((T) sug.get(index));			
			refreshAndSelect(sug.size()-1);
		}

		txtName.setText("");
		txtTranslate.setText("");
		table.refresh();
		
	}

    @SuppressWarnings({ "unchecked" })
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
        
        TableViewSelectionModel<T> sm = table.getSelectionModel();
		sm.select(0);
   }

    public void setEditable() {
    	if (getInsertButton()) {
    		table.setEditable(false);
    	} else {
    		table.setEditable(true);
    	}
    }
    
    @SuppressWarnings("unchecked")
	public void update() {
    	CardsController cntrl = (CardsController) controller;
    	if (service instanceof PartSpeechService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList((cntrl).getPartSpeeches());
    		this.table.setItems(data);
    		TableViewSelectionModel<T> sm = table.getSelectionModel();
    		Cards crd = cntrl.getTable().getFocusModel().getFocusedItem();
    		sm.select((T)crd.getPartSpeech());
    		table.setSelectionModel(sm);
    		this.table.refresh();
     		this.information.setText("The part of speech edit window");
     		this.information.setStyle(" -fx-text-fill: #d8d8d8;");
    	} else if (service instanceof SenseGroupService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList((cntrl).getSenseGroups());
    		this.table.setItems(data);
    		TableViewSelectionModel<T> sm = table.getSelectionModel();
    		Cards crd = cntrl.getTable().getFocusModel().getFocusedItem();
    		sm.select((T)crd.getSenseGroup());
    		table.setSelectionModel(sm);
    		this.table.refresh();
     		this.information.setText("The sense group edit window");
     		this.information.setStyle(" -fx-text-fill: #d8d8d8;");
    	} else if (service instanceof SubGroupService) {
    		this.data = (ObservableList<T>) FXCollections.observableArrayList((cntrl).getSubGroups());
    		this.table.setItems(data);
    		TableViewSelectionModel<T> sm = table.getSelectionModel();
    		Cards crd = cntrl.getTable().getFocusModel().getFocusedItem();
    		sm.select((T)crd.getSubGroup());
    		table.setSelectionModel(sm);
    		this.table.refresh();
     		this.information.setText("The subgroup edit window");
     		this.information.setStyle(" -fx-text-fill: #d8d8d8;");
    	}
    	table.requestFocus();
    	table.scrollTo(this.data.size()-1);
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
	
	private void refreshAndSelect(int index) {
		table.refresh();
		TableViewSelectionModel<T> sm = table.getSelectionModel();
		sm.select(index);
	}

	@SuppressWarnings("unchecked")
	private void nameColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<T, String> ce;
		ce = (TableColumn.CellEditEvent<T, String>) e;
		T name = ce.getRowValue();
		if (name instanceof PartSpeech) {
			((PartSpeech) name).setName(ce.getNewValue());
			((PartSpeech) name).setModificated(true);
		} else if (name instanceof SenseGroup) {
			((SenseGroup) name).setName(ce.getNewValue());
			((SenseGroup) name).setModificated(true);
		} else if (name instanceof SubGroup) {
			((SubGroup) name).setName(ce.getNewValue());
			((SubGroup) name).setModificated(true);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void translateColumn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<T, String> ce;
		ce = (TableColumn.CellEditEvent<T, String>) e;
		T translate = ce.getRowValue();
		if (translate instanceof PartSpeech) {
			((PartSpeech) translate).setTranslate(ce.getNewValue());
			((PartSpeech) translate).setModificated(true);
		} else if (translate instanceof SenseGroup) {
			((SenseGroup) translate).setTranslate(ce.getNewValue());
			((SenseGroup) translate).setModificated(true);
		} else if (translate instanceof SubGroup) {
			((SubGroup) translate).setTranslate(ce.getNewValue());
			((SubGroup) translate).setModificated(true);
		}
		
	}
	
}
