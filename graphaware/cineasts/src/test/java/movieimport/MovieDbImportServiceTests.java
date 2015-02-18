package movieimport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cineasts.domain.Actor;
import org.neo4j.cineasts.domain.Director;
import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.Person;
import org.neo4j.cineasts.movieimport.MovieDbImportService;
import org.neo4j.cineasts.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author mh
 * @since 13.03.11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/movies-test-context.xml"})
@Transactional
public class MovieDbImportServiceTests {

    @Autowired
    MovieDbImportService importService;

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void testImportMovie() throws Exception {
        Movie movie = importService.importMovie("2");
        assertEquals("movie-id","2", movie.getId());
        assertEquals("movie-title","Ariel", movie.getTitle());
    }

    @Test
    public void testImportMovieTwice() throws Exception {
        Movie movie = importService.importMovie("2");
        Movie movie2 = importService.importMovie("2");
        final Movie foundMovie = movieRepository.findById("2");
        assertEquals("movie-id", movie, foundMovie);
    }

    @Test
    public void testImportPerson() throws Exception {
        Person actor = importService.importPerson("105955", new Actor("105955",null));
        assertEquals("movie-id","105955", actor.getId());
        assertEquals("movie-title","George M. Williamson", actor.getName());
    }

    @Test
    public void shouldImportMovieWithTwoDirectors() throws Exception {
        Movie movie = importService.importMovie("603");

        movie = movieRepository.findById(movie.getId());

        assertThat(movie.getDirectors().size(), is(2));
    }
}
