package iha.education.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtils {
 
	private static final SessionFactory sessionFactory = buildSessionFactory();
 
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration oConf = new Configuration();
	        String sDir = "./mine";
	        if(System.getenv("OPENSHIFT_DATA_DIR") != null){
	        	sDir = System.getenv("OPENSHIFT_DATA_DIR");
	        }
	        String sUrl = String.format("jdbc:h2:%sMyDbTest", sDir);
			oConf.setProperty("hibernate.connection.url", sUrl);
			
			return oConf.configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
 
}