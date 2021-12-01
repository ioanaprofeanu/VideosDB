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
     *
     * @param inputAction
     * @param usersRepo
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String addFavorite(final ActionInputData inputAction,
                            final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                            final SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            if (!user.viewedShow(showTitle)) {
                message = "error -> " + showTitle + " is not seen";
            } else if (user.getFavoriteMovies().contains(showTitle)) {
                message = "error -> " + showTitle + " is already in favourite list";
            } else {
                // add the title to the favourite movies list and increase the favourite
                // number of the show
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
     *
     * @param inputAction
     * @param usersRepo
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String addView(final ActionInputData inputAction,
                          final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                          final SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            // increase or add the views in the history hashmap
            user.addHistoryViews(showTitle);
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
     *
     * @param inputAction
     * @param usersRepo
     * @param moviesRepo
     * @param serialsRepo
     * @return
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
                    // if the user hasn't watched the movie yet
                    if (!user.getRatedMovies().contains(showTitle)) {
                        // add rating to the list of ratings
                        moviesRepo.getMovieByTitle(showTitle).getRatings().add(grade);
                        user.getRatedMovies().add(showTitle);
                    } else {
                        message = "error -> " + showTitle + " has been already rated";
                        return message;
                    }
                    // if the show is a serial
                } else if (serialsRepo.getSerialByTitle(showTitle) != null) {
                    Serial serial = serialsRepo.getSerialByTitle(showTitle);
                    // if the user has started rated a season from the serial
                    if (serial.getRatedSeasonByUsers().containsKey(user.getUsername())) {
                        // if the rated season is the one that is being rated now
                        if (serial.getRatedSeasonByUsers().get(user.getUsername()).
                                contains(inputAction.getSeasonNumber())) {
                            message = "error -> " + showTitle + " has been already rated";
                            return message;
                        } else {
                            // if it is a new season, add it to the list
                            serial.getRatedSeasonByUsers().get(user.getUsername()).
                                    add(inputAction.getSeasonNumber());
                        }
                    } else {
                        ArrayList<Integer> auxiliaryList = new ArrayList<>();
                        auxiliaryList.add(inputAction.getSeasonNumber());
                        serial.getRatedSeasonByUsers().put(user.getUsername(), auxiliaryList);
                    }
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
