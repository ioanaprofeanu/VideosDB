package repository;

import entertainment.Movie;
import fileio.Input;
import fileio.MovieInputData;

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
}
