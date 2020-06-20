package com.challenge2.core.java.service;

import com.challenge2.core.java.model.AppInput;
import com.challenge2.core.java.constant.Status;
import com.challenge2.core.java.entity.PurchasingSpecialist;

public interface AccountingService {
    Status addPurchasingSpecialist(AppInput appInput);

    Status addInvoice(AppInput appInput, PurchasingSpecialist purchasingSpecialist);

    String generateReport();

    PurchasingSpecialist getPurchasingSpecialist(AppInput appInput);
}
