package com.softserve.auction.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.Bet;

@Repository
@Transactional
public class BetDaoImpl implements Dao<Bet> {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(Bet bet) {
		sessionFactory.getCurrentSession().save(bet);
	}

	@Override
	public void delete(Long id) {
		Bet bet = (Bet) sessionFactory.getCurrentSession().get(Bet.class, id);
		if (bet.getBetId() != null) {
			sessionFactory.getCurrentSession().delete(bet);
		}

	}

	@Override
	public void update(Long id) {
		Bet bet = (Bet) sessionFactory.getCurrentSession().get(Bet.class, id);
		if (bet.getBetId() != null) {
			sessionFactory.getCurrentSession().update(bet);
		}
	}

	@Override
	public void updateByQuery(String query) {
		sessionFactory.getCurrentSession().createSQLQuery(query)
				.addEntity(Bet.class).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bet> findAll() {
		List<Bet> bets = (List<Bet>) sessionFactory.getCurrentSession()
				.createCriteria(Bet.class).list();
		return bets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bet> findByQuery(String query) {
		List<Bet> bets = (List<Bet>) sessionFactory.getCurrentSession()
				.createSQLQuery(query).addEntity(Bet.class).list();
		return bets;
	}

	@Override
	public List<Bet> findByPage(int firstResult, int pageSize, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bet findById(Long id) {
		return (Bet) sessionFactory.getCurrentSession().get(Bet.class, id);
	}

}
