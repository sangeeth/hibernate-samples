package jay.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class BooleanType implements UserType {
    private static final String TRUE = "1";
    private static final String FALSE = "0";
    
    public BooleanType() {
        super();
    }

    public int[] sqlTypes() {
        return new int[] {Types.CHAR};
    }

    public Class returnedClass() {
        return Boolean.class;
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
        Boolean value = null;

        String name = rs.getString(names[0]);
        if (TRUE.equals(name)) {
            value = true;
        } else if (FALSE.equals(name)) {
            value = false;
        } 
        
        return value;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.CHAR);
        } else {
            doInstanceCheck(value);
            Boolean object = (Boolean) value;
            
            st.setString(index, Boolean.TRUE.equals(object)?TRUE:FALSE);
            
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        Boolean object = null;
        
        if (value!=null) {
            doInstanceCheck(value);
            object = (Boolean)value;
        }
        
        return object;
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

        if ((value != null) && !(value instanceof Boolean)) {
            throw new UnsupportedOperationException(value.getClass()
                    + " not supported, expecting type Boolean.");
        }

    }

}
