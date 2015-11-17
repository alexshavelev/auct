package com.softserve.auction.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.Lot;

@Repository
@Transactional
public class LotDaoImpl implements Dao<Lot> {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void save(Lot lot) {
		sessionFactory.getCurrentSession().save(lot);
	}

	@Override
	public void delete(Long id) {
		Lot lot = (Lot) sessionFactory.getCurrentSession().get(Lot.class, id);
		if (lot.getLotId() != null) {
			sessionFactory.getCurrentSession().delete(lot);
		}
	}

	@Override
	public void update(Long id) {
		Lot lot = (Lot) sessionFactory.getCurrentSession().get(Lot.class, id);
		if (lot.getLotId() != null) {
			sessionFactory.getCurrentSession().update(lot);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lot> findAll() {
		List<Lot> lots = (List<Lot>) sessionFactory.getCurrentSession()
				.createCriteria(Lot.class).list();
		return lots;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lot> findByQuery(String query) {
		List<Lot> lots = (List<Lot>) sessionFactory.getCurrentSession()
				.createSQLQuery(query).addEntity(Lot.class).list();
		return lots;
	}

	@Override
	public void updateByQuery(String query) {
		sessionFactory.getCurrentSession().createSQLQuery(query)
				.addEntity(Lot.class).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lot> findByPage(int firstResult, int pageSize, String queryLot) {
		return sessionFactory.getCurrentSession().createSQLQuery(queryLot)
				.addEntity(Lot.class).setFirstResult(firstResult)
				.setMaxResults(pageSize).list();
	}

	@Override
	public Lot findById(Long id) {
		return (Lot) sessionFactory.getCurrentSession().get(Lot.class, id);
	}

}
