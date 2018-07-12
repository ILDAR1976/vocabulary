package iha.education.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import iha.education.Application;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings({ "restriction"  })
public class MainController {
    private Application mainApp;
    
    @FXML
    private BorderPane mainFrame;
    
    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() throws JAXBException, URISyntaxException {
	        
    }

    @FXML
    public void handleShowCardsDialog() throws IOException {
    	mainFrame.setCenter(null);
    	CardsController cards = (CardsController)mainApp.getCardsView().getController();
    	cards.getCardsAnchor().setVisible(true);
    	mainFrame.setCenter(mainApp.getCardsView().getView());
    	cards.setFocus();
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Vocabulary");
        alert.setHeaderText("About");
        alert.setContentText("Author: iha akhmetov.ih@yandex.ru");
        alert.showAndWait();
    }
    
    public void setMainApp(Application mainApp) {
        this.mainApp = mainApp;
    }

	public BorderPane getMainFrame() {
		return mainFrame;
	}
    
    
}
