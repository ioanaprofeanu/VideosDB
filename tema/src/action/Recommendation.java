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
     * Create a hashmap which contains the genre of the shows within the database as key
     * and a list of all shows within that genre as value
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return a hashmap which has the genre as ket and the value as a list of shows
     */
    public Map<String, ArrayList<Show>> getGenreHashmap(final MoviesRepo moviesRepo,
                                                        final SerialsRepo serialsRepo) {
        Map<String, ArrayList<Show>> genreHashmap = new HashMap<>();

        // for each movie within the database
        for (Movie movie : moviesRepo.getMoviesData()) {
            // for each genre the movie has
            for (String genre : movie.getGenres()) {
                // if the hashmap already contains the genre, add the movie to the
                // value list of the key
                if (genreHashmap.containsKey(genre)) {
                    genreHashmap.get(genre).add(movie);
                } else {
                    // if the hashmap doesn't contain the genre, add new entry to the hashmap
                    ArrayList<Show> auxiliaryList = new ArrayList<>();
                    auxiliaryList.add(movie);
                    genreHashmap.put(genre, auxiliaryList);
                }
            }
        }

        // for each serial within the database
        for (Serial serial : serialsRepo.getSerialsData()) {
            // for each genre the serial has
            for (String genre : serial.getGenres()) {
                // if the hashmap already contains the genre, add the serial to the
                // value list of the key
                if (genreHashmap.containsKey(genre)) {
                    genreHashmap.get(genre).add(serial);
                } else {
                    // if the hashmap doesn't contain the genre, add new entry to the hashmap
                    ArrayList<Show> auxiliaryList = new ArrayList<>();
                    auxiliaryList.add(serial);
                    genreHashmap.put(genre, auxiliaryList);
                }
            }
        }

        return genreHashmap;
    }

    /**
     * Get the total views of all shows that have a given genre
     * @param genreHashmap the hashmap of genres and shows
     * @param genre the given genre
     * @return the number of views of the genre
     */
    public int getGenreViews(final Map<String, ArrayList<Show>> genreHashmap, final String genre) {
        int noViews = 0;
        for (Show show : genreHashmap.get(genre)) {
            noViews += show.getViewNumber();
        }

        return noViews;
    }

    /**
     * Get the first title of the show within the database the user has not seen
     * @param user the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the title of the show
     */
    public String standardRecommendation(final User user, final MoviesRepo moviesRepo,
                                         final SerialsRepo serialsRepo) {
        // to get the database order, first start searching within the movies
        // and afterwards within the serials
        for (Movie movie : moviesRepo.getMoviesData()) {
            // if the user hasn't watched the movie, return its title
            if (!user.viewedShow(movie.getTitle())) {
                return movie.getTitle();
            }
        }

        for (Serial serial : serialsRepo.getSerialsData()) {
            // if the user hasn't watched the serial, return its title
            if (!user.viewedShow(serial.getTitle())) {
                return serial.getTitle();
            }
        }

        return null;
    }

    /**
     * Get the first title of the best rated show the user hasn't seen
     * @param user the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the title of the best unseen show
     */
    public String bestUnseenRecommendation(final User user, final MoviesRepo moviesRepo,
                                           final SerialsRepo serialsRepo) {
        // create a list of all shows, first adding the movies and then the serials,
        // in order to get the shows in database order
        ArrayList<Show> sortedShows = new ArrayList<>();
        sortedShows.addAll(moviesRepo.getMoviesData());
        sortedShows.addAll(serialsRepo.getSerialsData());

        // sort the list by rating in descending order
        Collections.sort(sortedShows, new Comparators.SortVideoByRatingDesc());

        for (Show show : sortedShows) {
            // if the user hasn't watched the movie, return its title
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
            }
        }

        return null;
    }

    /**
     * Get the title of the first show within the most watched genre
     * that the user hasn't seen
     * @param user the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the title of the best unseen show
     */
    public String popularRecommendation(final User user, final MoviesRepo moviesRepo,
                                        final SerialsRepo serialsRepo) {
        // create the genre hashmap of genres and the shows within that genre
        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);

        // create an arraylist which contains the number of views each genre has
        ArrayList<Integer> genresViews = new ArrayList<Integer>();
        for (String genre : genreHashmap.keySet()) {
            genresViews.add(getGenreViews(genreHashmap, genre));
        }

        do {
            // get the maximum value of the genreViews list, then remove it from the list
            Integer maxViews = Collections.max(genresViews);
            genresViews.remove(maxViews);
            // search for the genre which has the found maximum value of views
            String maxGenre = null;
            for (String genre : genreHashmap.keySet()) {
                if (getGenreViews(genreHashmap, genre) == maxViews) {
                    maxGenre = genre;
                    break;
                }
            }
            // if the genre doesn't exist within the hashmap
            if (maxGenre == null) {
                return null;
            }
            // for each show within the genre
            for (Show show : genreHashmap.get(maxGenre)) {
                // if the user hasn't watched the show, return its title
                if (!user.viewedShow(show.getTitle())) {
                    return show.getTitle();
                }
            }
        } while (genresViews.size() > 0);

        return null;
    }

    /**
     * Get the title of the first most favorite show the user hasn't seen
     * @param user the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the title of the best unseen show
     */
    public String favoriteRecommendation(final User user, final MoviesRepo moviesRepo,
                                         final SerialsRepo serialsRepo) {
        // create a list of all shows favorite, first adding the movies and then the serials,
        // in order to get the shows in database order
        ArrayList<Show> sortedShows = new ArrayList<>();
        sortedShows.addAll(moviesRepo.getFavoriteMovies());
        sortedShows.addAll(serialsRepo.getFavoriteSerials());

        // sort the list by favorite in descending order
        Collections.sort(sortedShows, new Comparators.SortVideoByFavoriteDesc());

        for (Show show : sortedShows) {
            // if the user hasn't watched the movie, return its title
            if (!user.viewedShow(show.getTitle())) {
                return show.getTitle();
            }
        }

        return null;
    }

    /**
     * Get the titles of all shows within a given genre
     * @param inputAction the data of the action
     * @param user the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return a string with the list of titles within the genre
     */
    public String searchRecommendation(final ActionInputData inputAction,
                                       final User user, final MoviesRepo moviesRepo,
                                       final SerialsRepo serialsRepo) {
        StringBuilder message = new StringBuilder();
        message.append("[");

        // create the genre hashmap of genres and the shows within that genre
        Map<String, ArrayList<Show>> genreHashmap = getGenreHashmap(moviesRepo, serialsRepo);
        // if the wanted genre doesn't contain any show
        if (!genreHashmap.containsKey(inputAction.getGenre())) {
            return null;
        }

        // create a list using the shows within the genre
        ArrayList<Show> genreShowsList = genreHashmap.get(inputAction.getGenre());
        // sort the list by rating in ascending order
        Collections.sort(genreShowsList, new Comparators.SortVideoByRatingNameAsc());

        // iterate through the shows list
        for (int i = 0; i < genreShowsList.size(); i++) {
            // if the user hasn't watched the show
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
     * Apply the recommendation depending on the type
     * @param inputAction the data of the action
     * @param usersRepo the users database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @param recommendationType the input recommendation type
     * @return
     */
    public String applyRecommendation(final ActionInputData inputAction,
                                      final UsersRepo usersRepo,
                                      final MoviesRepo moviesRepo,
                                      final SerialsRepo serialsRepo,
                                      final String recommendationType) {
        User user = usersRepo.getUserByUsername(inputAction.getUsername());
        String message = null;
        String showTitle = null;

        // for each recommendation type, call the method that the output of the
        // recommendation; if the output is an exception, print adequate message
        switch (recommendationType) {
            case Constants.STANDARD -> {
                showTitle = standardRecommendation(user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null) {
                    message = "StandardRecommendation cannot be applied!";
                } else {
                    message = "StandardRecommendation result: " + showTitle;
                }
            }
            case Constants.BEST_UNSEEN -> {
                showTitle = bestUnseenRecommendation(user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null) {
                    message = "BestRatedUnseenRecommendation cannot be applied!";
                } else {
                    message = "BestRatedUnseenRecommendation result: " + showTitle;
                }
            }
            case Constants.POPULAR  -> {
                showTitle = popularRecommendation(user, moviesRepo, serialsRepo);
                if (showTitle == null || user == null || !user.getSubscriptionType().
                        equals(Constants.PREMIUM)) {
                    message = "PopularRecommendation cannot be applied!";
                } else {
                    message = "PopularRecommendation result: " + showTitle;
                }
            }
            case Constants.FAVORITE -> {
                showTitle = favoriteRecommendation(user, moviesRepo, serialsRepo);
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
