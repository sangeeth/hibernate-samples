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


public class TestGenderType {

	public static void main(String[] args) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.configure();
		
		Session session = sessionFactory.openSession();
		
		Transaction txn = session.beginTransaction();
		try {
			Object [][] rows = {
					{"Sangeeth", Gender.MALE},
					{"Priya", Gender.FEMALE},
					{"Madhumitha", Gender.FEMALE}
			};
			
			for(int i=0;i<rows.length;i++) {
				PersonEntity e = new PersonEntity();
				e.setGender((Gender)rows[i][1]);
				e.setName((String)rows[i][0]);
				
				session.save(e);
			}
			
		} finally {
			txn.commit();
			
			session.close();
		}
		
		session = sessionFactory.openSession();
		
		txn = session.beginTransaction();
		try {
			Query query = session.createQuery("from PersonEntity");
			List<PersonEntity> list = query.list();
			for(PersonEntity e:list) {
				System.out.printf("Name: %s; Gender: %s\n", e.getName(), e.getGender());
			}
			
		} finally {
			txn.commit();
			
			session.close();
		}
		
		session = sessionFactory.openSession();
		
		txn = session.beginTransaction();
		try {
			Query query = session.getNamedQuery("getPersons");
			query.setResultTransformer(Transformers.aliasToBean(Person.class));
			List<Person> list = query.list();
			for(Person e:list) {
				System.out.printf("Name: %s; Gender: %s\n", e.getName(), e.getGender());
			}
			
		} finally {
			txn.commit();
			
			session.close();
		}
	}

}
