package za.co.zantech.messagespec;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zandrewitte on 2017/05/31.
 * UserSpec
 */
public interface UserSpec {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class User {
        private Long id;
        private String userName;
        @JsonIgnore
        private String password;
        private Long role;


        @JsonCreator
        public User(@JsonProperty("id") Long id, @JsonProperty("userName") String userName, @JsonProperty("password") String password,
                    @JsonProperty("role") Long role) {
            this.id = id;
            this.userName = userName;
            this.password = password;
            this.role = role;
        }

        public User(Long id, String userName, Long role) {
            this.id = id;
            this.userName = userName;
            this.role = role;
        }

        public Long getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public Long getRole() {
            return role;
        }

    }

    public static class GetByUserName {
        private String userName;

        public GetByUserName(String userName){
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }
    }
}
