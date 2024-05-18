package nasa_services.apod;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApiKey;
import errors.ServerConnectionError;
import logging.TelegramLog;
import nasa_services.apod.apod_utils.ApodImageCollection;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import utils.DateUtils;
import utils.Emojis;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

/**
 * The {@code Apod} class fetches Astronomy Picture of the Day (APOD) data from NASA's API.
 * <p>It provides methods to retrieve APOD URLs for both default and specific dates.
 */
public class Apod {

    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod" + ApiKey.API_KEY;

    /**
     * Fetches the APOD URL for the given URL.
     *
     * @param apodUrl the URL to fetch the APOD data from.
     * @return a formatted by {@link Apod#createPost(ApodImageCollection apod)} string representing APOD data
     * or {@link ServerConnectionError#FAILED_REQUEST} message if an error occurs.
     */
    public String getUrl(String apodUrl) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(apodUrl));
            ApodImageCollection apod = mapper.readValue(response.getEntity().getContent(), ApodImageCollection.class);
            return createPost(apod);
        } catch (IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return ServerConnectionError.FAILED_REQUEST;
        }
    }

    /**
     * Fetches the APOD URL for the default date.
     *
     * @return a string representing the APOD data for the default date.
     */
    public String getUrlForDefaultDate() {
        return getUrl(BASE_URL);
    }

    /**
     * Fetches the APOD URL for the specified date.
     *
     * @param inputDate the date for which the APOD data is requested.
     * @return a string representing the APOD data for the specified date.
     */
    public String getUrlForSpecifiedDate(String inputDate) {
        return getUrl(BASE_URL + "&date=" + inputDate);
    }

    /**
     * Creates a formatted string representing the APOD data.
     *
     * @param apod the APOD data to be formatted.
     * @return a formatted string representing the APOD data.
     */
    private String createPost(ApodImageCollection apod) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Emojis.PAGE).append(apod.getTitle()).append("\n\n")
                .append(Emojis.DATE).append(DateUtils.dateFormatter(apod.getDate(), "yyyy-MM-dd", "MMM d, yyyy", Locale.ENGLISH)).append("\n\n")
                .append(Emojis.ROUND_PUSHPIN).append("Description").append("\n").append(apod.getExplanation()).append("\n\n");
        if (apod.getHdurl() == null)
            stringBuilder.append(Emojis.LINK).append("YouTube Video Link\n").append(apod.getUrl());
        else
            stringBuilder.append(Emojis.LINK).append("4k Image Link\n").append(apod.getHdurl()).append("\n\n")
                    .append(Emojis.LINK).append("HD Image Link\n").append(apod.getUrl());
        return stringBuilder.toString();
    }

}
