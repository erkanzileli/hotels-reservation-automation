package daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.HotelDAO;
import entity.Hotel;
import utility.EntityManagerUtility;

public class HotelDAOImpl implements HotelDAO {

	private EntityManager entityManager;

	public HotelDAOImpl() {
		entityManager = EntityManagerUtility.createEntityManager();
	}

	@Override
	public void create(Hotel t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

	@Override
	public List<Hotel> read() {
		Query query = entityManager.createNativeQuery("SELECT * FROM Hotel", Hotel.class);
		return query.getResultList();
	}

	@Override
	public Hotel readOne(long id) {
		Query query = entityManager.createNativeQuery("SELECT * FROM Hotel WHERE idHotel=?1", Hotel.class);
		query.setParameter(1, id);
		return (Hotel) query.getSingleResult();
	}

	@Override
	public void update(Hotel t) {
		Hotel h = readOne(t.getIdHotel());
		if (h != null) {
			entityManager.getTransaction().begin();
			h.setIdCompany(t.getIdCompany());
			h.setName(t.getName());
			entityManager.getTransaction().commit();
		}
	}

	@Override
	public void delete(Hotel t) {
		Hotel h = readOne(t.getIdHotel());
		if(h!=null) {
			entityManager.getTransaction().begin();
			entityManager.remove(h);
			entityManager.getTransaction().commit();
		}
	}

}
