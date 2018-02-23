package za.co.zantech.dao.common;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by zandrewitte on 2017/05/10.
 * BaseDAO
 */
@SuppressWarnings("unchecked")
public abstract class BaseDAO<T> {

    private String tableName;
    private Class<T> type;
    protected final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    public BaseDAO() {
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.type = (Class<T>) type.getActualTypeArguments()[0];
        this.tableName = this.getType().getSimpleName();
    }

    public PVector<T> executeListQuery(Function<Session, List<T>> f) {
        Session session = HibernateDAO.openSession();
        PVector<T> list = TreePVector.empty();

        try {
            session.beginTransaction();

            list = TreePVector.from((f.apply(session)));

            session.getTransaction().commit();

        }catch ( Exception e ) {
            logger.error(e.getMessage(), e);
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
                session.getTransaction().rollback();
            }
        }finally {
            session.close();
        }

        return list;
    }

    public Optional<T> executeQuery(Function<Session, T> f) {
        Session session = HibernateDAO.openSession();
        Optional<T> entity = Optional.empty();

        try {
            session.beginTransaction();

            entity = Optional.ofNullable(f.apply(session));

            session.getTransaction().commit();

        }catch ( Exception e ) {
            logger.error(e.getMessage(), e);
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
                session.getTransaction().rollback();
            }
        }finally {
            session.close();
        }

        return entity;
    }

    public Optional<?> execute(Function<Session, ?> f) {
        Session session = HibernateDAO.openSession();
        Optional<?> entity = Optional.empty();

        try {
            session.beginTransaction();

            entity = Optional.ofNullable(f.apply(session));

            session.getTransaction().commit();

        }catch ( Exception e ) {
            logger.error(e.getMessage(), e);
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
                session.getTransaction().rollback();
            }

            throw e;
        }finally {
            session.close();
        }

        return entity;
    }

    public void commit(Consumer<Session> f) {
        Session session = HibernateDAO.openSession();

        try {
            session.beginTransaction();

            f.accept(session);

            session.getTransaction().commit();

        }catch ( Exception e ) {
            logger.error(e.getMessage(), e);
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK ) {
                session.getTransaction().rollback();
            }

            throw e;
        }finally {
            session.close();
        }
    }

    protected Class<T> getType() {
        return this.type;
    }

    protected String getTableName(){
        return this.tableName;
    }

}
