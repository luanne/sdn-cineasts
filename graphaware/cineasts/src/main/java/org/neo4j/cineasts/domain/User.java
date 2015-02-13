package org.neo4j.cineasts.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NodeEntity
public class User {
    @GraphId
    Long nodeId;

    private static final String SALT = "cewuiqwzie";
    public static final String FRIEND = "FRIEND";
    public static final String RATED = "RATED";
    String login;  //TODO was indexed
    String name;
    String password;
    String info;
    //private Roles[] roles;

    @Relationship(type = RATED, direction = Relationship.OUTGOING)
    List<Rating> ratings; //Was @Fetch

    @Relationship(type = FRIEND, direction = Relationship.UNDIRECTED)
    Set<User> friends; //Was fetch

    public User() {
    }

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    /* public User(String login, String name, String password, Roles... roles) {
        this.login = login;
        this.name = name;
        this.password = encode(password);
        this.roles = roles;
    }*/
/*

    private String encode(String password) {
        return new Md5PasswordEncoder().encodePassword(password, SALT);
    }
*/


    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public Rating rate(Movie movie, int stars, String comment) {
        if(ratings==null) {
            ratings = new ArrayList<>();
        }

        Rating rating = new Rating(this,movie,stars,comment);
        ratings.add(rating);
        return rating;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, login);
    }

    public String getName() {
        return name;
    }

    public Set<User> getFriends() {
        return friends;
    }

   /* public Roles[] getRole() {
        return roles;
    }
*/

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

   /* public void updatePassword(String old, String newPass1, String newPass2) {
        if (!password.equals(encode(old))) throw new IllegalArgumentException("Existing Password invalid");
        if (!newPass1.equals(newPass2)) throw new IllegalArgumentException("New Passwords don't match");
        this.password = encode(newPass1);
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFriend(User other) {
        return other != null && getFriends().contains(other);
    }

   /* public enum Roles implements GrantedAuthority {
        ROLE_USER, ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }*/

    public Long getId() {
        return nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (nodeId != null ? !nodeId.equals(user.nodeId) : user.nodeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
