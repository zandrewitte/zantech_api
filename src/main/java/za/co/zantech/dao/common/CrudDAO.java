package za.co.zantech.dao.common;

import org.pcollections.PVector;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by zandrewitte on 2017/05/10.
 * CrudDAO
 */
@SuppressWarnings("unchecked")
public abstract class CrudDAO<T, U> extends BaseDAO<T>{

    public PVector<T> list() {
        return executeListQuery(session -> session.createQuery( "from " + getTableName() ).list());
    }

    public Optional<T> getById(U id) {
        return executeQuery(session -> session.get(this.getType(), (Serializable) id));
    }

    public Optional<Long> save(T t) {
        return (Optional<Long>) execute(session -> session.save(t));
    }

    public Optional<T> update(T t) {
        try {
            commit(session -> session.update(t));
            return Optional.of(t);
        } catch (Exception sqlException) {
            return Optional.empty();
        }
    }

    public boolean delete(T t) {
        try {
            commit(session -> session.delete(t));
            return true;
        } catch (Exception sqlException) {
            return false;
        }
    }

}
