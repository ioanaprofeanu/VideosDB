package action.Query;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import repository.ActorsRepo;
import repository.MoviesRepo;
import repository.SerialsRepo;
import utils.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryActor {
    /**
     * Create the list of actors sorted by their average rating
     * @param inputAction the data of the action
     * @param actorsRepo the actors database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the sorted by rating actors list
     */
    public ArrayList<Actor> getAverageActorList(final ActionInputData inputAction,
                                                final ActorsRepo actorsRepo,
                                                final MoviesRepo moviesRepo,
                                                final SerialsRepo serialsRepo) {
        // set the average ratings of all actors
        actorsRepo.setActorsRating(moviesRepo, serialsRepo);
        // initialise the sorted list with the list of rated actors
        ArrayList<Actor> sortedActorsList = actorsRepo.getRatedActors();

        // sort in ascending or descending order by rating and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedActorsList, new Comparators.SortActorByAverageAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(sortedActorsList, new Comparators.SortActorByAverageDesc());
        }

        return sortedActorsList;
    }

    /**
     * Create the list of actors sorted by the won awards
     * @param inputAction the data of the action
     * @param actorsRepo the actors database
     * @return the sorted by awards actors list
     */
    public ArrayList<Actor> getAwardsActorList(final ActionInputData inputAction,
                                               final ActorsRepo actorsRepo) {
        // copy the list of actors from the database
        ArrayList<Actor> actorsList = actorsRepo.getActorsData();
        // initialise the filtered and sorted actors list as a copy
        // of the list with all actors
        ArrayList<Actor> filterSortedList = new ArrayList<>(actorsList);
        // get the filters awards list
        List<String> awardsList = inputAction.getFilters().
                get(Constants.AWARDS_FIELD_FILTERS);

        // for each actor from the actors list
        for (Actor actor : actorsList) {
            // if the input awards list is not null
            if (awardsList.get(0) != null) {
                for (String award : awardsList) {
                    // check if the current actors has received the
                    // given award; otherwise, remove it from the filtered
                    // and sorted list
                    if (!actor.getAwards().containsKey(ActorsAwards.valueOf(award))) {
                        filterSortedList.remove(actor);
                    }
                }
            }
        }

        // sort in ascending or descending order by awards and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(filterSortedList, new Comparators.SortActorByAwardAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(filterSortedList, new Comparators.SortActorByAwardDesc());
        }
        return filterSortedList;
    }

    /**
     * Create the list of actors sorted by description and by name
     * @param inputAction the data of the action
     * @param actorsRepo the actors database
     * @return the sorted by description actors list
     */
    public ArrayList<Actor> getDescriptionActorList(final ActionInputData inputAction,
                                                    final ActorsRepo actorsRepo) {
        // copy the list of actors from the database
        ArrayList<Actor> actorsList = actorsRepo.getActorsData();
        // initialise the filtered and sorted actors list as a copy
        // of the list with all actors
        ArrayList<Actor> filteredSortedList = new ArrayList<>(actorsList);
        // get the filters words list
        List<String> wordsList = inputAction.getFilters().
                get(Constants.WORDS_FIELD_FILTERS);

        // for each actor from the sorted actors list
        for (Actor actor : actorsList) {
            // if the input words list is not null
            if (wordsList.get(0) != null) {
                // for each word in the list
                for (String word : wordsList) {
                    // check if the current actor's description
                    // contains the given word; otherwise,
                    // remove it from the filtered and sorted list
                    String regex = "[ !?.,':;-]" + word + "[ !?.,':;-]";
                    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(actor.getCareerDescription());
                    if (!matcher.find()) {
                        filteredSortedList.remove(actor);
                    }
                }
            }
        }

        // sort in ascending or descending order by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(filteredSortedList, new Comparators.SortActorByNameAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(filteredSortedList, new Comparators.SortActorByNameDesc());
        }
        return filteredSortedList;
    }

    /**
     * Apply the query for the given query type
     * @param inputAction the data of the action
     * @param actorsRepo the actors database
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @param queryType the input query type
     * @return the output message
     */
    public String applyQuery(final ActionInputData inputAction, final ActorsRepo actorsRepo,
                             final MoviesRepo moviesRepo, final SerialsRepo serialsRepo,
                             final String queryType) {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        // build an arraylist containing the shows sorted by query type,
        // which have the wanted genre & year
        ArrayList<Actor> finalSortedActors;

        switch (queryType) {
            case Constants.AVERAGE -> {
                finalSortedActors = getAverageActorList(inputAction,
                        actorsRepo, moviesRepo, serialsRepo);
            }
            case Constants.AWARDS -> {
                finalSortedActors = getAwardsActorList(inputAction, actorsRepo);
            }
            case Constants.FILTER_DESCRIPTIONS -> {
                finalSortedActors = getDescriptionActorList(inputAction, actorsRepo);
            }
            default -> {
                finalSortedActors = null;
            }
        }


        int numberListElem = inputAction.getNumber();

        // if the list is smaller than the wanted size
        if (finalSortedActors.size() < numberListElem) {
            numberListElem = finalSortedActors.size();
        }

        // print titles
        for (int i = 0; i < numberListElem; i++) {
            message.append(finalSortedActors.get(i).getName());
            if (i < numberListElem - 1) {
                message.append(", ");
            }
        }
        message.append("]");
        return message.toString();
    }
}
