package iha.education;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import iha.education.ui.CardsController;
import iha.education.ui.MainController;


@SuppressWarnings("restriction")
@Lazy
@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    @Value("Vocabulary")
    private String windowTitle;

    @Qualifier("mainView")
    @Autowired
    private ConfigurationControllers.View mainView;

    @Qualifier("cardsView")
    @Autowired
    private ConfigurationControllers.View cardsView;

    @Qualifier("editCardView")
    @Autowired
    private ConfigurationControllers.View editCardView;
   
    @Override
    public void start(Stage stage) throws Exception {
    	((MainController) mainView.getController()).setMainApp(this);
    	((CardsController) cardsView.getController()).setMainApp(this);
        
    	Scene scene = new Scene(mainView.getView(),1200,800,true);
    	scene.getStylesheets()
			.add(getClass()
			.getClassLoader()
			.getResource("css/DarkTheme.css")
			.toExternalForm());
    	
    	stage.setTitle(windowTitle);
    	stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

	public ConfigurationControllers.View getMainView() {
		return mainView;
	}

	public ConfigurationControllers.View getCardsView() {
		return cardsView;
	}

	public ConfigurationControllers.View getEditCardView() {
		return editCardView;
	}
 

}
