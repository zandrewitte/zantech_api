package za.co.zantech.messagespec;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by zandrewitte on 2017/05/10.
 * LoginSpec
 */
public interface LoginSpec {

    static class Login {
        private String username;
        private String password;

        @JsonCreator
        public Login(@JsonProperty("username") String username, @JsonProperty("password") String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class LoginAuthentication {
        private String login;
        private Function<String, Optional<String>> passwordMatcher;

        public LoginAuthentication(String login, Function<String, Optional<String>> passwordMatcher) {
            this.login = login;
            this.passwordMatcher = passwordMatcher;
        }

        public String getLogin() {
            return login;
        }

        public Function<String, Optional<String>> getPasswordMatcher() {
            return passwordMatcher;
        }
    }

}
