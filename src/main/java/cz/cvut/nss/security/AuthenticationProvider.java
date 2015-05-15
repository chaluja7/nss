package cz.cvut.nss.security;

import cz.cvut.nss.entities.Person;
import cz.cvut.nss.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Authentication provider (using spring security)
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PersonService personService;

    public AuthenticationProvider() {
        this.setUserCache(new NullUserCache());
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
        return transactionTemplate.execute(new TransactionCallback<UserDetails>() {

            @Override
            public UserDetails doInTransaction(TransactionStatus status) {
                try {
                    UserDetails userDetails;
                    String password = (String) upat.getCredentials();
                    Person person = personService.getPersonByUsername(username);
                    if(person == null || person.getPassword() == null || !person.getPassword().equals(AuthUtils.getPasswordHash(password))) {
                        throw new BadCredentialsException("Bad user credentials");
                    }

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    userDetails = new User(username, password, authorities);
                    return userDetails;

                } catch(AuthenticationException e){
                    status.setRollbackOnly();
                    throw e;
                }
            }

        });
    }

}
