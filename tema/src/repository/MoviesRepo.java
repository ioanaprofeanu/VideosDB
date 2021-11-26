package repository;

import entertainment.Movie;
import fileio.Input;
import fileio.MovieInputData;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class MoviesRepo {
    /**
     * List of movies
     */
    private List<Movie> moviesData;

    public MoviesRepo(Input input) {
        moviesData = new ArrayList<>();
        for (MovieInputData inputMovie : input.getMovies()) {
            Movie newMovie = new Movie(inputMovie.getTitle(),
                    inputMovie.getCast(), inputMovie.getGenres(),
                    inputMovie.getYear(), inputMovie.getDuration());
            moviesData.add(newMovie);
        }
    }

    public List<Movie> getMoviesData() {
        return moviesData;
    }

    public void setMoviesData(List<Movie> moviesData) {
        this.moviesData = moviesData;
    }

    /**
     * Find the movie with a given title
     * @param movieTitle the searched title of the movie
     * @return the found movie
     */
    public Movie getMovieByTitle(String movieTitle) {
        for (Movie movie : moviesData) {
            if (equals(movie.getTitle().equals(movieTitle))) {
                return movie;
            }
        }
        return null;
    }
}
