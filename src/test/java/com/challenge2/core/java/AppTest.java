package com.challenge2.core.java;

import com.challenge2.core.java.util.Printer;
import org.junit.Test;
import com.challenge2.core.java.model.AppInput;
import com.challenge2.core.java.constant.Status;
import com.challenge2.core.java.entity.PurchasingSpecialist;
import com.challenge2.core.java.service.impl.AccountingServiceImpl;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void approvedSingleInvoice() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane1");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(199);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        Status status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
        assertEquals(Status.APPROVED, status);
    }

    @Test
    public void rejectedSingleInvoice() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane2");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(201);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        Status status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
        assertEquals(Status.REJECT, status);
    }

    @Test
    public void approvedMultiInvoice() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane3");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(100);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        Status status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
        assertEquals(Status.APPROVED, status);

        appInput = new AppInput();
        appInput.setFirstName("Jane3");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(2);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        PurchasingSpecialist purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);
        status = AccountingServiceImpl.getInstance().addInvoice(appInput, purchasingSpecialist);
        assertEquals(Status.APPROVED, status);
    }

    @Test
    public void rejectedMultiInvoice() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane4");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(202);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        Status status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
        assertEquals(Status.REJECT, status);

        appInput = new AppInput();
        appInput.setFirstName("Jane4");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(203);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        PurchasingSpecialist purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);
        status = AccountingServiceImpl.getInstance().addInvoice(appInput, purchasingSpecialist);
        assertEquals(Status.REJECT, status);
    }

    @Test
    public void approvedAndRejectedInvoice() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane5");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(100);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        Status status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
        assertEquals(Status.APPROVED, status);

        appInput = new AppInput();
        appInput.setFirstName("Jane5");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(101);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        PurchasingSpecialist purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);
        status = AccountingServiceImpl.getInstance().addInvoice(appInput, purchasingSpecialist);
        assertEquals(Status.REJECT, status);
    }

    @Test
    public void uniqueConstraintSameSpecialist() {
        AppInput appInput = new AppInput();
        appInput.setFirstName("Jane6");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(10);
        appInput.setProductName("IPhone 8");
        appInput.setBillNo("TR0001");
        AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);

        appInput = new AppInput();
        appInput.setFirstName("Jane6");
        appInput.setLastName("Doe");
        appInput.setEmail("jane@doe.com");
        appInput.setAmount(11);
        appInput.setProductName("IPhone 9");
        appInput.setBillNo("TR0002");
        PurchasingSpecialist purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);
        AccountingServiceImpl.getInstance().addInvoice(appInput, purchasingSpecialist);

        purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);

        purchasingSpecialist.getInvoices().stream().forEach(item -> {
            assertEquals(Status.APPROVED, item.getStatus());
            if (item.getProductName().equalsIgnoreCase("IPhone 9"))
                assertEquals("TR0002", item.getBillNo());
            else
                assertEquals("TR0001", item.getBillNo());
        });

        assertEquals(2, purchasingSpecialist.getInvoices().size());
    }

    @Test
    public void uniqueConstraintDifferentSpecialist() {
        AppInput appInput1 = new AppInput();
        appInput1.setFirstName("Jane7");
        appInput1.setLastName("Doe");
        appInput1.setEmail("jane@doe.com");
        appInput1.setAmount(10);
        appInput1.setProductName("IPhone 8");
        appInput1.setBillNo("TR0001");
        AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput1);

        AppInput appInput2 = new AppInput();
        appInput2.setFirstName("Jane7");
        appInput2.setLastName("Doe");
        appInput2.setEmail("jane@doe.coma");
        appInput2.setAmount(201);
        appInput2.setProductName("IPhone 9");
        appInput2.setBillNo("TR0002");
        AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput2);

        PurchasingSpecialist purchasingSpecialist1 = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput1);
        PurchasingSpecialist purchasingSpecialist2 = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput2);

        purchasingSpecialist1.getInvoices().stream().forEach(item -> {
            assertEquals(Status.APPROVED, item.getStatus());
            assertEquals("TR0001", item.getBillNo());
        });
        assertEquals(1, purchasingSpecialist1.getInvoices().size());

        purchasingSpecialist2.getInvoices().stream().forEach(item -> {
            assertEquals(Status.REJECT, item.getStatus());
            assertEquals("TR0002", item.getBillNo());
        });
        assertEquals(1, purchasingSpecialist1.getInvoices().size());
    }

    @Test(expected = NullPointerException.class)
    public void appInputNullCheck() {
        AppInput appInput = null;
        AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
    }

    @Test
    public void printerTest() {
        Printer.print("test");
    }

}
