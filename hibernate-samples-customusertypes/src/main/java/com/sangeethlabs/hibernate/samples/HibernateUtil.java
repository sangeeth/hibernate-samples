package com.sangeethlabs.hibernate.samples;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    public static SessionFactory configure() throws Exception {
    	AnnotationConfiguration configuration = new AnnotationConfiguration();
    	configuration.configure();
    	
    	return configuration.buildSessionFactory();
    }

}
