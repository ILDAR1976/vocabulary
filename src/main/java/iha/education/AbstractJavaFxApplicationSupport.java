package iha.education;

import javafx.application.Application;
import javafx.application.Preloader;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.sun.javafx.application.LauncherImpl;

import iha.education.utils.Utils;

@SuppressWarnings("restriction")
public abstract class AbstractJavaFxApplicationSupport extends Application {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final int COUNT_LIMIT = 500000;
    private static int stepCount = 1;
    
    private static String[] savedArgs;

    protected ConfigurableApplicationContext context;
 
    public static String STEP() {
        return stepCount++ + ". ";
    }
    
 
    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    
    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass,Class<? extends Preloader> appPreloaderClass  , String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        LauncherImpl.launchApplication(appClass, appPreloaderClass, args);
    }
}
