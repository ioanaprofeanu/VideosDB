package repository;

import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRepo {
    /**
     * List of users
     */
    private ArrayList<User> usersData;

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

    public void setUsersData(ArrayList<User> usersData) {
        this.usersData = usersData;
    }
}
