package com.aca.moviestore.service;

import com.aca.moviestore.dao.MovieDao;
import com.aca.moviestore.dao.MovieDaoImpl;
import com.aca.moviestore.dao.MovieDaoMock;
import com.aca.moviestore.model.Genre;
import com.aca.moviestore.model.Movie;
import com.aca.moviestore.model.RequestError;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class MovieService {



    private MovieDao movieDao = new MovieDaoImpl();

    public List<Movie> getMovies() {
        return movieDao.getMovies();
    }
    public List<Movie> getMoviesByGenre(Genre genre) {
        return movieDao.getMoviesByGenre(genre);
    }
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        validateReleaseYear(releaseYear);
        return movieDao.getMoviesByReleaseYear(releaseYear);
    }

    public List<Movie> getMoviesById(Integer movieId) {
        validateMovieId(movieId);
        return movieDao.getMoviesById(movieId);
    }

    public Movie updateMovie(Movie updateMovie) {
        validateMovieId(updateMovie.getId());
        List<Movie> movies = movieDao.getMoviesById(updateMovie.getId());
        if(movies.size() == 1) {
            return movieDao.updateMovie(updateMovie);
        }
        else {
            RequestError error = new RequestError(4, "Invalid movie id: " + updateMovie.getId() + "' - Value must exist");
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }

    }

    private void validateMovieId(Integer movieId) {
        if(movieId < 0 || movieId == null) {
            RequestError error = new RequestError(2, "Invalid value for movie id: " + movieId + "' - Value must be > 0");
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }
    }
    private void validateReleaseYear(Integer releaseYear) {
        if(null == releaseYear || releaseYear < 1935 || releaseYear > 2025) {
            RequestError error = new RequestError(1, "Invalid value for release year: '" + releaseYear + "' - Value must be > 1935 and < 2025");
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }
    }

    private void validateTitle(Movie movie) {
        if(movie.getTitle().length() < 2 || movie.getTitle().length() > 50) {
            RequestError error = new RequestError(3, "Invalid title length: " + movie.getTitle() + " - length must be > 2 and < 50");
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }
    }

    private void validateTitle(String title) {
        List<Movie> movies = movieDao.getMoviesByTitle(title);
        if(movies.isEmpty()) {
            RequestError error = new RequestError(6, "Invalid title search: '" + title + "'");
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }
    }

    public Movie createMovie(Movie movie) {
        validateReleaseYear(movie.getReleaseYear());
        validateTitle(movie);
        return movieDao.createMovie(movie);
    }

    public Movie deleteMovie(Integer movieId) {
        validateMovieId(movieId);
        List<Movie> movies = movieDao.getMoviesById(movieId);
        if(movies.size() == 1) {
            return movieDao.deleteMovie(movieId);
        }
        else {
            RequestError error = new RequestError(5, "Movie id does not exist: " + movieId);
            Response response = Response.status(400)
                    .entity(error)
                    .build();
            throw new WebApplicationException(response);
        }
    }

    public List<Movie> getReport(Integer startReleaseYear, Integer endReleaseYear) {
        validateReleaseYear(startReleaseYear);
        validateReleaseYear(endReleaseYear);
        return movieDao.getReport(startReleaseYear, endReleaseYear);
    }

    public List<Movie> getMoviesByTitle(String title) {
        validateTitle(title);
        return movieDao.getMoviesByTitle(title);
    }
}
