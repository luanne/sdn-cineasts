package org.neo4j.cineasts.domain;

import java.util.Collection;

/**
 * @author mh
 * @since 10.11.11
 */
public class Actor extends Person {
    public Actor(String id, String name) {
        super(id, name);
    }

    public Actor() {
    }


    public Actor(String id) {
        super(id,null);
    }


}
