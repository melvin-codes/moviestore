package com.aca.moviestore.model;

public enum Genre {
    Action, Comedy, Horror, SciFi, Family;

    public static Genre convertStringToGenre(String value) {
        Genre myGenre = null;
        for (Genre genre : Genre.values()) {
            if (genre.toString().equalsIgnoreCase(value)) {
                myGenre = genre;
            }
        }
        return myGenre;
    }
}
