package repository;

import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.Input;
import fileio.SerialInputData;
import user.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SerialsRepo {
    /**
     * List of serials aka tv shows
     */
    private List<Serial> serialsData;

    public SerialsRepo(Input input) {
        serialsData = new ArrayList<>();
        for (SerialInputData inputSerial : input.getSerials()) {
            Serial newSerial = new Serial(inputSerial.getTitle(), inputSerial.getCast(),
                    inputSerial.getGenres(), inputSerial.getNumberSeason(),
                    inputSerial.getSeasons(), inputSerial.getYear());
            serialsData.add(newSerial);
        }
    }

    /**
     * Initialises the view number of all serials, according
     * to each user's view count
     * @param usersRepo
     */
    public void initialiseViewNumber(UsersRepo usersRepo) {
        for (Serial serial : serialsData) {
            for (User user : usersRepo.getUsersData()) {
                if (user.getHistory().containsKey(serial.getTitle())) {
                    for (int i = 0; i < user.getHistory().get(serial.getTitle()); i++) {
                        serial.increaseViewNumber();
                    }
                }
            }
        }
    }

    /**
     * Initialises the favorite number of all serials, according
     * to each user's view count
     * @param usersRepo
     */
    public void initialiseFavoriteNumber(UsersRepo usersRepo) {
        for (Serial serial : serialsData) {
            for (User user : usersRepo.getUsersData()) {
                if (user.getFavoriteMovies().contains(serial.getTitle())) {
                    serial.increaseFavoriteNumber();
                }
            }
        }
    }

    public List<Serial> getSerialsData() {
        return serialsData;
    }

    public void setSerialsData(List<Serial> serialsData) {
        this.serialsData = serialsData;
    }

    /**
     * Find the serial with a given title
     * @param serialTitle the searched title of the serial
     * @return the found serial
     */
    public Serial getSerialByTitle(String serialTitle) {
        for (Serial serial : serialsData) {
            if (equals(serial.getTitle().equals(serialTitle))) {
                return serial;
            }
        }
        return null;
    }

    /**
     * Returns a list of show objects
     * containing all the rated serials
     * @return a show list of rated serials
     */
    public ArrayList<Show> getRatedSerials() {
        ArrayList<Show> ratedSerialList = new ArrayList<Show>();
        for (Serial serial : serialsData) {
            if (serial.getAverageRating() > 0) {
                ratedSerialList.add(serial);
            }
        }
        return ratedSerialList;
    }

    /**
     * Get a show list of all serials who have been
     * added to favorite by the users
     * @return the favorite list of shows
     */
    public ArrayList<Show> getFavoriteSerials() {
        ArrayList<Show> favoriteSerialList = new ArrayList<Show>();
        for (Serial serial : serialsData) {
            if (serial.getFavoriteNumber() != 0) {
                favoriteSerialList.add(serial);
            }
        }
        return favoriteSerialList;
    }

    /**
     * Create a list of show objects from the list of serial objects
     * @return the show objects
     */
    public ArrayList<Show> getSerialShowData() {
        ArrayList<Show> showData = new ArrayList<Show>();
        for (Serial serial : serialsData) {
            showData.add(serial);
        }
        return showData;
    }

    /**
     * Get a show list of all serials were viewed by the users
     * @return the viewed list of shows
     */
    public ArrayList<Show> getViewedSerials() {
        ArrayList<Show> viewedSerialList = new ArrayList<Show>();
        for (Serial serial : serialsData) {
            if (serial.getViewNumber() != 0) {
                viewedSerialList.add(serial);
            }
        }
        return viewedSerialList;
    }
}
