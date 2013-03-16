package com.sangeethlabs.hibernate.samples;

import javax.persistence.EnumType;

import jay.hibernate.usertype.AbstractEnumType;

public class GenderType extends AbstractEnumType<Gender> {

    public GenderType() {
        super(EnumType.ORDINAL, Gender.class);
    }

}
