package nasa_services.nasa_image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import errors.UserInputError;
import logging.TelegramLog;
import lombok.Data;
import nasa_services.nasa_image.image_utils.Items;
import nasa_services.nasa_image.image_utils.NasaImageCollection;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import utils.DateUtils;
import utils.Emojis;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * The {@code NasaImage} class provides methods for retrieving NASA images based on search terms.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NasaImage {
    private static final String BASE_URL = "https://images-api.nasa.gov/search?q=";

    /**
     * Fetches a NASA image based on the provided search term.
     *
     * @param searchTerm the term to search for NASA images.
     * @return the formatted message containing information about the image.
     */
    public String getNasaImage(String searchTerm) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(generateUrl(searchTerm)));
            NasaImageCollection imageCollection = mapper.readValue(response.getEntity().getContent(), NasaImageCollection.class);
            List<Items> items = imageCollection.getCollectionData().getItems();
            int index = new Random().nextInt(items.size());
            return createPost(items.get(index));
        } catch (Exception e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return UserInputError.INVALID_SEARCH_TERM;
        }
    }

    /**
     * Generates the URL for fetching NASA images based on the search term.
     *
     * @param searchTerm the term to search for NASA images.
     * @return the generated URL for fetching images.
     */
    public String generateUrl(String searchTerm) {
        String[] searchTerms = searchTerm.split(" ");
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL);
        for (int i = 1; i < searchTerms.length; i++) {
            url.append(searchTerms[i]);
            if ((i + 1) == searchTerms.length)
                return url.append("&media_type=image").toString();
            url.append("%20");
        }
        return url.append("&media_type=image").toString();
    }

    /**
     * Creates a formatted post containing information about a NASA image.
     *
     * @param items the NASA image object containing information.
     * @return the formatted post.
     */
    public String createPost(Items items) {
        return Emojis.PAGE + items.getData().getFirst().getTitle() + "\n\n" +
                Emojis.DATE + " Launch Date: " + DateUtils.dateTimeFormatter(items.getData().getFirst().getDate_created(), DateTimeFormatter.ISO_DATE_TIME.toString(), "MMMM d, yyyy HH:mm:ss", Locale.ENGLISH) + "\n\n" +
                Emojis.PICTURE + " HD Image Link\n" + items.getLinks().getFirst().getHref() + "\n\n" +
                Emojis.NOTE + " Description\n" + filterText(items.getData().getFirst().getDescription()) + "\n\n";
    }

    /**
     * Filters the description text of a NASA image to remove unnecessary information.
     *
     * @param text the original description text of the NASA image.
     * @return the filtered description text.
     */
    public String filterText(String text) {
        int index = text.toLowerCase().indexOf("image credit:");
//        index = (text.lastIndexOf("Image") == index - 1) ? index - 1 : index;
        if (index != -1)
            return text.substring(0, index);
        return text;
    }

}
