package action;

import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;
import user.User;
import utils.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Recommendation {
    /**
     *
     * @param moviesRepo
     * @param serialsRepo
     * @return a hashmap which has the key as the genre and the value
     * as a list of shows
     */
    public Map<String, ArrayList<Show>> getGenreHashmap(final MoviesRepo moviesRepo,
                                                        final SerialsRepo serialsRepo) {
        Map<String, ArrayList<Show>> genreHashmap = new HashMap<>();
        for (Movie movie : moviesRepo.getMoviesData()) {
            for (String genre : movie.getGenres()) {
                if (genreHashmap.containsKey(genre)) {
                    genreHashmap.get(genre).add(movie);
                } else {
                    ArrayList<Show> auxiliaryList = new ArrayList<>();
                    auxiliaryList.add(movie);
                    genreHashmap.put(genre, auxiliaryList);
                }
            }
        }

        for (Serial serial : serialsRepo.getSerialsData()) {
            for (String genre : serial.getGenres()) {
                if (genreHashmap.containsKey(genre)) {
                    genreHashmap.get(genre).add(serial);
                } else {
                    ArrayList<Show> auxiliaryList = new ArrayList<>();
                    auxiliaryList.add(serial);
                    genreHashmap.put(genre, auxiliaryList);
                }
            }
        }
        return genreHashmap;
    }

    /**
     *
     * @param genreHashmap
     * @param genre
     * @return
     */
    public int getGenreViews(final Map<String, ArrayList<Show>> genreHashmap, final String genre) {
        int noViews = 0;
        for (Show show : genreHashmap.get(genre)) {
            noViews += show.getViewNumber();
        }
        return noViews;
    }

    /**
     *
     * @param inputAction
     * @param user
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String standardRecommendation(final ActionInputData inputAction,
                                         final User user, MoviesRepo moviesRepo,
                                         final SerialsRepo serialsRepo) {
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

    /**
     *
     * @param inputAction
     * @param user
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String bestUnseenRecommendation(final ActionInputData inputAction,
                                           final User user, MoviesRepo moviesRepo,
                                           final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedMoviesShows = new ArrayList<Show>();
        ArrayList<Show> sortedSerialsShows = new ArrayList<Show>();

        sortedMoviesShows.addAll(moviesRepo.getMoviesData());
        sortedSerialsShows.addAll(serialsRepo.getSerialsData());

        Collections.sort(sortedMoviesShows, new Comparators.SortVideoByRatingDesc());
        Collections.sort(sortedSerialsShows, new Comparators.SortVideoByRatingDesc());

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

    /**
     *
     * @param inputData
     * @param user
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String popularRecommendation(final ActionInputData inputData,
                                        final User user, final MoviesRepo moviesRepo,
                                        final SerialsRepo serialsRepo) {
        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);
        ArrayList<Integer> genresViews = new ArrayList<Integer>();
        for (String genre : genreHashmap.keySet()) {
            genresViews.add(getGenreViews(genreHashmap, genre));
        }
        do {
            Integer maxViews = Collections.max(genresViews);
            genresViews.remove(maxViews);
            String maxGenre = null;
            for (String genre : genreHashmap.keySet()) {
                if (getGenreViews(genreHashmap, genre) == maxViews) {
                    maxGenre = genre;
                    break;
                }
            }
            if (maxGenre == null) {
                return null;
            }
            for (Show show : genreHashmap.get(maxGenre)) {
                if (!user.viewedShow(show.getTitle())) {
                    return show.getTitle();
                }
            }
        } while (genresViews.size() > 0);
        return null;
    }

    /**
     *
     * @param inputAction
     * @param user
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String favoriteRecommendation(final ActionInputData inputAction,
                                         final User user, final MoviesRepo moviesRepo,
                                         final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();

        sortedShows.addAll(moviesRepo.getFavoriteMovies());
        sortedShows.addAll(serialsRepo.getFavoriteSerials());

        Collections.sort(sortedShows, new Comparators.SortVideoByFavoriteDesc());

        for (Show show : sortedShows) {
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
            }
        }
        return null;
    }

    /**
     *
     * @param inputAction
     * @param user
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String searchRecommendation(final ActionInputData inputAction,
                                       final User user, final MoviesRepo moviesRepo,
                                       final SerialsRepo serialsRepo) {
        StringBuilder message = new StringBuilder();
        message.append("[");

        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);
        if (!genreHashmap.containsKey(inputAction.getGenre())) {
            return null;
        }
        ArrayList<Show> genreShowsList = genreHashmap.get(inputAction.getGenre());
        Collections.sort(genreShowsList, new Comparators.SortVideoByRatingNameAsc());

        for (int i = 0; i < genreShowsList.size(); i++) {
            if (!user.viewedShow(genreShowsList.get(i).getTitle())) {
                if (i != 0 && !message.toString().equals("[")) {
                    message.append(", ");
                }
                message.append(genreShowsList.get(i).getTitle());
            }
        }
        message.append("]");
        return message.toString();
    }

    /**
     *
     * @param inputAction
     * @param usersRepo
     * @param moviesRepo
     * @param serialsRepo
     * @param recommendationType
     * @return
     */
    public String applyRecommendation(final ActionInputData inputAction,
                                      final UsersRepo usersRepo, final MoviesRepo moviesRepo,
                                      final SerialsRepo serialsRepo, final String recommendationType) {
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
                showTitle = popularRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null || !user.getSubscriptionType().
                        equals(Constants.PREMIUM)) {
                    message = "PopularRecommendation cannot be applied!";
                } else {
                    message = "PopularRecommendation result: " + showTitle;
                }
            }
            case Constants.FAVORITE -> {
                showTitle = favoriteRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null || !user.getSubscriptionType().
                        equals(Constants.PREMIUM)) {
                    message = "FavoriteRecommendation cannot be applied!";
                } else {
                    message = "FavoriteRecommendation result: " + showTitle;
                }
            }
            case Constants.SEARCH -> {
                showTitle = searchRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || showTitle.equals(Constants.BRACES) || user == null
                        || !user.getSubscriptionType().equals(Constants.PREMIUM)) {
                    message = "SearchRecommendation cannot be applied!";
                } else {
                    message = "SearchRecommendation result: " + showTitle;
                }
            }
            default -> {
                message = null;
            }
        }
        return message;
    }
}
