package integration;

import context.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cineasts.domain.Actor;
import org.neo4j.cineasts.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.test.WrappingServerIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DomainTests extends WrappingServerIntegrationTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void actorCanPlayARoleInAMovie() {
        Actor tomHanks = new Actor("1", "Tom Hanks");
        actorRepository.save(tomHanks);
    }

}
