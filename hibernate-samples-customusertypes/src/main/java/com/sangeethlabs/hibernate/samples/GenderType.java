package com.sangeethlabs.hibernate.samples;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class GenderType implements UserType {

	public int[] sqlTypes() {
		return new int[] { Types.INTEGER };
	}

	public Class returnedClass() {
		return Gender.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null || y == null) {
			return false;
		}

		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException {
		if (x != null) {
			return x.hashCode();
		}

		return 0;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		Gender gender = null;

		int ordinal = rs.getInt(names[0]);
		gender = Gender.values()[ordinal];

		return gender;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {

		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {

			doInstanceCheck(value);
			Gender gender = (Gender) value;
			st.setInt(index, gender.ordinal());
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		Gender gender = null;
		
		if (value!=null) {
			doInstanceCheck(value);
			gender = (Gender)value;
		}
		
		return gender;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	protected void doInstanceCheck(Object value) {

		if ((value != null) && !(value instanceof Gender)) {
			throw new UnsupportedOperationException(value.getClass()
					+ " not supported, expecting type "
					+ Gender.class.getName());
		}

	}
}
