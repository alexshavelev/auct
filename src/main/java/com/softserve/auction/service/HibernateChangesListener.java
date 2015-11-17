package com.softserve.auction.service;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.event.spi.DeleteEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
public class HibernateChangesListener implements SaveOrUpdateEventListener, DeleteEventListener{
	private static final long serialVersionUID = 1L;
	private long version=0;

	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		System.out.println("version: " + version++);
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		System.out.println("delete :: version: " + version++);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onDelete(DeleteEvent event, Set transientEntities)
			throws HibernateException {
		// TODO Auto-generated method stub
		
	}
	
	
}
