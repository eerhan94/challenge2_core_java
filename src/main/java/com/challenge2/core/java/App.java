package com.challenge2.core.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.challenge2.core.java.util.Printer;
import com.challenge2.core.java.model.AppInput;
import com.challenge2.core.java.constant.Status;
import com.challenge2.core.java.mapper.AppMapper;
import com.challenge2.core.java.entity.PurchasingSpecialist;
import com.challenge2.core.java.service.impl.AccountingServiceImpl;

public class App {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        while (true) {
            try {
                Printer.print("Secim Yapiniz:\n1-Fatura Girisi\n2-Rapor\n->");
                String input = reader.readLine();
                if (input.equals("1")) {
                    Printer.print("Fatura Girisi Yapiniz -> ");
                    String[] fields = inputParser(reader.readLine());
                    if (fields.length == 6) {
                        AppInput appInput = AppMapper.getInput(fields);
                        PurchasingSpecialist purchasingSpecialist = AccountingServiceImpl.getInstance().getPurchasingSpecialist(appInput);
                        Status status;
                        if (purchasingSpecialist == null) {
                            status = AccountingServiceImpl.getInstance().addPurchasingSpecialist(appInput);
                        } else {
                            status = AccountingServiceImpl.getInstance().addInvoice(appInput, purchasingSpecialist);
                        }
                        Printer.print(" -> " + status);
                    }
                } else if (input.equals("2")) {
                    String report = AccountingServiceImpl.getInstance().generateReport();
                    Printer.print(report);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] inputParser(String input) {
        return input.split(",");
    }

}
