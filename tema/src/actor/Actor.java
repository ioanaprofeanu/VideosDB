package actor;

import entertainment.Movie;
import entertainment.Serial;
import fileio.ActorInputData;
import repository.MoviesRepo;
import repository.SerialsRepo;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 */
public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * average rating of all shows an actor played in
     */
    private double rating;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0;
    }

    /**
     * Set the average rating of the shows
     * an actor plays in
     * @param moviesRepo the movies database
     * @param serialsRepo the serials database
     * @return the average
     */
    public void setAverageActorRating(MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        double sumOfRatings = 0;
        int noRatings = 0;
        for (String title : this.filmography) {
            for (Movie movie : moviesRepo.getMoviesData()) {
                if (movie.getAverageRating() > 0) {
                    if (movie.getTitle().equals(title)) {
                        noRatings++;
                        sumOfRatings += movie.getAverageRating();
                    }
                }
            }
            for (Serial serial : serialsRepo.getSerialsData()) {
                if (serial.getAverageRating() > 0) {
                    if (serial.getTitle().equals(title)) {
                        noRatings++;
                        sumOfRatings += serial.getAverageRating();;
                    }
                }
            }
        }
        this.rating = sumOfRatings / noRatings;
    }

    /**
     * Get the number of awards an actor has won
     * @return
     */
    public int getNumberOfAwards() {
        int noAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            noAwards += entry.getValue();
        }
        return noAwards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
