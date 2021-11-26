package entertainment;

import java.util.ArrayList;

/**
 * General information about show (video)
 */
public abstract class Show {
    /**
     * Show's title
     */
    private String title;
    /**
     * The year the show was released
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * The number of views
     */
    private int viewNumber;
    /**
     * The number of users who liked the show
     */
    private int favoriteNumber;

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public abstract double getAverageRating();

    public void addViewNumber() {
        viewNumber++;
    }

    public void addFavoriteNumber() {
        favoriteNumber++;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
}