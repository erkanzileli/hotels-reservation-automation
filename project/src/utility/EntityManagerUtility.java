package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtility {

	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnitName");

	private EntityManagerUtility() {
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
