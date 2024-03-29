package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Get the average rating of a season
     * <p>
     * Iterate through the ratings list and calculate the average
     * @return
     */
    public double getAverageSeasonRating() {
        // if the season isn't rated yet
        if (this.ratings.size() == 0) {
            return 0;
        }

        double sumOfGrades = 0;
        int noGrades = 0;
        for (Double rating : this.ratings) {
            sumOfGrades += rating;
            noGrades++;
        }
        return sumOfGrades / noGrades;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

