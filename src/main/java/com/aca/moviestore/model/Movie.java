package com.aca.moviestore.model;

import java.time.LocalDateTime;

public class Movie {

    private String title;
    private Integer releaseYear;
    private Genre genre;
    private Integer movieId;
    private LocalDateTime updateDateTime;
    private LocalDateTime createDateTime;

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getId() {
        return movieId;
    }

    public void setId(Integer id) {
        this.movieId = id;
    }

    @Override
    public String toString() {
        return "movie [ " + movieId + ", " + title + ", " + releaseYear + ", " + genre + " ]";
    }
}
