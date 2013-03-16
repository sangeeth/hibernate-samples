package com.sangeethlabs.hibernate.samples;

import javax.persistence.EnumType;

import jay.hibernate.usertype.AbstractEnumType;

public class OccupationType extends AbstractEnumType<Occupation> {

    public OccupationType() {
        super(EnumType.STRING, Occupation.class);
    }

}
