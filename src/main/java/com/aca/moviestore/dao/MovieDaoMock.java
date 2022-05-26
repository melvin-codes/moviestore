package com.aca.moviestore.dao;

import com.aca.moviestore.model.Genre;
import com.aca.moviestore.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDaoMock implements MovieDao{

    private static List<Movie> movies = new ArrayList<Movie>();

    private static Integer lastMovieId = 5;

    private static Integer getNextMovieId() {
        return ++lastMovieId;
    }

    static {
        Movie bond = new Movie();
        bond.setTitle("The World Is Not Enough");
        bond.setReleaseYear(1999);
        bond.setGenre(Genre.Action);
        bond.setId(1);

        Movie jerk = new Movie();
        jerk.setTitle("The Jerk");
        jerk.setReleaseYear(1979);
        jerk.setGenre(Genre.Action);
        jerk.setId(2);

        Movie encanto = new Movie();
        encanto.setTitle("Encanto");
        encanto.setReleaseYear(2021);
        encanto.setGenre(Genre.Family);
        encanto.setId(3);

        Movie blackPanther = new Movie();
        blackPanther.setTitle("Black Panther");
        blackPanther.setReleaseYear(2018);
        blackPanther.setGenre(Genre.Action);
        blackPanther.setId(4);

        Movie matrix = new Movie();
        matrix.setTitle("The Matrix");
        matrix.setReleaseYear(1999);
        matrix.setGenre(Genre.SciFi);
        matrix.setId(5);

        movies.add(bond);
        movies.add(jerk);
        movies.add(encanto);
        movies.add(blackPanther);
        movies.add(matrix);
    }

    @Override
    public List<Movie> getMovies() {
        return new ArrayList<Movie>(movies);
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        List<Movie> myMovies = new ArrayList<Movie>();

        for(Movie i: movies) {
            if(i.getGenre().equals(genre)) {
                myMovies.add(i);
            }
        }

        return myMovies;
    }

    @Override
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        List<Movie> myMovies = new ArrayList<>();

        for(Movie i: movies) {
            if(i.getReleaseYear().equals(releaseYear)) {
                myMovies.add(i);
            }
        }
        return myMovies;
    }

    @Override
    public List<Movie> getMoviesById(Integer movieId) {
        List<Movie> myMovies = new ArrayList<>();

        for(Movie i: movies) {
            if(i.getId().intValue() == movieId.intValue() ) {
                myMovies.add(i);
            }
        }
        return myMovies;
    }

    //Do a getMoviesByTitle here to check if there already exists a movie.

    @Override
    public Movie createMovie(Movie newMovie) {
        newMovie.setId(getNextMovieId());
        movies.add(newMovie);
        return newMovie;

    }

    @Override
    public Movie updateMovie(Movie updateMovie) {
        int index = 0;
        for(int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getId().intValue() == updateMovie.getId().intValue() ) {
                index = i;
                break;
            }
        }
        movies.set(index,updateMovie);
        return updateMovie;
    }

    @Override
    public Movie deleteMovie(Integer movieId) {
        int index = 0;
        for(int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getId().intValue() == movieId.intValue() ) {
                index = i;
                break;
            }
        }
        Movie movie = movies.get(index);
        movies.remove(index);
        return movie;
    }

    @Override
    public List<Movie> getReport(Integer startReleaseYear, Integer endReleaseYear) {
        List<Movie> myMovies = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getReleaseYear() >= startReleaseYear) {
                if(movie.getReleaseYear() <= endReleaseYear) {
                    myMovies.add(movie);
                }
            }
        }
        return myMovies;
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        title = title.toLowerCase();
        List<Movie> myMovies = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getTitle().toLowerCase().contains(title)) {
                myMovies.add(movie);
            }
        }
        return myMovies;
    }


}
