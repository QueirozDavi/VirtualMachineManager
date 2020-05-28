package com.ustore.virtualmachinemanager.domain;

public enum StatusEnum {

    INACTIVE("Inactive"),
    ACTIVE("Active");

    private String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
