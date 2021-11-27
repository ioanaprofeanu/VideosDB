package action.Query;

import common.Constants;
import fileio.ActionInputData;
import repository.UsersRepo;
import user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class QueryUser {
    static class SortUserByRatingAsc implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            if (o2.getRatedMovies().size() == o1.getRatedMovies().size()) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
            if (o2.getRatedMovies().size() < o1.getRatedMovies().size()) {
                return 1;
            }
            return -1;
        }
    }

    static class SortUserByRatingDesc implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            if (o1.getRatedMovies().size() == o2.getRatedMovies().size()) {
                return o2.getUsername().compareTo(o1.getUsername());
            }
            if (o1.getRatedMovies().size() < o2.getRatedMovies().size()) {
                return 1;
            }
            return -1;
        }
    }

    public String applyQuery(ActionInputData inputAction,
                            UsersRepo usersRepo) {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        ArrayList<User> reviewersList = usersRepo.getReviewersUsers();
        int numberListElem = inputAction.getNumber();
        // if the order is ascending
        if (inputAction.getSortType().equals(Constants.ASC)) {
            Collections.sort(reviewersList, new SortUserByRatingAsc());
        } else if (inputAction.getActionType().equals(Constants.DESC)) {
            Collections.sort(reviewersList, new SortUserByRatingDesc());
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

