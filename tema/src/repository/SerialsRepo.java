package repository;

import entertainment.Serial;
import entertainment.Show;
import fileio.Input;
import fileio.SerialInputData;
import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which contains the serials database
 */
public final class SerialsRepo {
    /**
     * List of serials aka tv shows
     */
    private final ArrayList<Serial> serialsData;

    public SerialsRepo(final Input input) {
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
     * @param usersRepo the users database
     */
    public void initialiseViewNumber(final UsersRepo usersRepo) {
        for (Serial serial : serialsData) {
            for (User user : usersRepo.getUsersData()) {
                // if the serial was watched by the user
                if (user.getHistory().containsKey(serial.getTitle())) {
                    // increase the view number for as many times as the view
                    // number is in the history hashmap
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
     * @param usersRepo the users database
     */
    public void initialiseFavoriteNumber(final UsersRepo usersRepo) {
        for (Serial serial : serialsData) {
            for (User user : usersRepo.getUsersData()) {
                // if the serial is within the user's favorite list
                if (user.getFavoriteMovies().contains(serial.getTitle())) {
                    serial.increaseFavoriteNumber();
                }
            }
        }
    }

    /**
     * Find the serial with a given title
     * @param serialTitle the searched title of the serial
     * @return the found serial
     */
    public Serial getSerialByTitle(final String serialTitle) {
        for (Serial serial : serialsData) {
            if (serial.getTitle().equals(serialTitle)) {
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
        ArrayList<Show> ratedSerialList = new ArrayList<>();
        for (Serial serial : serialsData) {
            // if the serial was rated
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
        ArrayList<Show> favoriteSerialList = new ArrayList<>();
        for (Serial serial : serialsData) {
            // if the serial was added to favorite
            if (serial.getFavoriteNumber() > 0) {
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
        return new ArrayList<>(serialsData);
    }

    /**
     * Get a show list of all serials were viewed by the users
     * @return the viewed list of shows
     */
    public ArrayList<Show> getViewedSerials() {
        ArrayList<Show> viewedSerialList = new ArrayList<>();
        for (Serial serial : serialsData) {
            // if the serial was viewed
            if (serial.getViewNumber() > 0) {
                viewedSerialList.add(serial);
            }
        }
        return viewedSerialList;
    }

    public List<Serial> getSerialsData() {
        return serialsData;
    }
}
