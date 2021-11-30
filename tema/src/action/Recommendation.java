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

import java.util.*;

public class Recommendation {
    /**
     * Sort video list by rating in ascending order
     */
    static class SortVideoByRatingAsc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o2.getAverageRating() == o1.getAverageRating()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getAverageRating() < o1.getAverageRating()) {
                return 1;
            }
            return -1;
        }
    }

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

    /**
     * Sort video list by favorite in descending order
     */
    static class SortVideoByFavoriteDesc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o1.getFavoriteNumber() == o2.getFavoriteNumber()) {
                return 0;
            }
            if (o1.getFavoriteNumber() < o2.getFavoriteNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view in descending order
     */
    static class SortVideoByViewsDesc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o1.getViewNumber() == o2.getViewNumber()) {
                return 0;
            }
            if (o1.getViewNumber() < o2.getViewNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     *
     * @param moviesRepo
     * @param serialsRepo
     * @return a hashmap which has the key as the genre and the value
     * as a list of shows
     */
    public Map<String, ArrayList<Show>> getGenreHashmap(MoviesRepo moviesRepo,
                                                          SerialsRepo serialsRepo) {
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

    public int getGenreViews(Map<String, ArrayList<Show>> genreHashmap, String genre) {
        int noViews = 0;
        for (Show show : genreHashmap.get(genre)) {
            noViews += show.getViewNumber();
        }
        return noViews;
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

    public String PopularRecommendation(ActionInputData inputData,
                                        User user, MoviesRepo moviesRepo,
                                        SerialsRepo serialsRepo) {
        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);
        ArrayList<Integer> genresViews = new ArrayList<Integer>();
        for (String genre : genreHashmap.keySet()) {
            genresViews.add(getGenreViews(genreHashmap, genre));
        }
        do{
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
                System.out.println("nu e bine deloc");
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

    public String FavoriteRecommendation(ActionInputData inputAction,
                                           User user, MoviesRepo moviesRepo,
                                           SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();

        sortedShows.addAll(moviesRepo.getFavoriteMovies());
        sortedShows.addAll(serialsRepo.getFavoriteSerials());

        Collections.sort(sortedShows, new Recommendation.SortVideoByFavoriteDesc());

        for (Show show : sortedShows) {
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
            }
        }
        return null;
    }

    public String SearchRecommendation(ActionInputData inputAction,
                                       User user, MoviesRepo moviesRepo,
                                       SerialsRepo serialsRepo) {
        StringBuilder message = new StringBuilder();
        message.append("[");

        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);
        if (!genreHashmap.containsKey(inputAction.getGenre())) {
            return null;
        }
        ArrayList<Show> genreShowsList = genreHashmap.get(inputAction.getGenre());
        Collections.sort(genreShowsList, new Recommendation.SortVideoByRatingAsc());

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
                showTitle = PopularRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null || !user.getSubscriptionType().equals(Constants.PREMIUM)) {
                    message = "PopularRecommendation cannot be applied!";
                } else {
                    message = "PopularRecommendation result: " + showTitle;
                }
            }
            case Constants.FAVORITE -> {
                showTitle = FavoriteRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null || !user.getSubscriptionType().equals(Constants.PREMIUM)) {
                    message = "FavoriteRecommendation cannot be applied!";
                } else {
                    message = "FavoriteRecommendation result: " + showTitle;
                }
            }
            case Constants.SEARCH -> {
                showTitle = SearchRecommendation(inputAction, user, moviesRepo, serialsRepo);
                if (showTitle == null || showTitle.equals(Constants.BRACES) || user == null || !user.getSubscriptionType().equals(Constants.PREMIUM)) {
                    message = "SearchRecommendation cannot be applied!";
                } else {
                    message = "SearchRecommendation result: " + showTitle;
                }
            }
        }
        return message;
    }
}
