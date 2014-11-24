package cz.cvut.nss.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by jakubchalupa on 24.11.14.
 *
 * Provides utilities for security.
 */
public class AuthUtils {

    public static String getPasswordHash(String password) {
        return HashUtils.computeHash(password);
    }

    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
