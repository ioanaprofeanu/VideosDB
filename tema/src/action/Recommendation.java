package action;

import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;
import user.User;

public class Recommendation {
    public String standardRecommendation(ActionInputData inputAction,
                                         User user, MoviesRepo moviesRepo,
                                         SerialsRepo serialsRepo) {
        for (Movie movie : moviesRepo.getMoviesData()) {
            if (!user.viewedShow(movie.getTitle())) {
                return movie.getTitle();
            }
        }

        for (Serial serial : serialsRepo.getSerialsData()) {
            if (!user.viewedShow(serial.getTitle())) {
                return serial.getTitle();
            }
        }
        return null;
    }

    public String applyRecommendation(ActionInputData inputAction,
                                      UsersRepo usersRepo, MoviesRepo moviesRepo,
                                      SerialsRepo serialsRepo, String recommendationType) {
        User user = usersRepo.getUserByUsername(inputAction.getUsername());
        String message = null;
        String showTitle = null;
        switch (recommendationType) {
            case Constants.STANDARD -> {
                showTitle = standardRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null) {
                    message = "StandardRecommendation cannot be applied!";
                } else {
                    message = "StandardRecommendation result: " + showTitle;
                }
            }
            case Constants.BEST_UNSEEN -> {
                if (showTitle == null || user == null) {
                    message = "BestRatedUnseenRecommendation cannot be applied!";
                }
            }
            case Constants.POPULAR  -> {
                if (showTitle == null || user == null) {
                    message = "PopularRecommendation cannot be applied!";
                }
            }
            case Constants.FAVORITE -> {
                if (showTitle == null || user == null) {
                    message = "FavoriteRecommendation cannot be applied!";
                }
            }
            case Constants.SEARCH -> {
                if (showTitle == null || user == null) {
                    message = "SearchRecommendation cannot be applied!";
                }
            }
        }
        return message;
    }
}
