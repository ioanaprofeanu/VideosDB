package user;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about a user
 */
public class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Rated movies
     */
    private final ArrayList<String> ratedMovies;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratedMovies = new ArrayList<>();
    }

    /**
     * Check if a show was viewed by a user
     * @param title the title of the show
     * @return ture if the show has already been viewed,
     * false otherwise
     */
    public boolean viewedShow(String title) {
        for (Map.Entry<String, Integer> entry : history.entrySet()) {
            if (entry.getKey().equals(title)) {
                if (entry.getValue() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * add or increase the views of a show
     * if the show doesn't exist, add entry
     * @param title the title of the show
     */
    public void addHistoryViews(String title) {
        if (this.history.containsKey(title)) {
            this.history.put(title, this.history.get(title) + 1);
            return;
        }
        this.history.put(title, 1);
    }

    /**
     * @param title the title of the show
     * @return the number of times a show was
     * viewed by the user
     */
    public int numberOfHistoryViews(String title) {
        return this.history.get(title);
    }

    /**
     * @return the number of shows rated my a user
     */
    public int numberOfRatedShows(){
        return ratedMovies.size();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

}
