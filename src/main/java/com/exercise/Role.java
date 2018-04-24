package com.exercise;

enum Role {
    PILOT() {
        @Override
        public boolean canDrive() {
            return true;
        }
    },
    CREW_MEMBER,
    FLIGHT_ATTENDANT,
    ONBOARD_CHIEF() {
        @Override
        public boolean canDrive() {
            return true;
        }
    },
    PRISONER,
    POLICEMAN() {
        @Override
        public boolean canDrive() {
            return true;
        }
    };

    public boolean canDrive() {
        return false;
    };
}
