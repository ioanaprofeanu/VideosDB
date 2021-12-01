package entertainment;

import java.util.ArrayList;

/**
 * General information about show (video)
 */
public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * The number of views
     */
    private int viewNumber;
    /**
     * The number of users who added the show to favorites
     */
    private int favoriteNumber;

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.viewNumber = 0;
        this.favoriteNumber = 0;
    }

    /**
     * Abstract classes for getting the average rating
     * <p>
     * It will be implemented in the children classes (Movie & Serial)
     */
    public abstract double getAverageRating();

    /**
     * Abstract classes for getting the duration
     * <p>
     * It will be implemented in the children classes (Movie & Serial)
     */
    public abstract int getDuration();

    /**
     * increase the number of views for the show
     */
    public void increaseViewNumber() {
        viewNumber++;
    }

    /**
     * increase the number of favorites for the show
     */
    public void increaseFavoriteNumber() {
        favoriteNumber++;
    }

    public final int getViewNumber() {
        return viewNumber;
    }

    public final int getFavoriteNumber() {
        return favoriteNumber;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
}
