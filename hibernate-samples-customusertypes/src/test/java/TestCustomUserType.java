import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;

import com.sangeethlabs.hibernate.samples.Gender;
import com.sangeethlabs.hibernate.samples.HibernateUtil;
import com.sangeethlabs.hibernate.samples.Person;
import com.sangeethlabs.hibernate.samples.PersonEntity;
import com.sangeethlabs.hibernate.samples.Occupation;


public class TestCustomUserType {

	public static void main(String[] args) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.configure();
		
		Session session = sessionFactory.openSession();
		
		// Populate test data
		Transaction txn = session.beginTransaction();
		try {
			Object [][] rows = {
					{"Gandhi", Gender.MALE, Occupation.POLITICS, false},
					{"Bose", Gender.MALE, Occupation.POLITICS, false },
					{"Gosling", Gender.MALE, Occupation.SCIENTIST, true},
					{"Shetty", Gender.MALE, Occupation.DOCTOR, true},
					{"Curie", Gender.FEMALE, Occupation.SCIENTIST, false}
			};
			
			for(int i=0;i<rows.length;i++) {
				PersonEntity e = new PersonEntity();
				e.setName((String)rows[i][0]);
				e.setGender((Gender)rows[i][1]);
				e.setOccupation((Occupation)rows[i][2]);
				e.setAlive((Boolean)rows[i][3]);
				
				session.save(e);
			}
			
		} finally {
			txn.commit();
			
			session.close();
		}
		
		// Fetch the persons using HQL
		session = sessionFactory.openSession();
		
		txn = session.beginTransaction();
		try {
			Query query = session.createQuery("from PersonEntity");
			List<PersonEntity> list = query.list();
			for(PersonEntity e:list) {
				System.out.printf("Name: %s; Gender: %s; Occupation: %s; Alive: %s\n", 
				                  e.getName(), e.getGender(), e.getOccupation(), e.isAlive());
			}
			
		} finally {
			txn.commit();
			
			session.close();
		}
		
		// Fetch the persons using ANSI SQL
		session = sessionFactory.openSession();
		
		txn = session.beginTransaction();
		try {
			Query query = session.getNamedQuery("getPersons");
			query.setResultTransformer(Transformers.aliasToBean(Person.class));
			List<Person> list = query.list();
            for(Person e:list) {
                System.out.printf("Name: %s; Gender: %s; Occupation: %s; Alive: %s\n", 
                                  e.getName(), e.getGender(), e.getOccupation(), e.isAlive());
            }
			
		} finally {
			txn.commit();
			
			session.close();
		}
	}
	
	

}
