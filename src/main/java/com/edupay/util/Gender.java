package com.edupay.util;

public enum Gender {
    MALE("Homme"),
    FEMALE("Femme");

    private final String label;

    Gender(String label ) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}

