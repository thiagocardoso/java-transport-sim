package com.exercise;

import com.google.common.collect.Lists;

import java.util.List;

enum BasicRole implements Role {
    PILOT() {
        @Override
        public boolean canDrive() {
            return true;
        }

        @Override
        public void setupRestrictions(){
            this.addRestriction(FLIGHT_ATTENDANT);
        }
    },
    ONBOARD_CHIEF() {
        @Override
        public boolean canDrive() {
            return true;
        }

        @Override
        public void setupRestrictions() {
            this.addRestriction(PRISONER);
        }
    },
    POLICEMAN() {
        @Override
        public boolean canDrive() {
            return true;
        }

        @Override
        public void setupRestrictions() { }
    },
    OFFICER {
        @Override
        public void setupRestrictions() {
            this.addRestriction(PRISONER);
            this.addRestriction(ONBOARD_CHIEF);
        }
    },
    FLIGHT_ATTENDANT {
        @Override
        public void setupRestrictions() {
            this.addRestriction(PRISONER);
            this.addRestriction(PILOT);
        }
    },
    PRISONER {
        @Override
        public void setupRestrictions() {
            this.addRestriction(PILOT);
            this.addRestriction(ONBOARD_CHIEF);
            this.addRestriction(FLIGHT_ATTENDANT);
        }
    };

    private final List<Role> restrictions = Lists.newLinkedList();

    protected void addRestriction(Role restricted) {
        this.restrictions.add(restricted);
    }

    BasicRole() {
        this.setupRestrictions();
    }

    @Override
    public boolean hasRestrictions() {
        return !restrictions.isEmpty();
    }
}
