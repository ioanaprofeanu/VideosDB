package repository;

import actor.Actor;
import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;

public class ActorsRepo {
    /**
     * List of actors
     */
    private ArrayList<Actor> actorsData;

    public ActorsRepo(Input input) {
        actorsData = new ArrayList<>();
        for (ActorInputData inputActor : input.getActors()) {
            Actor newActor = new Actor(inputActor.getName(),
                    inputActor.getCareerDescription(),
                    inputActor.getFilmography(), inputActor.getAwards());
            actorsData.add(newActor);
        }
    }

    public ArrayList<Actor> getActorsData() {
        return actorsData;
    }

    public void setActorsData(ArrayList<Actor> actorsData) {
        this.actorsData = actorsData;
    }
}
