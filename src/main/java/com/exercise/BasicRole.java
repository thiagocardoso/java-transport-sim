package com.exercise;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

enum BasicRole implements Role {
    PILOT {
        @Override
        public boolean canDrive() {
            return true;
        }

        @Override
        public void setupRestrictions(){
            addRestriction(this, FLIGHT_ATTENDANT);
        }
    },
    ONBOARD_CHIEF {
        @Override
        public boolean canDrive() {
            return true;
        }

        @Override
        public void setupRestrictions() {
            addRestriction(this, PRISONER);
        }
    },
    POLICEMAN {
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
            addRestriction(this,PRISONER);
            addRestriction(this, ONBOARD_CHIEF);
        }
    },
    FLIGHT_ATTENDANT {
        @Override
        public void setupRestrictions() {
            addRestriction(this, PRISONER);
            addRestriction(this, PILOT);
        }
    },
    PRISONER {
        @Override
        public void setupRestrictions() {
            addRestriction(this, PILOT);
            addRestriction(this, ONBOARD_CHIEF);
            addRestriction(this, FLIGHT_ATTENDANT);
        }
    };

    private static final Map<Role, List<Role>> restrictions = Maps.newHashMap();

    static {
        for (Role role: BasicRole.values())
            role.setupRestrictions();
    }

    protected static void addRestriction(Role first, Role restricted) {
        checkNotNull(first);
        checkNotNull(restricted);

        if(!restrictions.containsKey(first))
            restrictions.put(first, Lists.newLinkedList());

        restrictions.get(first).add(restricted);
    }

    @Override
    public boolean hasRestrictions() {
        return restrictions.containsKey(this);
    }

    @Override
    public boolean containsRestriction(Role role) {
        return restrictions.get(this).contains(role);
    }
}
