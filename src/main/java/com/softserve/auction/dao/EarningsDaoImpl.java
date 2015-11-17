package com.softserve.auction.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.Earnings;

@Repository
@Transactional
public class EarningsDaoImpl implements Dao<Earnings> {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(Earnings earnings) {
		sessionFactory.getCurrentSession().save(earnings);
	}

	@Override
	public void delete(Long id) {
		//
	}

	@Override
	public void update(Long id) {
		//
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Earnings> findAll() {
		List<Earnings> earnings = (List<Earnings>) sessionFactory.getCurrentSession()
				.createCriteria(Earnings.class).list();
		return earnings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Earnings> findByQuery(String query) {
		List<Earnings> earnings = (List<Earnings>) sessionFactory.getCurrentSession()
				.createSQLQuery(query).addEntity(Earnings.class).list();
		return earnings;
	}

	@Override
	public void updateByQuery(String query) {
		//		
	}

	@Override
	public List<Earnings> findByPage(int firstResult, int pageSize, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Earnings findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
