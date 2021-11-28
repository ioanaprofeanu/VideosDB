package action.Query;

import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueryVideo {
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
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getAverageRating() < o2.getAverageRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by favorite in ascending order
     */
    static class SortVideoByFavoriteAsc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o2.getFavoriteNumber() == o1.getFavoriteNumber()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getFavoriteNumber() < o1.getFavoriteNumber()) {
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
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getFavoriteNumber() < o2.getFavoriteNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by duration in ascending order
     */
    static class SortVideoByDurationAsc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o2.getDuration() == o1.getDuration()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getDuration() < o1.getDuration()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view in descending order
     */
    static class SortVideoByDurationDesc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o1.getDuration() == o2.getDuration()) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getDuration() < o2.getDuration()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view in ascending order
     */
    static class SortVideoByViewsAsc implements Comparator<Show> {
        @Override
        public int compare(Show o1, Show o2) {
            if (o2.getViewNumber() == o1.getViewNumber()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getViewNumber() < o1.getViewNumber()) {
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
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getViewNumber() < o2.getViewNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Filter a show list by genre and year
     * @param inputData
     * @param sortedShowList a sorted input show list
     * @return the list filtered by genre and year
     */
    public ArrayList<Show> filteredSortedShows(ActionInputData inputData, ArrayList<Show> sortedShowList) {
        ArrayList<Show> filteredSortedList = new ArrayList<>(sortedShowList);
        List<String> genreList = inputData.getFilters().get(Constants.GENRE_FIELD_FILTERS);
        List<String> yearList = inputData.getFilters().get(Constants.YEAR_FIELD_FILTERS);

        for (Show show : sortedShowList) {
            System.out.println(show.getTitle());
        }
        // for each show from the sorted show list
        for (Show show : sortedShowList) {
            // if the input genre list is not null
            if (genreList != null) {
                // for each genre in the genre list
                for (String genre : genreList) {
                    // check if the current show contains the genre in its genre list;
                    // otherwise, remove it from the filtered and sorted list
                    if (!show.getGenres().contains(genre)) {
                        filteredSortedList.remove(show);
                    }
                }
            }
            if (yearList != null) {
                for (String year : yearList) {
                    if (!Integer.toString(show.getYear()).equals(year)) {
                        filteredSortedList.remove(show);
                    }
                }
            }
        }
        for (Show show : filteredSortedList) {
            System.out.println(show.getTitle());
        }
        return filteredSortedList;
    }

    /**
     * Returns the ratings list filtered and sorted
     * @param inputAction
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public ArrayList<Show> getRatingsShowList(ActionInputData inputAction,
                                              MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredSortedShows(inputAction, moviesRepo.getRatedMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredSortedShows(inputAction, serialsRepo.getRatedSerials()));
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new SortVideoByRatingAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new SortVideoByRatingDesc());
        }
        return sortedShows;
    }

    /**
     * Returns the favorites list filtered and sorted
     * @param inputAction
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public ArrayList<Show> getFavoriteShowList(ActionInputData inputAction,
                                              MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredSortedShows(inputAction, moviesRepo.getFavoriteMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredSortedShows(inputAction, serialsRepo.getFavoriteSerials()));
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new SortVideoByFavoriteAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new SortVideoByFavoriteDesc());
        }
        return sortedShows;
    }

    /**
     * Returns the list of shows filtered and sorted by length
     * @param inputAction
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public ArrayList<Show> getLongestShowList(ActionInputData inputAction,
                                               MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredSortedShows(inputAction, moviesRepo.getMoviesShowData()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredSortedShows(inputAction, serialsRepo.getSerialShowData()));
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new SortVideoByDurationAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new SortVideoByDurationDesc());
        }
        return sortedShows;
    }

    /**
     * Returns the viewed list filtered and sorted
     * @param inputAction
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public ArrayList<Show> getMostViewedShowList(ActionInputData inputAction,
                                               MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Show> sortedShows = new ArrayList<Show>();
        if (inputAction.getObjectType().equals(Constants.MOVIES)) {
            sortedShows.addAll(filteredSortedShows(inputAction, moviesRepo.getViewedMovies()));
        } else if (inputAction.getObjectType().equals(Constants.SHOWS)) {
            sortedShows.addAll(filteredSortedShows(inputAction, serialsRepo.getViewedSerials()));
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedShows, new SortVideoByViewsAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(sortedShows, new SortVideoByViewsDesc());
        }
        return sortedShows;
    }
    /**
     * Apply the query depending on the type
     * @param inputAction
     * @param moviesRepo
     * @param serialsRepo
     * @return
     */
    public String applyQuery(ActionInputData inputAction,
                                    MoviesRepo moviesRepo, SerialsRepo serialsRepo,
                                    String queryType) {
            StringBuilder message = new StringBuilder();
            message.append("Query result: [");
            // build an arraylist containing the shows sorted by query type,
            // which have the wanted genre & year
            ArrayList<Show> finalSortedShows = new ArrayList<Show>();
            switch (queryType) {
                case Constants.RATINGS -> {
                    finalSortedShows = getRatingsShowList(inputAction, moviesRepo, serialsRepo);
                }
                case Constants.FAVORITE -> {
                    finalSortedShows = getFavoriteShowList(inputAction, moviesRepo, serialsRepo);
                }
                case Constants.LONGEST  -> {
                    finalSortedShows = getLongestShowList(inputAction, moviesRepo, serialsRepo);
                }
                case Constants.MOST_VIEWED -> {
                    finalSortedShows = getMostViewedShowList(inputAction, moviesRepo, serialsRepo);
                }
            }

            int numberListElem = inputAction.getNumber();
            // if the list is smaller than the wanted size
            if (finalSortedShows.size() < numberListElem) {
                numberListElem = finalSortedShows.size();
            }
            // print titles
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
