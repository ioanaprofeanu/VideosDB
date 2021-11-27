package entertainment;

import java.util.ArrayList;

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

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
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
        this.seasons.get(seasonNumber).getRatings().add(grade);
    }

    /**
     * Get the average rating of a season within a serial
     * @param seasonNumber the order number of the season
     * @return the average rating of a season
     */
    public double getAverageSeasonRating(int seasonNumber) {
        double sumOfGrades = 0;
        for (double grade : this.seasons.get(seasonNumber).getRatings()) {
            sumOfGrades += grade;
        }
        return sumOfGrades / this.seasons.get(seasonNumber).getRatings().size();
    }

    /**
     * Get the average rating of a serial
     * @return the average rating of a serial
     */
    public double getAverageRating() {
        double sumOfGrades = 0;
        for (int i = 0; i < this.seasons.size(); i++) {
            sumOfGrades += getAverageSeasonRating(i);
        }
        return sumOfGrades / numberOfSeasons;
    }

    /**
     * Get the duration of a serial
     * @return the total duration of a serial
     */
    public int getDuration() {
        int sumOfDurations = 0;
        for (Season season : seasons) {
            sumOfDurations += season.getDuration();
        }
        return sumOfDurations;
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
