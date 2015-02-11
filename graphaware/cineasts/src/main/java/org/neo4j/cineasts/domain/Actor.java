package org.neo4j.cineasts.domain;

import org.springframework.data.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author mh
 * @since 10.11.11
 */
public class Actor extends Person {

    //@Relationship(type = "ACTS_IN", direction = Relationship.OUTGOING)
   // Collection<Role> roles=new HashSet<Role>();

    Role role;

    public Actor() {
    }

    public Actor(String id, String name) {
        super(id, name);
    }

    public Actor(String id) {
        super(id,null);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /* public Iterable<Role> getRoles() {
        return roles;
    }

    public Role playedIn(Movie movie, String roleName) {
        final Role role = new Role(this, movie, roleName);
        roles.add(role);
        return role;
    }*/

}
