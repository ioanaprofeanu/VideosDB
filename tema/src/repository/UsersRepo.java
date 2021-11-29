package repository;

import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public final class UsersRepo {
    /**
     * List of users
     */
    private final ArrayList<User> usersData;

    public UsersRepo(Input input) {
        usersData = new ArrayList<>();
        for (UserInputData inputUser : input.getUsers()) {
            User newUser = new User(inputUser.getUsername(),
                    inputUser.getSubscriptionType(),
                    inputUser.getHistory(), inputUser.getFavoriteMovies());
            usersData.add(newUser);
        }
    }

    public ArrayList<User> getUsersData() {
        return usersData;
    }

    /**
     * Find the user with a given name
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
    public ArrayList<User> getReviewersUsers () {
        ArrayList<User> reviewersList = new ArrayList<User>();
        for (User user : usersData) {
            if (user.getRatedMovies().size() > 0) {
                reviewersList.add(user);
            }
        }
        return reviewersList;
    }
}
