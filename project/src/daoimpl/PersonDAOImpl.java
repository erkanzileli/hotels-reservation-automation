package daoimpl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dao.PersonDAO;
import entity.Person;

public class PersonDAOImpl implements PersonDAO {

	@Override
	public void create(Person t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

	@Override
	public List<Person> read() {
		TypedQuery<Person> query = (TypedQuery<Person>) entityManager.createNativeQuery("select * from person");
		return query.getResultList();
	}

	@Override
	public Person readOne(long id) {
		TypedQuery<Person> query = (TypedQuery<Person>) entityManager
				.createNativeQuery("select * from person where idPerson=?1");
		query.setParameter(1, id);
		return query.getSingleResult();
	}

	@Override
	public void update(Person t) {
		Query query = entityManager
				.createNativeQuery("update person set name=?1, set surname=?2, birthDate=?3, phoneNumber=?4");
		
	}

	@Override
	public void delete(Person t) {
		// TODO Auto-generated method stub

	}

}
