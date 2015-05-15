package cz.cvut.nss.security;

/**
 * Provides utilities for security.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public class AuthUtils {

    public static String getPasswordHash(String password) {
        return HashUtils.computeHash(password);
    }

}
