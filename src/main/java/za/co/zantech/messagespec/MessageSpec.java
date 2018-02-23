package za.co.zantech.messagespec;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zandrewitte on 2017/05/12.
 * MessageSpec
 */
public class MessageSpec {

    public static class Actors {
        public static String userActorName = "userActor";
        public static String permissionActorName = "permissionActor";

        public static ActorRef getActorByName(ActorSystem actorSystem, String actorName) {
            return actorSystem.actorFor("user/" + actorName);
        }
    }

    public static class GetAll {
        private Map<String, Object> queryMap = new HashMap<>();

        public GetAll(){}

        public GetAll(Map<String, Object> queryMap){
            this.queryMap = queryMap;
        }

        public Map<String, Object> getQueryMap() {
            return queryMap;
        }
    }

    public static class GetById<U> {
        private U id;

        public GetById(U id){
            this.id = id;
        }

        public U getId() {
            return id;
        }
    }
    public static class Save <T> {
        private T entity;

        public Save(T entity){
            this.entity = entity;
        }

        public T getEntity() {
            return entity;
        }
    }
    public static class Update <T> {
        private T entity;

        public Update(T entity){
            this.entity = entity;
        }

        public T getEntity() {
            return entity;
        }
    }
    public static class Delete <T> {
        private T entity;

        public Delete(T entity){
            this.entity = entity;
        }

        public T getEntity() {
            return entity;
        }
    }

    @SuppressWarnings("unchecked")
    public static class SingleResponse<T> {
        private final T object;

        public SingleResponse(T object) {
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public static Object getResponse(Object o) {
            SingleResponse r = SingleResponse.class.cast(o);
            return r.getObject();
        }
    }

    @SuppressWarnings("unchecked")
    public static class ListResponse<T> {
        private final PVector<T> list;

        public ListResponse(Collection<T> list) {
            this.list = TreePVector.from(list);
        }

        public PVector<T> getList() {
            return list;
        }

        public static PVector getResponse(Object o) {
            ListResponse r = ListResponse.class.cast(o);
            return r.getList();
        }
    }
}
