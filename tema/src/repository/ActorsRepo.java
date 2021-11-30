package repository;

import actor.Actor;
import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;

public final class ActorsRepo {
    /**
     * List of actors
     */
    private final ArrayList<Actor> actorsData;

    public ActorsRepo(Input input) {
        actorsData = new ArrayList<>();
        for (ActorInputData inputActor : input.getActors()) {
            Actor newActor = new Actor(inputActor.getName(),
                    inputActor.getCareerDescription(),
                    inputActor.getFilmography(), inputActor.getAwards());
            actorsData.add(newActor);
        }
    }

    /**
     * Sets the value of the rating of each actor
     * @param moviesRepo
     * @param serialsRepo
     */
    public void setActorsRating (MoviesRepo moviesRepo, SerialsRepo serialsRepo) {
        for (Actor actor : actorsData) {
            actor.setAverageActorRating(moviesRepo, serialsRepo);
        }
    }
    /**
     * Get a list of all rated actors
     * @return
     */
    public ArrayList<Actor> getRatedActors() {
        ArrayList<Actor> ratedActorsList = new ArrayList<>();
        for (Actor actor : actorsData) {
            if (actor.getRating() > 0) {
                ratedActorsList.add(actor);
            }
        }
        return ratedActorsList;
    }

    public ArrayList<Actor> getActorsData() {
        return actorsData;
    }
}
