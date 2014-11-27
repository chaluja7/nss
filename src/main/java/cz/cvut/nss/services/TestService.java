package cz.cvut.nss.services;

import org.springframework.stereotype.Service;

/**
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class TestService {

    public int doSomething() {
        System.out.println("ahojda");

        return 1;
    }

}
