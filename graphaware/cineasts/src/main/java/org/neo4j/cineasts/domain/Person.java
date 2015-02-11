package org.neo4j.cineasts.domain;


import org.springframework.data.neo4j.ogm.annotation.GraphId;
import org.springframework.data.neo4j.ogm.annotation.NodeEntity;

import java.util.Date;

/**
 * @author mh
 * @since 12.03.11
 */
@NodeEntity
public class Person {

    @GraphId
    Long nodeId;
    String id; //TODO validation on id
    String name;   //TODO name was indexed, fulltext, indexName = people
    private Date birthday;
    private String birthplace;
    private String biography;
    private Integer version;
    private Date lastModified;
    private String profileImageUrl;

    protected Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Person() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, id);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getBiography() {
        return biography;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Long getNodeId() {
        return nodeId;     //TODO drop this getter once we expose findBy* methods
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
