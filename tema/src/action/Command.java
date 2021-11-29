package action;

import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;
import user.User;

public class Command {
    public String addFavorite(ActionInputData inputAction,
                            UsersRepo usersRepo, MoviesRepo moviesRepo,
                            SerialsRepo serialsRepo) {
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

    public String addView(ActionInputData inputAction,
                          UsersRepo usersRepo, MoviesRepo moviesRepo,
                          SerialsRepo serialsRepo) {
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
            message =  "success -> " +
                    showTitle +
                    " was viewed with total views of " +
                    user.numberOfHistoryViews(showTitle);
        }
        return message;
    }

    public String addRating(ActionInputData inputAction,
                            UsersRepo usersRepo, MoviesRepo moviesRepo,
                            SerialsRepo serialsRepo) {
        String message = null;
        String showTitle = inputAction.getTitle();
        double grade = inputAction.getGrade();
        User user = usersRepo.getUserByUsername(inputAction.getUsername());

        if (user != null) {
            // if the user has seen the show
            if (user.viewedShow(showTitle)) {
                // if the show isn't rated yet
                if (!user.getRatedMovies().contains(showTitle)) {
                    if (moviesRepo.getMovieByTitle(showTitle) != null) {
                        // add rating to the list of ratings
                        moviesRepo.getMovieByTitle(showTitle).getRatings().add(grade);
                        user.getRatedMovies().add(showTitle);
                    } else if (serialsRepo.getSerialByTitle(showTitle) != null) {
                        // add rating to the list of ratings of the serial's season
                        serialsRepo.getSerialByTitle(showTitle).addSeasonRating(inputAction.getSeasonNumber(), grade);
                    }
                    user.getRatedMovies().add(showTitle);
                    message = "success -> " + showTitle + " was rated with " + grade + " by " + user.getUsername();
                } else {
                    message = "error -> " + showTitle + " has been already rated";
                }
            } else {
                message = "error -> " + showTitle + " is not seen";
            }
        }
        return message;
    }
}
