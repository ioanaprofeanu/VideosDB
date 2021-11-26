package repository;

import entertainment.Serial;
import fileio.Input;
import fileio.SerialInputData;

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
            Serial newSerial = new Serial(inputSerial.getTitle(), inputSerial.getCast(), inputSerial.getGenres(), inputSerial.getNumberSeason(), inputSerial.getSeasons(), inputSerial.getYear());
            serialsData.add(newSerial);
        }
    }

    public List<Serial> getSerialsData() {
        return serialsData;
    }

    public void setSerialsData(List<Serial> serialsData) {
        this.serialsData = serialsData;
    }
}
