package iha.education.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import iha.education.Application;
import iha.education.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings({ "restriction" })
public class MainController {
	private Application mainApp;
	private String body = "";
	
	@FXML
	private BorderPane mainFrame;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	public void initialize() {
		mainAnchor.setStyle("-fx-background-color: #3c3c3c;");

	}

	@PostConstruct
	public void init() throws JAXBException, URISyntaxException {

	}

	@FXML
	public void handleShowCardsDialog() throws IOException {
		mainFrame.setCenter(null);
		CardsController cards = (CardsController) mainApp.getCardsView().getController();
		cards.getCardsAnchor().setVisible(true);
		mainFrame.setCenter(mainApp.getCardsView().getView());
		cards.update();
	}

	public void handleShowCheckCardDialog() throws IOException {
		mainFrame.setCenter(null);
		CheckCardController checkCard = (CheckCardController) mainApp.getCheckCardView().getController();
		checkCard.getCheckCardAnchor().setVisible(true);
		mainFrame.setCenter(mainApp.getCheckCardView().getView());
		checkCard.update();
	}

	public void handleImportToSql() throws IOException {
		CardsController cards = (CardsController) mainApp.getCardsView().getController();
		body += "DELETE FROM `dictionary`;\r\n";
		body += "INSERT INTO `dictionary` (`id`, `partspeech`, `wgroup`, `wsubgroup`, `englishword`, `russianword`, `addition`) VALUES\r\n";
		cards.getCards().stream().forEach(e->{
			body += "(" + e.getId() + ", "
					    + e.getPartSpeech().getId() + ", "
					    + e.getSenseGroup().getId() + ", "
					    + e.getSubGroup().getId() + ", '" 
					    + e.getWord().replace("'", "`") + "', '"
					    + e.getTranslate().replace("'", "`") + "','"
					    + e.getExample() + "'),\r\n";
		});
		
		body = body.substring(0,body.length()-3);
		body += ";\r\n";
		
		body += "DELETE FROM `partspeech`;\r\n";
		body += "INSERT INTO `partspeech` (`id`, `name`, `translate`) VALUES\r\n";		
		cards.getPartSpeeches().stream().forEach(e->{
			body += "(" + e.getId() + ", '"
				    + e.getName().replace("'", "`") + "','"
				    + e.getTranslate().replace("'", "`") + "'),\r\n";			
		});
		body = body.substring(0,body.length()-3);
		body += ";\r\n";

		body += "DELETE FROM `wgroup`;\r\n";
		body += "INSERT INTO `wgroup` (`id`, `name`, `translate`) VALUES\r\n";		
		cards.getSenseGroups().stream().forEach(e->{
			body += "(" + e.getId() + ", '"
				    + e.getName().replace("'", "`") + "','"
				    + e.getTranslate().replace("'", "`") + "'),\r\n";			
		});
		body = body.substring(0,body.length()-3);
		body += ";\r\n";


		body += "DELETE FROM `wsubgroup`;\r\n";
		body += "INSERT INTO `wsubgroup` (`id`, `name`, `translate`) VALUES\r\n";		
		cards.getSubGroups().stream().forEach(e->{
			body += "(" + e.getId() + ", '"
				    + e.getName().replace("'", "`") + "','"
				    + e.getTranslate().replace("'", "`") + "'),\r\n";			
		});
		body = body.substring(0,body.length()-3);
		body += ";\r\n";


		Utils.write("./vacabulary.sql", body);
		cards.update();
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
