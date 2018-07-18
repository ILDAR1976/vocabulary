package iha.education;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import javafx.embed.swing.JFXPanel;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings({ "unused", "restriction" })
@RunWith(SpringJUnit4ClassRunner.class)

public class ApplicationTests {

    @BeforeClass
    public static void bootstrapJavaFx(){
         new JFXPanel();
    }

    @Test
	public void contextLoads() {
	}

}
