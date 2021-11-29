package action;

import action.Query.QueryVideo;
import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;
import user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Recommendation {
    /**
     * Sort video list by rating in descending order
     */
    static class SortVideoByRatingDesc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o1.getAverageRating() == o2.getAverageRating()) {
                return 0;
            }
            if (o1.getAverageRating() < o2.getAverageRating()) {
                return 1;
            }
            return -1;
        }
    }

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

    public String bestUnseenRecommendation(ActionInputData inputAction,
                                           User user, MoviesRepo moviesRepo,
                                           SerialsRepo serialsRepo) {
        ArrayList<Show> sortedMoviesShows = new ArrayList<Show>();
        ArrayList<Show> sortedSerialsShows = new ArrayList<Show>();

        sortedMoviesShows.addAll(moviesRepo.getMoviesData());
        sortedSerialsShows.addAll(serialsRepo.getSerialsData());

        Collections.sort(sortedMoviesShows, new Recommendation.SortVideoByRatingDesc());
        Collections.sort(sortedSerialsShows, new Recommendation.SortVideoByRatingDesc());

        for (Show show : sortedMoviesShows) {
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
            }
        }

        for (Show show : sortedSerialsShows) {
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
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
                showTitle = bestUnseenRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null) {
                    message = "BestRatedUnseenRecommendation cannot be applied!";
                } else {
                message = "BestRatedUnseenRecommendation result: " + showTitle;
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
