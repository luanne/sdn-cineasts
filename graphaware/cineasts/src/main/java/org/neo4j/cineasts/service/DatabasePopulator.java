package org.neo4j.cineasts.service;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.User;
import org.neo4j.cineasts.movieimport.MovieDbImportService;
import org.neo4j.cineasts.repository.MovieRepository;
import org.neo4j.cineasts.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author mh
 * @since 04.03.11
 */
@Service
public class DatabasePopulator {

    private final static Logger log = LoggerFactory.getLogger(DatabasePopulator.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieDbImportService importService;

    // @Transactional
    public List<Movie> populateDatabase() {
        importService.importImageConfig();
        User me = userRepository.save(new User("micha", "Micha", "password", User.SecurityRole.ROLE_ADMIN, User.SecurityRole.ROLE_USER));
        User ollie = new User("ollie", "Olliver", "password", User.SecurityRole.ROLE_USER);
        me.addFriend(ollie);
        userRepository.save(me);
        List<Integer> ids = asList(600, 601, 602, 603, 2);
        List<Movie> result = new ArrayList<Movie>(ids.size());
        for (Integer id : ids) {
            result.add(importService.importMovie(String.valueOf(id)));
        }

        me.rate(movieRepository.findById("13"), 5, "Inspiring");
        final Movie movie = movieRepository.findById("603");
        me.rate(movie, 5, "Best of the series");
        return result;
    }

    //@Transactional
    public void cleanDb() {
        new Neo4jDatabaseCleaner().cleanDb();
    }
}
