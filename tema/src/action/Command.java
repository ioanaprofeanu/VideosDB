package action;

import entertainment.Serial;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;
import user.User;

import java.util.ArrayList;

public class Command {
    /**
     * Adds the show to the list of favorite shows of a user
     * @param inputAction the data of the action
     * @param usersRepo the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the output message
     */
    public String addFavorite(final ActionInputData inputAction,
                            final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                            final SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            // if the user has not seen the show or is already within the favorites list
            if (!user.viewedShow(showTitle)) {
                message = "error -> " + showTitle + " is not seen";
            } else if (user.getFavoriteMovies().contains(showTitle)) {
                message = "error -> " + showTitle + " is already in favourite list";
            } else {
                // add the title to the favourite movies list and increase
                // the favorite number of the show
                if (moviesRepo.getMovieByTitle(showTitle) != null) {
                    user.getFavoriteMovies().add(showTitle);
                    moviesRepo.getMovieByTitle(showTitle).increaseFavoriteNumber();
                } else if (serialsRepo.getSerialByTitle(showTitle) != null) {
                    user.getFavoriteMovies().add(showTitle);
                    serialsRepo.getSerialByTitle(showTitle).increaseFavoriteNumber();
                }
                message = "success -> " + showTitle + " was added as favourite";
            }
        }

        return message;
    }

    /**
     * Adds the show to the map of viewed shows of a user
     * @param inputAction the data of the action
     * @param usersRepo the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the output message
     */
    public String addView(final ActionInputData inputAction,
                          final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                          final SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            // increase or add the views in the history map
            user.addHistoryViews(showTitle);
            // increase the view number of the show
            if (moviesRepo.getMovieByTitle(showTitle) != null) {
                moviesRepo.getMovieByTitle(showTitle).increaseViewNumber();
            } else if (serialsRepo.getSerialByTitle(showTitle) != null) {
                serialsRepo.getSerialByTitle(showTitle).increaseViewNumber();
            }
            message =  "success -> "
                    + showTitle
                    + " was viewed with total views of "
                    + user.numberOfHistoryViews(showTitle);
        }

        return message;
    }

    /**
     * Adds the show to the list of rated shows of a user
     * @param inputAction the data of the action
     * @param usersRepo the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the output message
     */
    public String addRating(final ActionInputData inputAction,
                            final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                            final SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        double grade = inputAction.getGrade();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            // if the user has seen the show
            if (user.viewedShow(showTitle)) {
                // if the show is a movie
                if (moviesRepo.getMovieByTitle(showTitle) != null) {
                    // if the user hasn't rated the movie yet
                    if (!user.getRatedMovies().contains(showTitle)) {
                        // add rating to the movie's list of grades and
                        // to the user's list of rated movies
                        moviesRepo.getMovieByTitle(showTitle).getRatings().add(grade);
                        user.getRatedMovies().add(showTitle);
                    } else {
                        message = "error -> " + showTitle + " has been already rated";
                        return message;
                    }
                    // if the show is a serial
                } else if (serialsRepo.getSerialByTitle(showTitle) != null) {
                    Serial serial = serialsRepo.getSerialByTitle(showTitle);
                    // if the user has started rated any season from the serial
                    if (serial.getRatedSeasonByUsers().containsKey(user.getUsername())) {
                        // if the to-be-rated season has already been rated
                        if (serial.getRatedSeasonByUsers().get(user.getUsername()).
                                contains(inputAction.getSeasonNumber())) {
                            message = "error -> " + showTitle + " has been already rated";
                            return message;
                        } else {
                            // if the serial was rated but the current the season hasn't been rated
                            // yet, add it to the list of hashmap values to
                            // whom the username is th key
                            serial.getRatedSeasonByUsers().get(user.getUsername()).
                                    add(inputAction.getSeasonNumber());
                        }
                    } else {
                        // if the serial has never been rated, add new entry to the hashmap,
                        // with the username as key and the season number as the first element
                        // of the value list
                        ArrayList<Integer> auxiliaryList = new ArrayList<>();
                        auxiliaryList.add(inputAction.getSeasonNumber());
                        serial.getRatedSeasonByUsers().put(user.getUsername(), auxiliaryList);
                    }
                    // add rating to the serial's season list of grades and
                    // to the user's list of rated movies
                    serialsRepo.getSerialByTitle(showTitle).
                            addSeasonRating(inputAction.getSeasonNumber(), grade);
                    user.getRatedMovies().add(showTitle);
                }
                message = "success -> " + showTitle + " was rated with "
                        + grade + " by " + user.getUsername();
            } else {
                message = "error -> " + showTitle + " is not seen";
                return message;
            }
        }

        return message;
    }
}
