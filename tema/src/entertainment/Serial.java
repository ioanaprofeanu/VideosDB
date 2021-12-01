package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about a tv show
 */
public class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    /**
     * A list of lists; the key represents the username of a user
     * and the value is a list of seasons the user rated
     */
    private final Map<String, ArrayList<Integer>> ratedSeasonByUsers;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.ratedSeasonByUsers = new HashMap<>();
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Add new rating to a season's ratings list
     * @param seasonNumber the number of the season
     * @param grade the rating
     */
    public void addSeasonRating(int seasonNumber, double grade) {
        for (Season season : seasons) {
            if (seasonNumber == season.getCurrentSeason()){
                season.getRatings().add(grade);
                return;
            }
        }
    }

    /**
     * Get the average rating of a serial
     * @return the average rating of a serial
     */
    public double getAverageRating() {
        double sumOfGrades = 0;
        for (Season season : seasons) {
            sumOfGrades += season.getAverageSeasonRating();
        }
        return sumOfGrades / numberOfSeasons;
    }

    /**
     * Get the total duration of a serial by
     * calculating the sum of the seasons' durations
     * @return the total duration of a serial
     */
    public int getDuration() {
        int sumOfDurations = 0;
        for (Season season : seasons) {
            sumOfDurations += season.getDuration();
        }
        return sumOfDurations;
    }

    public Map<String, ArrayList<Integer>> getRatedSeasonByUsers() {
        return ratedSeasonByUsers;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
