package com.neueda.services.atm.dao;


public enum UserRole {

    ROLE_ADMIN("System Admin"),
    ROLE_USER("User");

    private final String displayName;

     UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
