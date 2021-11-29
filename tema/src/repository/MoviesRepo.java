package repository;

import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.Input;
import fileio.MovieInputData;
import user.User;

import java.lang.reflect.Array;
import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.List;

public final class MoviesRepo {
    /**
     * List of movies
     */
    private final List<Movie> moviesData;

    public MoviesRepo(Input input) {
        moviesData = new ArrayList<>();
        for (MovieInputData inputMovie : input.getMovies()) {
            Movie newMovie = new Movie(inputMovie.getTitle(),
                    inputMovie.getCast(), inputMovie.getGenres(),
                    inputMovie.getYear(), inputMovie.getDuration());
            moviesData.add(newMovie);
        }
    }

    /**
     * Initialises the view number of all movies, according
     * to each user's view count
     * @param usersRepo
     */
    public void initialiseViewNumber(UsersRepo usersRepo) {
        for (Movie movie : moviesData) {
            for (User user : usersRepo.getUsersData()) {
                if (user.getHistory().containsKey(movie.getTitle())) {
                    for (int i = 0; i < user.getHistory().get(movie.getTitle()); i++) {
                        movie.increaseViewNumber();
                    }
                }
            }
        }
    }

    /**
     * Initialises the favorite number of all movies, according
     * to each user's view count
     * @param usersRepo
     */
    public void initialiseFavoriteNumber(UsersRepo usersRepo) {
        for (Movie movie : moviesData) {
            for (User user : usersRepo.getUsersData()) {
                if (user.getFavoriteMovies().contains(movie.getTitle())) {
                    movie.increaseFavoriteNumber();
                }
            }
        }
    }

    public List<Movie> getMoviesData() {
        return moviesData;
    }

    /**
     * Find the movie with a given title
     * @param movieTitle the searched title of the movie
     * @return the found movie
     */
    public Movie getMovieByTitle(String movieTitle) {
        for (Movie movie : moviesData) {
            if (movie.getTitle().equals(movieTitle)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns a list of show objects
     * containing all the rated movies
     * @return a show list of rated movies
     */
    public ArrayList<Show> getRatedMovies() {
        ArrayList<Show> ratedMoviesList = new ArrayList<Show>();
        for (Movie movie : moviesData) {
            if (movie.getAverageRating() > 0) {
                ratedMoviesList.add(movie);
            }
        }
        return ratedMoviesList;
    }

    /**
     * Get a show list of all movies who have been
     * added to favorite by the users
     * @return the favorite list of shows
     */
    public ArrayList<Show> getFavoriteMovies() {
        ArrayList<Show> favoriteMoviesList = new ArrayList<Show>();
        for (Movie movie : moviesData) {
            if (movie.getFavoriteNumber() != 0) {
                favoriteMoviesList.add(movie);
            }
        }
        return favoriteMoviesList;
    }

    /**
     * Create a list of show objects from the list of movie objects
     * @return the show objects
     */
    public ArrayList<Show> getMoviesShowData() {
        ArrayList<Show> showData = new ArrayList<Show>();
        for (Movie movie : moviesData) {
            showData.add(movie);
        }
        return showData;
    }

    /**
     * Get a show list of all viewed movies
     * by the users
     * @return the viewed list of shows
     */
    public ArrayList<Show> getViewedMovies() {
        ArrayList<Show> viewedMoviesList = new ArrayList<Show>();
        for (Movie movie : moviesData) {
            if (movie.getViewNumber() != 0) {
                viewedMoviesList.add(movie);
            }
        }
        return viewedMoviesList;
    }
}
