package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a movie
 */
public class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of all ratings the movie was given
     */
    private List<Double> ratings;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }
    /**
     * Get the average rating of a movie
     * @return the average rating
     */
    public double getAverageRating() {
        if (ratings == null) {
            return 0;
        }

        double sumOfRatings = 0;
        for (double rating : ratings) {
            sumOfRatings += rating;
        }
        return sumOfRatings / ratings.size();
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
