package iha.education;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import iha.education.ui.CardsController;
import iha.education.ui.MainController;

import java.io.IOException;
import java.io.InputStream;


@SuppressWarnings("restriction")
@Configuration
public class ConfigurationControllers {

    @Bean(name = "mainView")
    public View getMainView() throws IOException {
        return loadView("fxml/main.fxml");
    }
	
    @Bean(name = "cardsView")
    public View getCardsView() throws IOException {
        return loadView("fxml/cards.fxml");
    }
	
    @Bean(name = "checkCardView")
    public View getCheckCardView() throws IOException {
        return loadView("fxml/checkCard.fxml");
    }

    @Bean(name = "searchView")
    public View getSearchView() throws IOException {
        return loadView("fxml/search.fxml");
    }
    
    @Bean
    public CardsController getCardsController() throws IOException {
        return (CardsController) getCardsView().getController();
    }
    
    @Bean
    public MainController getMainController() throws IOException {
        return (MainController) getMainView().getController();
    }

    protected View loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new View(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    public class View {
        private Parent view;
        private Object controller;

        public View(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }

}
