package repository;

import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;

/**
 * Class which contains the users database
 */
public final class UsersRepo {
    /**
     * List of users
     */
    private final ArrayList<User> usersData;

    public UsersRepo(final Input input) {
        usersData = new ArrayList<>();
        for (UserInputData inputUser : input.getUsers()) {
            User newUser = new User(inputUser.getUsername(),
                    inputUser.getSubscriptionType(),
                    inputUser.getHistory(), inputUser.getFavoriteMovies());
            usersData.add(newUser);
        }
    }

    /**
     * Find the user with a given username
     * @param username the searched name of the user
     * @return the found user
     */
    public User getUserByUsername(final String username) {
        for (User user : usersData) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets all users who have given a review
     * @return list of all reviewers
     */
    public ArrayList<User> getReviewersUsers() {
        ArrayList<User> reviewersList = new ArrayList<>();
        for (User user : usersData) {
            // if the list of rated movies is not empty, it means that
            // the user reviewed at least one show
            if (user.getRatedMovies().size() > 0) {
                reviewersList.add(user);
            }
        }
        return reviewersList;
    }

    public ArrayList<User> getUsersData() {
        return usersData;
    }
}
