package com.softserve.auction.service;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.DeleteEventListener;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HibernateEventWiring {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private HibernateChangesListener listener;

	@PostConstruct
	public void registerListeners() {
	    EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
	            EventListenerRegistry.class);
	    
	    registry.getEventListenerGroup(EventType.UPDATE).appendListener((SaveOrUpdateEventListener) listener);
	    registry.getEventListenerGroup(EventType.DELETE).appendListener((DeleteEventListener) listener);
	    registry.getEventListenerGroup(EventType.SAVE).appendListener((SaveOrUpdateEventListener) listener);
	}
}
