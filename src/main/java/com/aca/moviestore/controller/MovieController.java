package com.aca.moviestore.controller;

import com.aca.moviestore.model.Genre;
import com.aca.moviestore.model.Movie;
import com.aca.moviestore.service.MovieService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)

public class MovieController {

    private final MovieService service = new MovieService();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Movie updateMovie(Movie movie) {
        System.out.println(movie);
        return service.updateMovie(movie);
    }

    @DELETE
    @Path("/{movieIdValue}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Movie deleteMovie(@PathParam("movieIdValue") Integer movieId) {
        System.out.println("movieId: " + movieId);
        return service.deleteMovie(movieId);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Movie createMovie(Movie movie) {

        System.out.println(movie);
        return service.createMovie(movie);
    }

    @GET
    public List<Movie> getMovie() {
        return service.getMovies();
    }

    @GET
    @Path("/genre/{genreValue}")
    public List<Movie> getMoviesByGenre(@PathParam("genreValue") Genre genre) {
        System.out.println("genre: " + genre);
        return service.getMoviesByGenre(genre);
    }

    @GET
    @Path("/releaseyear/{releaseYearValue}")
    public List<Movie> getMoviesByReleaseYear(@PathParam("releaseYearValue") Integer releaseYear) {
        System.out.println("releaseYear: " + releaseYear);
        return service.getMoviesByReleaseYear(releaseYear);
    }

    @GET
    @Path("/{idValue}")
    public List<Movie> getMoviesById(@PathParam("idValue") Integer movieId) {
        System.out.println("id: " + movieId);
        return service.getMoviesById(movieId);
    }

    @GET
    @Path("/report")
    public List<Movie> getReport(
            @QueryParam("startReleaseYear") Integer startReleaseYear,
            @QueryParam("endReleaseYear") Integer endReleaseYear) {
        return service.getReport(startReleaseYear, endReleaseYear);
    }

    @GET
    @Path("/title/{titleValue}")
    public List<Movie> getMoviesByTitle(@PathParam("titleValue") String title) {
        System.out.println("title: " + title);
        return service.getMoviesByTitle(title);
    }


}
