package com.edupay.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    public static String format(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        currencyFormatter.setCurrency(java.util.Currency.getInstance("MGA"));
        return currencyFormatter.format(amount);
    }

    public static String format(double amount, String currency) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        currencyFormatter.setCurrency(java.util.Currency.getInstance(currency));
        return currencyFormatter.format(amount);
    }
}
