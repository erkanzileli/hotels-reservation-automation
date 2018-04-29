package daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.CustomerDAO;
import entity.Customer;
import utility.EntityManagerUtility;

public class CustomerDAOImpl implements CustomerDAO {

	EntityManager entityManager;

	public CustomerDAOImpl() {
		entityManager = EntityManagerUtility.createEntityManager();
	}

	@Override
	public void create(Customer t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

	@Override
	public List<Customer> read() {
		String sqlString = "select * from customer";
		TypedQuery<Customer> query = ((TypedQuery<Customer>) entityManager.createNativeQuery(sqlString));
		return query.getResultList();
	}

	@Override
	public Customer readOne(long id) {
		TypedQuery<Customer> query = (TypedQuery<Customer>) entityManager
				.createNativeQuery("select * from customer where tc=?1");
		query.setParameter(1, id);
		return query.getSingleResult();
	}

	@Override
	public void update(Customer t) {

	}

	@Override
	public void delete(Customer t) {
		Customer c = entityManager.find(Customer.class, t.getTc());
		if (c != null)
			entityManager.remove(t);
	}

	@Override
	public List<Customer> getByFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
