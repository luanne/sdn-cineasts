package integration;

import context.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cineasts.domain.*;
import org.neo4j.cineasts.repository.ActorRepository;
import org.neo4j.cineasts.repository.DirectorRepository;
import org.neo4j.cineasts.repository.MovieRepository;
import org.neo4j.cineasts.repository.UserRepository;
import org.neo4j.ogm.testutil.WrappingServerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DomainTests extends WrappingServerIntegrationTest {

    @Override
    protected int neoServerPort() {
        return PersistenceContext.NEO4J_PORT;
    }

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldAllowActorCreation() {
        Actor tomHanks = new Actor("1", "Tom Hanks");
        tomHanks = actorRepository.save(tomHanks);

        Actor foundTomHanks = actorRepository.findOne(tomHanks.getNodeId());
        assertEquals(tomHanks.getName(),foundTomHanks.getName());
        assertEquals(tomHanks.getId(),foundTomHanks.getId());

    }

    @Test
    public void shouldAllowDirectorCreation() {
        Director robert = new Director("1","Robert Zemeckis");
        robert = directorRepository.save(robert);

        Director foundRobert = directorRepository.findOne(robert.getNodeId());
        assertEquals(robert.getId(),foundRobert.getId());
        assertEquals(robert.getName(),foundRobert.getName());

    }

    @Test
    public void shouldAllowMovieCreation() {
        Movie forrest = new Movie("1","Forrest Gump");
        forrest =  movieRepository.save(forrest);

        Movie foundForrest = movieRepository.findOne(forrest.getNodeId());
        assertEquals(forrest.getId(),foundForrest.getId());
        assertEquals(forrest.getTitle(),foundForrest.getTitle());
    }

    @Test
    public void shouldAllowDirectorToDirectMovie() {
        Movie forrest = new Movie("1","Forrest Gump");
        forrest =  movieRepository.save(forrest);

        Director robert = new Director("1","Robert Zemeckis");
        robert.directed(forrest);
        robert = directorRepository.save(robert);

        Director foundRobert = directorRepository.findOne(robert.getNodeId());
        assertEquals(robert.getId(),foundRobert.getId());
        assertEquals(robert.getName(),foundRobert.getName());
        assertEquals(forrest,robert.getDirectedMovies().iterator().next());

        Movie foundForrest = movieRepository.findOne(forrest.getNodeId());
        assertEquals(1,foundForrest.getDirectors().size());
        assertEquals(foundRobert,foundForrest.getDirectors().iterator().next());

    }

    @Test
    public void shouldAllowActorToActInMovie() {
        Movie forrest = new Movie("1","Forrest Gump");
        forrest =  movieRepository.save(forrest);

        Actor tomHanks = new Actor("1", "Tom Hanks");
        tomHanks = actorRepository.save(tomHanks);

        tomHanks.playedIn(forrest, "Forrest Gump");
        tomHanks = actorRepository.save(tomHanks);

        Actor foundTomHanks = actorRepository.findOne(tomHanks.getNodeId());
        assertEquals(tomHanks.getName(),foundTomHanks.getName());
        assertEquals(tomHanks.getId(),foundTomHanks.getId());
        assertEquals("Forrest Gump",foundTomHanks.getRoles().iterator().next().getName());
    }

    @Test
    public void userCanRateMovie() {
        Movie forrest = new Movie("1","Forrest Gump");
        forrest =  movieRepository.save(forrest);

        User micha = new User("micha","Micha","password");
       // micha = userRepository.save(micha);

        Rating awesome = micha.rate(forrest, 5, "Awesome");
        micha = userRepository.save(micha);


        User foundMicha = userRepository.findOne(micha.getId());
        assertEquals(1,foundMicha.getRatings().size());  //Works
        Movie foundForrest = movieRepository.findOne(forrest.getNodeId());
        assertEquals(1,foundForrest.getRatings().size()); //No ratings loaded
       /* Rating rating = foundForrest.getRatings().get(0);
        assertEquals(awesome,rating);
        assertEquals("Awesome",rating.getComment());
        assertEquals(5,rating.getStars());
        assertEquals(5,foundForrest.getStars(),0);*/
    }
}
