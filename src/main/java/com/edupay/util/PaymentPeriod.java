package com.edupay.util;

public enum PaymentPeriod {
    EQUIPMENT("Équipement"),
    JANUARY("Janvier"),
    FEBRUARY("Février"),
    MARCH("Mars"),
    APRIL("Avril"),
    MAY("Mai"),
    JUNE("Juin"),
    JULY("Juillet"),
    AUGUST("Août"),
    SEPTEMBER("Septembre"),
    OCTOBER("Octobre"),
    NOVEMBER("Novembre"),
    DECEMBER("Décembre");

    private final String label;

    PaymentPeriod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
