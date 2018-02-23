package za.co.zantech.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.Optional;

/**
 * Created by zandrewitte on 2017/05/31.
 * PasswordEncrypter
 */
public class PasswordEncrypter {

    private static Argon2 _factory = Argon2Factory.create();

    private static Argon2 getFactory() {
        return _factory;
    }

    public static Optional<String> encrypt(String password) {
        Optional<String> encrypted = Optional.empty();
        try {
            // Hash password
            String hash = getFactory().hash(2, 65536, 1, password);
            // Verify password
            if (getFactory().verify(hash, password)) {
                // Hash matches password
                encrypted = Optional.of(hash);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            // Wipe confidential data
            getFactory().wipeArray(password.toCharArray());
        }

        return encrypted;
    }

    public static boolean matches(String hash, String password) {
        try {
            // Verify password
            return getFactory().verify(hash, password);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            // Wipe confidential data
            getFactory().wipeArray(password.toCharArray());
        }
    }



}
