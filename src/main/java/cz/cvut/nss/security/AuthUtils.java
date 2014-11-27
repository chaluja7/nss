package cz.cvut.nss.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
