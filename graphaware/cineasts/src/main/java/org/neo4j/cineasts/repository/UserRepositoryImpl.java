package org.neo4j.cineasts.repository;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.Rating;
import org.neo4j.cineasts.domain.User;
import org.neo4j.cineasts.service.CineastsUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * @author mh
 * @since 06.03.11
 */
public class UserRepositoryImpl implements CineastsUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CineastsUserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        final User user = findByLogin(login);
        if (user==null) throw new UsernameNotFoundException("Username not found: "+login);
        return new CineastsUserDetails(user);
    }

    private User findByLogin(String login) {
        Iterator<User> userIterator = userRepository.findByProperty("login",login).iterator();
        if(userIterator.hasNext()) {
            return userIterator.next();
        }
        return null;
    }

    @Override
    public User getUserFromSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CineastsUserDetails) {
            CineastsUserDetails userDetails = (CineastsUserDetails) principal;
            return userDetails.getUser();
        }
        return null;
    }

    @Override
    public User register(String login, String name, String password) {
        User found = findByLogin(login);
        if (found!=null) throw new RuntimeException("Login already taken: "+login);
        if (name==null || name.isEmpty()) throw new RuntimeException("No name provided.");
        if (password==null || password.isEmpty()) throw new RuntimeException("No password provided.");
        //User user=userRepository.save(new User(login,name,password,User.Roles.ROLE_USER)); //TODO
        User user=userRepository.save(new User(login,name,password));
        setUserInSession(user);
        return user;
    }

    void setUserInSession(User user) {
        SecurityContext context = SecurityContextHolder.getContext();
        CineastsUserDetails userDetails = new CineastsUserDetails(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),userDetails.getAuthorities());
        context.setAuthentication(authentication);

    }

    @Override
    public void addFriend(String friendLogin, final User user) {
        User friend = findByLogin(friendLogin);
        if (!user.equals(friend)) {
            user.addFriend(friend);
            userRepository.save(user);
        }
    }
}
