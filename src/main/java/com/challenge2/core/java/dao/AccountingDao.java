package com.challenge2.core.java.dao;

import com.challenge2.core.java.model.AppInput;
import com.challenge2.core.java.entity.Invoice;
import com.challenge2.core.java.constant.Status;
import com.challenge2.core.java.dao.manager.EMProvider;
import com.challenge2.core.java.entity.PurchasingSpecialist;

import java.util.List;
import javax.persistence.EntityManager;

public class AccountingDao {

    private static AccountingDao accountingDao;

    public static AccountingDao getInstance() {
        if (null == accountingDao) {
            accountingDao = new AccountingDao();
        }
        return accountingDao;
    }

    private EntityManager getEntityManager() {
        return EMProvider.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void addPurchasingSpecialist(PurchasingSpecialist purchasingSpecialist, Invoice invoice) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(purchasingSpecialist);
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void addInvoice(Invoice invoice) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public PurchasingSpecialist getPurchasingSpecialist(AppInput appInput) {
        EntityManager entityManager = getEntityManager();

        List<PurchasingSpecialist> purchasingSpecialistList = entityManager.createQuery(
                "SELECT p FROM PurchasingSpecialist p where p.firstName = :firstNameValue and p.lastName = :lastNameValue and p.email = :emailValue")
                .setParameter("firstNameValue", appInput.getFirstName())
                .setParameter("lastNameValue", appInput.getLastName())
                .setParameter("emailValue", appInput.getEmail())
                .getResultList();

        if (purchasingSpecialistList.isEmpty())
            return null;
        return purchasingSpecialistList.get(0);
    }

    public String generateReport() {
        EntityManager entityManager = getEntityManager();

        Long total = (Long) entityManager.createQuery(
                "SELECT COUNT(i) FROM Invoice i")
                .getSingleResult();

        Long red_edildi = (Long) entityManager.createQuery(
                "SELECT COUNT(i) FROM Invoice i where i.status = :value1")
                .setParameter("value1", Status.REJECT)
                .getSingleResult();
        entityManager.close();

        return (total + " fatura girişi yapıldı. " + red_edildi + " fatura rededildi");
    }
}


