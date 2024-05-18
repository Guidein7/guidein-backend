package com.GuideIn.jobs;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class CustomJobIdGenerator implements IdentifierGenerator{

    @PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		try {
            Query query = entityManager.createNativeQuery("SELECT nextval('job_sequence')");
            Long id = ((Number) query.getSingleResult()).longValue();
            return String.format("%08d", id); // Ensures the ID is 8 digits long, padded with leading zeros if necessary
        } catch (Exception e) {
            throw new HibernateException("Unable to generate sequence value.", e);
        }
	}

}
