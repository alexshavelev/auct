package com.softserve.auction.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.User;

@Repository
@Transactional
public class UserDaoImpl implements Dao<User> {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void delete(Long id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class,
				id);
		if (user != null) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public void update(Long id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class,
				id);
		if (user != null) {
			sessionFactory.getCurrentSession().update(user);
		}
	}

	@Override
	public void updateByQuery(String query) {
		sessionFactory.getCurrentSession().createSQLQuery(query)
				.addEntity(User.class).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		List<User> users = (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class).list();
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByQuery(String query) {
		List<User> users = (List<User>) sessionFactory.getCurrentSession()
				.createSQLQuery(query).addEntity(User.class).list();
		return users;
	}

	@Override
	public List<User> findByPage(int firstResult, int pageSize, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findById(Long id) {
		String query = "SELECT * FROM user WHERE userid =" + id ;
		return (User) sessionFactory.getCurrentSession().createSQLQuery(query)
				.addEntity(User.class).list().get(0);

	}
}
