package dao;

import java.util.List;

import javax.persistence.EntityManager;

import utility.EntityManagerUtility;

public interface BaseDAO<T> {

	EntityManager entityManager = EntityManagerUtility.getEntityManager();

	void create(T t);

	List<T> read();

	T readOne(long id);

	void update(T t);

	void delete(T t);
}
