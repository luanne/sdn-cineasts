package org.neo4j.cineasts.domain;



import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


/**
 * @author mh
 * @since 04.03.11
 */
@NodeEntity
public class Movie {
    @GraphId
    Long nodeId;

    String id; //TODO id is indexed, unique, add validation
    String title;  //TODO title is indexed, fulltext, indexName=search
    String description;

    @Relationship(type="DIRECTED", direction = Relationship.INCOMING)
    Set<Director> directors;


    Set<Actor> actors;

    @Relationship(type = "ACTS_IN", direction = Relationship.INCOMING)
    Set<Role> roles;

    @Relationship(type = "RATED", direction = Relationship.INCOMING)
    private Set<Rating> ratings = new HashSet<>();

    private String language;
    private String imdbId;
    private String tagline;
    private Date releaseDate;
    private Integer runtime;
    private String homepage;
    private String trailer;
    private String genre;
    private String studio;
    private Integer version;
    private Date lastModified;
    private String imageUrl;

    public Movie() {
    }

    public Movie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public int getYear() {
        if (releaseDate==null) return 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        return cal.get(Calendar.YEAR);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }

    public int getStars() {
        Iterable<Rating> allRatings = ratings;

        if (allRatings == null) return 0;
        int stars=0, count=0;
        for (Rating rating : allRatings) {
            stars += rating.getStars();
            count++;
        }
        return count==0 ? 0 : stars / count;
    }

    public Set<Rating> getRatings() {
        //Iterable<Rating> allRatings = ratings;
       // return allRatings == null ? Collections.<Rating>emptyList() : ratings;
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTagline() {
        return tagline;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getGenre() {
        return genre;
    }

    public String getStudio() {
        return studio;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYoutubeId() {
        String trailerUrl = trailer;
        if (trailerUrl==null || !trailerUrl.contains("youtu")) return null;
        String[] parts = trailerUrl.split("[=/]");
        int numberOfParts = parts.length;
        return numberOfParts > 0 ? parts[numberOfParts-1] : null;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public Long getNodeId() {
        return nodeId; //TODO remove this once we add the findBy* methods
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", title, releaseDate, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (!id.equals(movie.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

