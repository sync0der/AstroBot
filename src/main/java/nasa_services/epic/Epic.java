package nasa_services.epic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApiKey;
import logging.TelegramLog;
import lombok.Data;
import nasa_services.epic.epic_utils.EpicImageCollection;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import utils.DateUtils;
import utils.Emojis;

import java.io.IOException;
import java.util.*;

/**
 * The {@code Epic} class provides methods to interact with the NASA EPIC API
 * and retrieve EPIC image data.
 */
@Data
public class Epic {

    private static final String BASE_URL = "https://api.nasa.gov/EPIC/";
    /**
     * Set containing all available dates for EPIC images.
     */
    private Set<String> listOfAllAvailableDates = new HashSet<>();

    /**
     * Fetches a list of EPIC images for a specified date.
     *
     * @param inputDate the specified date to fetch EPIC images (format: yyyy-MM-dd).
     * @return a list of EPIC image collections.
     */
    public List<EpicImageCollection> getEpicImagesForDate(String inputDate) {
        return fetchEpicImages(BASE_URL + "api/natural/date/" + inputDate + ApiKey.API_KEY);
    }

    /**
     * Fetches a list of EPIC images for the default date.
     *
     * @return a list of EPIC image collections.
     */
    public List<EpicImageCollection> getEpicImagesForDefaultDate() {
        return fetchEpicImages(BASE_URL + "api/natural" + ApiKey.API_KEY);
    }

    /**
     * Fetches EPIC images from the specified URL.
     *
     * @param url the URL to fetch EPIC images from.
     * @return a list of EPIC image collections.
     */
    public List<EpicImageCollection> fetchEpicImages(String url) {
        List<EpicImageCollection> list = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
            List<EpicImageCollection> imageDataList = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            list.addAll(imageDataList);
            return list;
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Retrieves all available dates for EPIC images from NASA API.
     */
    public void retrieveAllAvailableDates() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(BASE_URL + "api/natural/all" + ApiKey.API_KEY));
            Set<EpicImageCollection> listOfDates = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            for (EpicImageCollection listOfDate : listOfDates)
                this.listOfAllAvailableDates.add(listOfDate.getDate());
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Checks if the specified date exists in {@link Epic#listOfAllAvailableDates}.
     *
     * @param inputDate the date to check (format: yyyy-MM-dd).
     * @return {@code true} if the date exists, {@code false} otherwise
     */
    public boolean checkForExistenceOfDate(String inputDate) {
        return listOfAllAvailableDates.contains(inputDate);
    }

    /**
     * Constructs the URL for a specified date.
     *
     * @param inputDate  the date of the image (format: yyyy-MM-dd).
     * @param inputImage the image name.
     * @return the URL for the specific EPIC image.
     */
    public String getUrl(String inputDate, String inputImage) {
        return BASE_URL + "archive/natural/" + inputDate + "/png/" + inputImage + ".png" + ApiKey.API_KEY;
    }

    /**
     * Creates a formatted post with EPIC image information.
     *
     * @param epic the EPIC image collection.
     * @return a formatted message with EPIC image information.
     */
    public String createPost(EpicImageCollection epic) {
        return Emojis.EARTH + epic.getCaption() + Emojis.SATELLITE + "\n\n" +
                Emojis.DATE + DateUtils.dateTimeFormatter(epic.getDate(), "yyyy-MM-dd HH:mm:ss", "MMM d, yyyy HH:mm:ss", Locale.ENGLISH) + "\n\n" +
                Emojis.LINK + "HD Image Link\n";
    }

    /**
     * Gets the most recent date from the EPIC image collection.
     *
     * @param epic the EPIC image collection.
     * @return the most recent date.
     */
    public String getTheMostRecentDate(EpicImageCollection epic) {
        return epic.getDate();
    }

    /**
     * Checks if the specified date is after the EPIC launch date (2015-06-13).
     *
     * @param inputDate the date to compare (format: yyyy-MM-dd).
     * @return {@code true} if the date is after the launch date, {@code false} otherwise.
     */
    public boolean isDateAfterEpicLaunchDate(String inputDate) {
        return DateUtils.compareDates(inputDate, "2015-06-13");
    }
}

