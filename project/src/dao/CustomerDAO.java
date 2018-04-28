package dao;

import java.util.List;

import entity.Customer;

public interface CustomerDAO extends BaseDAO<Customer> {

	List<Customer> getByFilter();
	//TODO insert necessary parameters

}
