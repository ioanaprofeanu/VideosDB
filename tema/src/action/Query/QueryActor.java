package action.Query;

import actor.Actor;
import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.ActorsRepo;
import repository.MoviesRepo;
import repository.SerialsRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueryActor {
    /**
     * Sort actors list by average rating in ascending order
     */
    static class SortActorByAverageAsc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            if (o2.getRating() == o1.getRating()) {
                return o1.getName().compareTo(o2.getName());
            }
            if (o2.getRating() < o1.getRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by average rating in descending order
     */
    static class SortActorByAverageDesc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            if (o1.getRating() == o2.getRating()) {
                return o2.getName().compareTo(o1.getName());
            }
            if (o1.getRating() < o2.getRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by the number of awards in ascending order
     */
    static class SortActorByAwardAsc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            if (o2.getNumberOfAwards() == o1.getNumberOfAwards()) {
                return o1.getName().compareTo(o2.getName());
            }
            if (o2.getNumberOfAwards() < o1.getNumberOfAwards()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by the number of awards in descending order
     */
    static class SortActorByAwardDesc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            if (o1.getNumberOfAwards() == o2.getNumberOfAwards()) {
                return o2.getName().compareTo(o1.getName());
            }
            if (o1.getNumberOfAwards() < o2.getNumberOfAwards()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by name in ascending order
     */
    static class SortActorByNameAsc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    /**
     * Sort actors list by name in descending order
     */
    static class SortActorByNameDesc implements Comparator<Actor> {
        @Override
        public int compare(Actor o1, Actor o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }

    /**
     * Returns the list of actors filtered and sorted
     * @param inputData
     * @param sortedActorList
     * @return
     */
    public ArrayList<Actor> filteredSortedActors(ActionInputData inputData, ArrayList<Actor> sortedActorList) {
        ArrayList<Actor> filteredSortedList = new ArrayList<>(sortedActorList);
        List<String> wordsList = inputData.getFilters().get(Constants.WORDS_FIELD_FILTERS);
        List<String> awardsList = inputData.getFilters().get(Constants.AWARDS_FIELD_FILTERS);

        // for each actor from the sorted actors list
        for (Actor actor : sortedActorList) {
            // if the input words list is not null
            if (wordsList != null) {
                // for each word in the list
                for (String word : wordsList) {
                    // check if the current actor's description contains the given word;
                    // otherwise, remove it from the filtered and sorted list
                    if (!actor.getCareerDescription().contains(word)) {
                        filteredSortedList.remove(actor);
                    }
                }
            }
            if (awardsList != null) {
                for (String award : awardsList) {
                    // check if the current actors has received the given award;
                    // otherwise, remove it from the filtered and sorted list
                    if (!actor.getAwards().containsKey(award)) {
                        filteredSortedList.remove(actor);
                    }
                }
            }
        }
        return filteredSortedList;
    }

    public ArrayList<Actor> getAverageActorList(ActionInputData inputAction, ActorsRepo actorsRepo,
                                               MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        actorsRepo.setActorsRating(moviesRepo, serialsRepo);
        ArrayList<Actor> sortedActorsList = actorsRepo.getRatedActors();

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(sortedActorsList, new QueryActor.SortActorByAverageAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(sortedActorsList, new QueryActor.SortActorByAverageDesc());
        }
        return sortedActorsList;
    }

    public ArrayList<Actor> getAwardsActorList(ActionInputData inputAction, ActorsRepo actorsRepo,
                                                MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Actor> sortedActorsList = actorsRepo.getActorsData();

        ArrayList<Actor> filteredSortedList = new ArrayList<>(sortedActorsList);
        List<String> awardsList = inputAction.getFilters().get(Constants.AWARDS_FIELD_FILTERS);

        // for each actor from the sorted actors list
        for (Actor actor : sortedActorsList) {
            // if the input words list is not null
            // if the input words list is not null
            if (awardsList != null) {
                for (String award : awardsList) {
                    // check if the current actors has received the given award;
                    // otherwise, remove it from the filtered and sorted list
                    if (!actor.getAwards().containsKey(award)) {
                        filteredSortedList.remove(actor);
                    }
                }
            }
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(filteredSortedList, new QueryActor.SortActorByAwardAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(filteredSortedList, new QueryActor.SortActorByAwardDesc());
        }
        return filteredSortedList;
    }

    public ArrayList<Actor> getDescriptionActorList(ActionInputData inputAction, ActorsRepo actorsRepo,
                                               MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        ArrayList<Actor> sortedActorsList = actorsRepo.getActorsData();

        ArrayList<Actor> filteredSortedList = new ArrayList<>(sortedActorsList);
        List<String> wordsList = inputAction.getFilters().get(Constants.WORDS_FIELD_FILTERS);

        // for each actor from the sorted actors list
        for (Actor actor : sortedActorsList) {
            // if the input words list is not null
            if (wordsList != null) {
                // for each word in the list
                for (String word : wordsList) {
                    // check if the current actor's description contains the given word;
                    // otherwise, remove it from the filtered and sorted list
                    if (!actor.getCareerDescription().toLowerCase().contains(word.toLowerCase())) {
                        filteredSortedList.remove(actor);
                    }
                }
            }
        }

        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(filteredSortedList, new QueryActor.SortActorByAwardAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(filteredSortedList, new QueryActor.SortActorByAwardDesc());
        }
        return filteredSortedList;
    }

    public String applyQuery(ActionInputData inputAction, ActorsRepo actorsRepo,
                             MoviesRepo moviesRepo, SerialsRepo serialsRepo,
                             String queryType) {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        // build an arraylist containing the shows sorted by query type,
        // which have the wanted genre & year
        ArrayList<Actor> finalSortedActors = new ArrayList<Actor>();
        switch (queryType) {
            case Constants.AVERAGE -> {
                finalSortedActors = getAverageActorList(inputAction, actorsRepo, moviesRepo, serialsRepo);
            }
            case Constants.AWARDS -> {
                finalSortedActors = getAwardsActorList(inputAction, actorsRepo, moviesRepo, serialsRepo);
            }
            case Constants.FILTER_DESCRIPTIONS  -> {
                finalSortedActors = getDescriptionActorList(inputAction, actorsRepo, moviesRepo, serialsRepo);
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
