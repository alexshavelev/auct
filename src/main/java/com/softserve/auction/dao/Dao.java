package com.softserve.auction.dao;

import java.util.List;

public interface Dao<E> {
	void save(E element);

	void delete(Long id);

	void update(Long id);
	
	void updateByQuery(String query);

	List<E> findAll();
	
	E findById(Long id);
	
	List<E> findByQuery(String query);
	
	List<E> findByPage(int firstResult, int pageSize, String query);

}
