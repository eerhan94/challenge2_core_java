package com.challenge2.core.java.service.impl;

import com.challenge2.core.java.model.AppInput;
import com.challenge2.core.java.entity.Invoice;
import com.challenge2.core.java.constant.Status;
import com.challenge2.core.java.dao.AccountingDao;
import com.challenge2.core.java.config.AppProperties;
import com.challenge2.core.java.service.AccountingService;
import com.challenge2.core.java.entity.PurchasingSpecialist;

import java.util.Arrays;

public class AccountingServiceImpl implements AccountingService {

    private static AccountingServiceImpl emServiceImpl;

    public static AccountingServiceImpl getInstance() {
        if (emServiceImpl == null)
            emServiceImpl = new AccountingServiceImpl();
        return emServiceImpl;
    }

    @Override
    public Status addPurchasingSpecialist(AppInput appInput) {
        PurchasingSpecialist purchasingSpecialist = new PurchasingSpecialist();
        purchasingSpecialist.setFirstName(appInput.getFirstName());
        purchasingSpecialist.setLastName(appInput.getLastName());
        purchasingSpecialist.setEmail(appInput.getEmail());

        Invoice invoice = new Invoice();
        int amount = appInput.getAmount();
        invoice.setAmount(amount);
        invoice.setProductName(appInput.getProductName());
        invoice.setBillNo(appInput.getBillNo());
        invoice.setPurchasingSpecialist(purchasingSpecialist);

        if (AppProperties.getInstance().getInvoiceLimit() > amount)
            invoice.setStatus(Status.APPROVED);
        else
            invoice.setStatus(Status.REJECT);

        purchasingSpecialist.setInvoices(Arrays.asList(invoice));
        AccountingDao.getInstance().addPurchasingSpecialist(purchasingSpecialist, invoice);
        return invoice.getStatus();
    }

    @Override
    public Status addInvoice(AppInput appInput, PurchasingSpecialist purchasingSpecialist) {

        Invoice invoice = new Invoice();
        int amount = appInput.getAmount();
        invoice.setAmount(amount);
        invoice.setProductName(appInput.getProductName());
        invoice.setBillNo(appInput.getBillNo());
        invoice.setPurchasingSpecialist(purchasingSpecialist);
        int totalAmount = purchasingSpecialist.getInvoices().stream().mapToInt(Invoice::getAmount).sum() + amount;
        if (AppProperties.getInstance().getInvoiceLimit() >= totalAmount)
            invoice.setStatus(Status.APPROVED);
        else
            invoice.setStatus(Status.REJECT);
        AccountingDao.getInstance().addInvoice(invoice);
        return invoice.getStatus();
    }

    @Override
    public String generateReport() {
        return AccountingDao.getInstance().generateReport();
    }

    @Override
    public PurchasingSpecialist getPurchasingSpecialist(AppInput appInput) {
        return AccountingDao.getInstance().getPurchasingSpecialist(appInput);

    }

}
