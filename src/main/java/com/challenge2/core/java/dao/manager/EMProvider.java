package com.challenge2.core.java.dao.manager;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EMProvider {

    private static EMProvider emProvider;
    private EntityManagerFactory entityManagerFactory;

    public EMProvider() {
        initialize();
    }

    public static EMProvider getInstance() {
        if (emProvider == null)
            emProvider = new EMProvider();
        return emProvider;
    }

    private void initialize() {
        entityManagerFactory = Persistence.createEntityManagerFactory("challenge2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
