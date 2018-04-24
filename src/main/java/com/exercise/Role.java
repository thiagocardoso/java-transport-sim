package com.exercise;

interface Role {
    default boolean canDrive() {
        return false;
    }

    boolean hasRestrictions();

    void setupRestrictions();

    boolean containsRestriction(Role role);
}
