package iha.education.ui;

//Underconstraction

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.beans.property.*;
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
import iha.education.utils.CardsListWrapper;
import iha.education.utils.Utils;
import javafx.collections.FXCollections;
import static iha.education.utils.Utils.*;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "restriction" })
public class PropertiesController {

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @Autowired 
    private PartSpeechService partSpeechService;
    
    @Autowired 
    private SenseGroupService senseGroupService;
    
    @Autowired 
    private SubGroupService subGroupService;
    
    @Autowired 
    private CardsService cardsService;
    
    @FXML private AnchorPane cardsAnchor;
    
    @FXML private TableView<Cards> table;
 
    // Variables
    private ObservableList<Cards> data;
    
     
    private CardsListWrapper wrapper;
    
  
    @FXML
    public void initialize() {
    }

    @Autowired 
    public AnchorPane getCardsAnchor() {
    	
    	return cardsAnchor;
    }
    
    public void handleCloseTable() {
    	this.cardsAnchor.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() throws JAXBException, URISyntaxException {
			File file = Utils.getVocabularyFile();
			List<Cards> cards = new ArrayList<Cards>();    	
    	switch (2) {
    		case 1:
    		   	partSpeechService.save(new PartSpeech("Verb", "Глагол"));
    	   		senseGroupService.save(new SenseGroup("Verb of Stage", "Глаголы стадии"));
    	    	subGroupService.save(new SubGroup("beginning", "Начало"));
    	    	cardsService.save(new Cards(partSpeechService.findById(1), senseGroupService.findById(1), subGroupService.findById(1), "begin", "начало","I begin a talk"));
    	    	cardsService.save(new Cards(partSpeechService.findById(1), senseGroupService.findById(1), subGroupService.findById(1), "start", "начинать","I start"));
    	    	cards = cardsService.findAll();
    	        data = FXCollections.observableArrayList(cards);
    	        wrapper = new CardsListWrapper(); 
    	    	wrapper.setCards(data);
    	    	saveCards(file, wrapper);
    	    	
   			break;
    		case 2:
    			wrapper = new CardsListWrapper(FXCollections.observableArrayList(cards));
    		    wrapper = loadCards(file, wrapper);
    		    transferToSpringContext();
    	    	cards = cardsService.findAll();
      	        data = FXCollections.observableArrayList(cards);
    	        break;
    	}
    	
    	TableColumn<Cards, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Cards, String> partSpeechColumn = new TableColumn<>("Parts of Speech");
        partSpeechColumn.setCellValueFactory(new PropertyValueFactory<>("partSpeech"));

        TableColumn<Cards, String> senseGroupColumn = new TableColumn<>("Sense group");
        senseGroupColumn.setCellValueFactory(new PropertyValueFactory<>("senseGroup"));

        TableColumn<Cards, String> subGroupColumn = new TableColumn<>("Subgroup");
        subGroupColumn.setCellValueFactory(new PropertyValueFactory<>("subGroup"));

        TableColumn<Cards, String> NameColumn = new TableColumn<>("Name");
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Cards, String> translateColumn = new TableColumn<>("Translate");
        translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));

        TableColumn<Cards, String> exampleColumn = new TableColumn<>("Example");
        exampleColumn.setCellValueFactory(new PropertyValueFactory<>("example"));
       
        partSpeechColumn.setCellValueFactory(new Callback<CellDataFeatures<Cards, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cards, String> param) {
                Cards cards = param.getValue();
                SimpleStringProperty stringProp = new SimpleStringProperty(cards.getPartSpeech().getName());
                return stringProp;
            }
        });

        
        senseGroupColumn.setCellValueFactory(new Callback<CellDataFeatures<Cards, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cards, String> param) {
                Cards cards = param.getValue();
                SimpleStringProperty stringProp = new SimpleStringProperty(cards.getSenseGroup().getName());
                return stringProp;
            }
        });


        subGroupColumn.setCellValueFactory(new Callback<CellDataFeatures<Cards, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cards, String> param) {
                Cards cards = param.getValue();
                SimpleStringProperty stringProp = new SimpleStringProperty(cards.getSubGroup().getName());
                return stringProp;
            }
        });

        table.getColumns().setAll(idColumn, partSpeechColumn, senseGroupColumn, subGroupColumn, NameColumn, translateColumn, exampleColumn);

        table.setItems(data);
        
    }

    private void transferToSpringContext() {
   		for (Cards item: wrapper.getCards()) {
			partSpeechService.save(item.getPartSpeech());
			senseGroupService.save(item.getSenseGroup());
			subGroupService.save(item.getSubGroup());
			cardsService.save(new Cards(partSpeechService.findById(item.getPartSpeech().getId()), 
					                    senseGroupService.findById(item.getSenseGroup().getId()), 
					                    subGroupService.findById(item.getSubGroup().getId()), 
					                    item.getWord(), 
					                    item.getTranslate(),
					                    item.getExample()));
		}
   	
    }
}
