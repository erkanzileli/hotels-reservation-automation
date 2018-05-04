package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtility {

	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysqlPersistenceUnit");

	private EntityManagerUtility() {
	}

	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
