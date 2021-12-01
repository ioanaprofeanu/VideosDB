package action.Query;

import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;
import utils.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryVideo {
    /**
     * Filter a show list by genre and year
     * @param inputAction the data of the action
     * @param showList an input show list
     * @return the show list filtered by genre and year
     */
    public ArrayList<Show> filteredShows(final ActionInputData inputAction,
                                               final ArrayList<Show> showList) {
        // initialise the filtered and sorted show list as a copy
        // of the input show list
        ArrayList<Show> filteredList = new ArrayList<>(showList);
        // get the filters genre and year lists
        List<String> genreList = inputAction.getFilters().get(Constants.GENRE_FIELD_FILTERS);
        List<String> yearList = inputAction.getFilters().get(Constants.YEAR_FIELD_FILTERS);

        // for each show from the show list
        for (Show show : showList) {
            // if the input genre list is not null
            if (genreList.get(0) != null) {
                // for each genre in the genre list
                for (String genre : genreList) {
                    // check if the current show contains the genre in its genre list;
                    // otherwise, remove it from the filtered and sorted list
                    if (!show.getGenres().contains(genre)) {
                        filteredList.remove(show);
                    }
                }
            }
            // if the input year list is not null
            if (yearList.get(0) != null) {
                // for each year in the year list
                for (String year : yearList) {
                    // check if the year matches the show's year; otherwise,
                    // remove it from the filtered and sorted list
                    if (!Integer.toString(show.getYear()).equals(year)) {
                        filteredList.remove(show);
                    }
                }
            }
        }

        return filteredList;
    }

    /**
     * Create the list of shows sorted by their rating and by the given filters
     * @param inputAction the data of the action
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the filtered and sorted by rating list of shows
     */
    public ArrayList<Show> getRatingsShowList(final ActionInputData inputAction,
                                              final MoviesRepo moviesRepo,
                                              final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<>();
        // get the rated movies or serials list, filter it and add it
        // to the new list, depending on the object type of the input action
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredShows(inputAction, moviesRepo.getRatedMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredShows(inputAction, serialsRepo.getRatedSerials()));
        }

        // sort in ascending or descending order by rating and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByRatingNameAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByRatingNameDesc());
        }

        return sortedShows;
    }

    /**
     * Create the list of shows sorted by favorite and by the given filters
     * @param inputAction the data of the action
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the filtered and sorted by favorite list of shows
     */
    public ArrayList<Show> getFavoriteShowList(final ActionInputData inputAction,
                                               final MoviesRepo moviesRepo,
                                               final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<>();
        // get the favorite movies or serials list, filter it and add it
        // to the new list, depending on the object type of the input action
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredShows(inputAction, moviesRepo.getFavoriteMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredShows(inputAction, serialsRepo.getFavoriteSerials()));
        }

        // sort in ascending or descending order by favorite and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByFavoriteNameAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByFavoriteNameDesc());
        }

        return sortedShows;
    }

    /**
     * Create the list of shows sorted by duration and by the given filters
     * @param inputAction the data of the action
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the filtered and sorted by duration list of shows
     */
    public ArrayList<Show> getLongestShowList(final ActionInputData inputAction,
                                              final MoviesRepo moviesRepo,
                                              final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<>();
        // get all movies or serials list, filter it and add it
        // to the new list, depending on the object type of the input action
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredShows(inputAction, moviesRepo.getMoviesShowData()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredShows(inputAction, serialsRepo.getSerialShowData()));
        }

        // sort in ascending or descending order by duration and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByDurationNameAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByDurationNameDesc());
        }

        return sortedShows;
    }

    /**
     * Create the list of shows sorted by views and by the given filters
     * @param inputAction the data of the action
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the filtered and sorted by views list of shows
     */
    public ArrayList<Show> getMostViewedShowList(final ActionInputData inputAction,
                                                 final MoviesRepo moviesRepo,
                                                 final SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<>();
        // get the viewed movies or serials list, filter it and add it
        // to the new list, depending on the object type of the input action
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredShows(inputAction, moviesRepo.getViewedMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredShows(inputAction, serialsRepo.getViewedSerials()));
        }

        // sort in ascending or descending order by views and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByViewsNameAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new Comparators.SortVideoByViewsNameDesc());
        }

        return sortedShows;
    }
    /**
     * Apply the query depending on the type
     * @param inputAction the data of the action
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @param queryType the input query type
     * @return the output message
     */
    public String applyQuery(final ActionInputData inputAction, final MoviesRepo moviesRepo,
                             final SerialsRepo serialsRepo, final String queryType) {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        // build an arraylist containing the shows sorted by query type,
        // which have the wanted genre & year
        ArrayList<Show> finalSortedShows = new ArrayList<>();

        // for each query type, call the method that returns the filtered
        // and sorted list of shows
        switch (queryType) {
            case Constants.RATINGS -> {
                finalSortedShows = getRatingsShowList(inputAction, moviesRepo, serialsRepo);
            }
            case Constants.FAVORITE -> {
                finalSortedShows = getFavoriteShowList(inputAction, moviesRepo, serialsRepo);
            }
            case Constants.LONGEST -> {
                finalSortedShows = getLongestShowList(inputAction, moviesRepo, serialsRepo);
            }
            case Constants.MOST_VIEWED -> {
                finalSortedShows = getMostViewedShowList(inputAction, moviesRepo, serialsRepo);
            }
            default -> {
                finalSortedShows = null;
            }
        }

        int numberListElem = inputAction.getNumber();
        // if the list is smaller than the wanted size
        if (finalSortedShows.size() < numberListElem) {
            numberListElem = finalSortedShows.size();
        }

        // print the wanted number of show titles
        for (int i = 0; i < numberListElem; i++) {
            message.append(finalSortedShows.get(i).getTitle());
            if (i < numberListElem - 1) {
                message.append(", ");
            }
        }
        message.append("]");
        return message.toString();
    }
}
