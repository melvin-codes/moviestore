package com.aca.moviestore.dao;

import java.util.List;

import com.aca.moviestore.model.*;

public interface MovieDao {

    public List<Movie> getMovies();
    public List<Movie> getMoviesByGenre(Genre genre);
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear);
    public List<Movie> getMoviesById(Integer movieId);
    public Movie createMovie(Movie movie);
    public Movie updateMovie(Movie updateMovie);
    public Movie deleteMovie(Integer movieId);
    public List<Movie> getReport(Integer startReleaseYear, Integer endReleaseYear);
    public List<Movie> getMoviesByTitle(String title);
}