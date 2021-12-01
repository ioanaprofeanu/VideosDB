package action.Query;

import common.Constants;
import fileio.ActionInputData;
import repository.UsersRepo;
import user.User;
import utils.Comparators;

import java.util.ArrayList;
import java.util.Collections;

public class QueryUser {
    /**
     * Apply the query
     * @param inputAction the data of the action
     * @param usersRepo the users database
     * @return the output message
     */
    public String applyQuery(final ActionInputData inputAction, final UsersRepo usersRepo) {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        // get a list of all reviewers user
        ArrayList<User> reviewersList = usersRepo.getReviewersUsers();
        int numberListElem = inputAction.getNumber();

        // sort in ascending or descending order by rating and by name
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(reviewersList, new Comparators.SortUserByRatingAsc());
        } else if (inputAction.getSortType().equals(Constants.DESC)) {
            Collections.sort(reviewersList, new Comparators.SortUserByRatingDesc());
        }

        // if the list is smaller than the wanted size
        if (reviewersList.size() < numberListElem) {
            numberListElem = reviewersList.size();
        }

        // print username
        for (int i = 0; i < numberListElem; i++) {
            message.append(reviewersList.get(i).getUsername());
            if (i < numberListElem - 1) {
                message.append(", ");
            }
        }
        message.append("]");
        return message.toString();
    }
}

