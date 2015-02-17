package org.neo4j.cineasts.repository;

import org.neo4j.cineasts.domain.User;
import org.neo4j.cineasts.service.CineastsUserDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author mh
 * @since 08.11.11
 */
public interface CineastsUserDetailsService extends UserDetailsService {
    @Override
    CineastsUserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException;

    User getUserFromSession();

    User register(String login, String name, String password);

    void addFriend(String login, final User userFromSession);
}
