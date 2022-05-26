package com.aca.moviestore.dao;

import com.aca.moviestore.model.Genre;
import com.aca.moviestore.model.Movie;
import jakarta.ws.rs.QueryParam;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {

    private final static String columnNames =
            "SELECT id, title, releaseYear, genreId, updateDateTime, createDateTime ";

    private final static String selectAllMovies =
            columnNames +
                "FROM movies";

    private final static String selectMoviesByGenre =
            columnNames +
                "FROM movies " +
                "WHERE genreId = ?";

    private final static String selectMoviesByReleaseYear =
            columnNames +
                "FROM movies " +
                "WHERE releaseYear = ?";

    private final static String selectMoviesById =
            columnNames +
                    "FROM movies " +
                    "WHERE id = ?";

    private final static String selectMoviesByTitle =
            columnNames +
                "FROM movies " +
                "WHERE title LIKE ?";

    private final static String deleteMoviesById =
            "DELETE FROM movies " +
                    "WHERE id = ?";

    private final static String createMovie =
            "INSERT INTO movies (title, releaseYear, genreId) " +
            "VALUES " +
            "(?,?,?)";
    private final static String updateMovieById =
            "UPDATE movies " +
            "SET title = ?, " +
            "releaseYear = ?, " +
            "genreId = ?" +
            "WHERE id = ?";

    private final static String selectMoviesByRange =
            columnNames +
            "FROM movies " +
            "WHERE releaseYear >= ? " +
            "AND releaseYear <= ?";

    private final static String selectNewMovieId =
            "SELECT LAST_INSERT_ID() AS 'movieId'";



    @Override
    public List<Movie> getMovies() {
        List<Movie> myMovies = new ArrayList<>();
        ResultSet result = null;
        Statement statement = null;

        Connection conn = MariaDbUtil.getConnection();

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(selectAllMovies);
            myMovies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myMovies;
    }

    private List<Movie> makeMovie(ResultSet result) throws SQLException {
        List<Movie> myMovies = new ArrayList<>();
        while(result.next()) {
            Movie movie = new Movie();
            movie.setId(result.getInt("id"));
            movie.setReleaseYear(result.getInt("releaseYear"));
            movie.setTitle(result.getString("title"));
            movie.setGenre(Genre.convertStringToGenre(result.getString("genreId")));

            movie.setUpdateDateTime(result.getObject("updateDateTime", LocalDateTime.class));
            movie.setCreateDateTime(result.getObject("createDateTime", LocalDateTime.class));
            myMovies.add(movie);
        }
        return myMovies;
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        List<Movie> myMovies = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement prepared = null;
        Connection connection = MariaDbUtil.getConnection();
        try {
            prepared = connection.prepareStatement(selectMoviesByGenre);
            prepared.setString(1, genre.toString());
            result = prepared.executeQuery();
            myMovies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
                prepared.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return myMovies;
    }

    @Override
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        List<Movie> myMovies = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement prepared = null;
        Connection connection = MariaDbUtil.getConnection();
        try {
            prepared = connection.prepareStatement(selectMoviesByReleaseYear);
            prepared.setInt(1, releaseYear);
            result = prepared.executeQuery();
            myMovies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myMovies;
    }

    @Override
    public List<Movie> getMoviesById(Integer movieId) {
        List<Movie> myMovies = new ArrayList<>();
        Connection connection = MariaDbUtil.getConnection();
        try {
            PreparedStatement prepared = connection.prepareStatement(selectMoviesById);
            prepared.setInt(1, movieId);

            ResultSet result = prepared.executeQuery();
            myMovies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myMovies;
    }

    @Override
    public Movie createMovie(Movie movie) {
        int updateRowCount = 0;
        PreparedStatement prepared = null;
        Connection connection = MariaDbUtil.getConnection();
        try {
            prepared = connection.prepareStatement(createMovie);

            prepared.setString(1, movie.getTitle());
            prepared.setInt(2, movie.getReleaseYear());
            prepared.setString(3, movie.getGenre().toString());
            updateRowCount = prepared.executeUpdate();
            System.out.println("rows inserted: " + updateRowCount);
            int movieId = this.getNewMovieId(connection);
            movie.setId(movieId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert prepared != null;
                prepared.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        return movie;
    }

    @Override
    public Movie updateMovie(Movie updateMovie) {
        List<Movie> movies = getMoviesById(updateMovie.getId());

        if(movies.size() == 1) {
            int updateRowCount = 0;
            PreparedStatement prepared = null;
            Connection connection = MariaDbUtil.getConnection();
            try {
                prepared = connection.prepareStatement(updateMovieById);
                prepared.setString(1,updateMovie.getTitle());
                prepared.setInt(2, updateMovie.getReleaseYear());
                prepared.setString(3, updateMovie.getGenre().toString());
                prepared.setInt(4, updateMovie.getId());
                updateRowCount = prepared.executeUpdate();
                System.out.println("rows updated: " + updateRowCount);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert prepared != null;
                    prepared.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return updateMovie;
    }

    @Override
    public Movie deleteMovie(Integer movieId) {
        List<Movie> movies = this.getMoviesById(movieId);
        Movie movieToDelete = null;

        if(movies.size() > 0) {
            movieToDelete = movies.get(0);
            int updateRowCount = 0;
            PreparedStatement prepared = null;
            Connection connection = MariaDbUtil.getConnection();
            try {
                prepared = connection.prepareStatement(deleteMoviesById);
                prepared.setInt(1, movieId);
                updateRowCount = prepared.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert prepared != null;
                    prepared.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return movieToDelete;
    }

    @Override
    public List<Movie> getReport(Integer startReleaseYear, Integer endReleaseYear) {
        List<Movie> myMovies = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement prepared = null;
        Connection connection = MariaDbUtil.getConnection();
        try {
            prepared = connection.prepareStatement(selectMoviesByRange);
            prepared.setInt(1, startReleaseYear);
            prepared.setInt(2, endReleaseYear);
            result = prepared.executeQuery();
            myMovies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
                prepared.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return myMovies;
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        Connection connection = MariaDbUtil.getConnection();
        try {
            PreparedStatement prepared = connection.prepareStatement(selectMoviesByTitle);
            prepared.setString(1, "%" + title + "%");
            ResultSet result = prepared.executeQuery();
            movies = makeMovie(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private int getNewMovieId(Connection connection) {
        ResultSet result = null;
        Statement statement = null;
        int newMovieId = 0;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(selectNewMovieId);
            while(result.next()) {
                newMovieId = result.getInt("movieId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return newMovieId;
    }

}
