package jay.hibernate.usertype;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.persistence.EnumType;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

abstract public class AbstractEnumType<T extends Enum<?>> implements UserType {
    
    private EnumType enumType;
    
    private Class<T> enumClass;
    
    public AbstractEnumType(EnumType enumType, Class<T> enumClass) {
        super();
        this.enumType = enumType;
        this.enumClass = enumClass;
    }

    public int[] sqlTypes() {
        return new int[] { (enumType==EnumType.ORDINAL)?Types.INTEGER:Types.CHAR};
    }

    public Class<? extends Enum<?>> returnedClass() {
        return enumClass;
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
        T value = null;

        switch(this.enumType) {
        case ORDINAL:
            int ordinal = rs.getInt(names[0]);
            
            try {
                Method method = this.enumClass.getMethod("values");
                Object values = method.invoke(null);
                int length = Array.getLength(values);
                if (ordinal>=0 && ordinal < length) {
                    value = (T)Array.get(values, ordinal);
                }
            } catch(Exception e) {
                // It is very unlikely this block will be reached.
                e.printStackTrace();
            }
            break;
        case STRING:
            String name = rs.getString(names[0]);
            
            try {
                Method method = this.enumClass.getMethod("valueOf", String.class);
                value = (T)method.invoke(null, name);
            } catch (Exception e) {
                // TODO Handle this exception appropriately
                e.printStackTrace();
            }
            
            break;
        }
        
        return value;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, (this.enumType==EnumType.STRING)?Types.VARCHAR:Types.INTEGER);
        } else {

            doInstanceCheck(value);
            T object = (T) value;
            
            switch(this.enumType) {
            case ORDINAL:
                st.setInt(index, object.ordinal());
                break;
            case STRING:
                st.setString(index, object.name());
                break;
            }
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        T object = null;
        
        if (value!=null) {
            doInstanceCheck(value);
            object = (T)value;
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

        if ((value != null) && !(value.getClass().equals(enumClass))) {
            throw new UnsupportedOperationException(value.getClass()
                    + " not supported, expecting type "
                    + enumClass.getName());
        }

    }

}
