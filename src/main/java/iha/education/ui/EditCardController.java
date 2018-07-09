package iha.education.ui;

//Underconstraction

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "restriction" })
/**
 * T - Entity type, S - Service type 
 */
public class EditCardController<T,S> {

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(EditCardController.class);
      
    @Autowired 
    private S service;
     
    @FXML private AnchorPane entityTypeAnchor;
    
    @FXML private TableView<T> table;
 
    // Variables
    private ObservableList<T> data;

  
    @FXML
    public void initialize() {
    }

    @Autowired 
    public AnchorPane getEntityTypeAnchor() {
    	
    	return entityTypeAnchor;
    }
    
    public void handleCloseTable() {
    	this.entityTypeAnchor.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {

		List<T> entityType = new ArrayList<T>();    	
       	TableColumn<T, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<T, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<T, String> translateColumn = new TableColumn<>("Translate");
        translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));
        
        table.getColumns().setAll(idColumn,  nameColumn, translateColumn);

        table.setItems(data);
        
    }

   
}
