package nasa_services.mars_rover;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApiKey;
import errors.ServerConnectionError;
import logging.TelegramLog;
import lombok.Data;
import nasa_services.mars_rover.rover_utils.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import utils.DateUtils;
import utils.Emojis;

import java.io.IOException;
import java.util.*;

/**
 * The {@code MarsRover} class provides methods for retrieving Mars rover images
 * and rover information from NASA's API.
 */
@Data
public class MarsRover {

    public static final String MARS_CURIOSITY = "curiosity";
    public static final String MARS_PERSEVERANCE = "perseverance";
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/";
    private static final List<String> CURIOSITY_DEFAULT_CAMERAS = Arrays.asList("FHAZ", "RHAZ", "NAVCAM");
    private static final List<String> PERSEVERANCE_DEFAULT_CAMERAS = Arrays.asList("REAR_HAZCAM_RIGHT", "REAR_HAZCAM_LEFT", "FRONT_HAZCAM_RIGHT_A", "FRONT_HAZCAM_LEFT_A", "NAVCAM_RIGHT", "NAVCAM_LEFT", "SUPERCAM_RMI");

    /**
     * Fetches Mars rover images for a specific date or the latest available date.
     *
     * @param roverName the name of the Mars rover ({@link MarsRover#MARS_CURIOSITY} or {@link MarsRover#MARS_PERSEVERANCE}).
     * @param inputDate the specified date in the format "yyyy-MM-dd" for fetching images.
     *                  If null, fetches the latest images.
     * @return the formatted message containing information about the rover image.
     */
    public String getMarsRoverImage(String roverName, String inputDate) {
        boolean isDateNull = inputDate == null;
        String roverUrl = BASE_URL + roverName;
        if (!isDateNull && !inputDate.isEmpty())
            roverUrl += "/photos" + "?earth_date=" + inputDate + ApiKey.API_KEY.replace('?', '&');
        else
            roverUrl += "/latest_photos" + ApiKey.API_KEY;
        try {
            List<MarsPhotos> photos = new ArrayList<>();
            if ((roverName.equals(MARS_CURIOSITY) && !isDateNull) || (roverName.equals(MARS_PERSEVERANCE) && !isDateNull))
                photos.addAll(getMarsPhotosList(roverUrl, MarsImageCollectionWithSpecifiedDate.class));
            else if (roverName.equals(MARS_CURIOSITY) || roverName.equals(MARS_PERSEVERANCE))
                photos.addAll(getMarsPhotosList(roverUrl, MarsImageCollectionWithDefaultDate.class));
            return createPost(photos.get(findSuitableImageSelection(photos, roverName)));
        } catch (Exception e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return ServerConnectionError.FAILED_REQUEST;
        }
    }

    /**
     * Fetches a list of Mars photos based on the provided URL and data class.
     *
     * @param url   the url to fetch the data from.
     * @param clazz the class representing the data structure.
     * @param <T>   any of the {@link MarsImageCollectionWithSpecifiedDate} and {@link MarsImageCollectionWithDefaultDate} classes
     * @return a list of Mars photos.
     * @throws IllegalArgumentException if different classes provided for {@code clazz}
     *                                  rather than {@link MarsImageCollectionWithSpecifiedDate} and {@link MarsImageCollectionWithDefaultDate} classes.
     */
    public <T> List<MarsPhotos> getMarsPhotosList(String url, Class<T> clazz) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
            T imageCollection = mapper.readValue(response.getEntity().getContent(), clazz);
            if (imageCollection instanceof MarsImageCollectionWithDefaultDate)
                return ((MarsImageCollectionWithDefaultDate) imageCollection).getPhotos();
            if (imageCollection instanceof MarsImageCollectionWithSpecifiedDate)
                return ((MarsImageCollectionWithSpecifiedDate) imageCollection).getPhotos();
            throw new IllegalArgumentException("Unexpected type: " + clazz.getName());
        } catch (IllegalArgumentException | IOException e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Finds a suitable image based on the rover's cameras
     * ({@link MarsRover#CURIOSITY_DEFAULT_CAMERAS} and {@link MarsRover#PERSEVERANCE_DEFAULT_CAMERAS}).
     *
     * @param photos    the list of Mars rover photos.
     * @param roverName the name of the Mars rover ({@link MarsRover#MARS_CURIOSITY} or {@link MarsRover#MARS_PERSEVERANCE}).
     * @return the index of the selected image.
     */
    public int findSuitableImageSelection(List<MarsPhotos> photos, String roverName) {
        List<String> suitableCameras = roverName.equals(MARS_CURIOSITY) ? CURIOSITY_DEFAULT_CAMERAS : PERSEVERANCE_DEFAULT_CAMERAS;
        for (int i = 0; i < photos.size(); i++) {
            int index = new Random().nextInt(photos.size());
            String cameraName = photos.get(index).getCamera().getName();
            if (suitableCameras.contains(cameraName))
                return index;
        }
        return new Random().nextInt(photos.size());
    }

    /**
     * Creates a formatted post containing information about the Mars rover.
     *
     * @param rover the Mars rover object containing information.
     * @return the formatted post.
     */
    public String createRoverInfoPost(Rover rover) {
        List<Cameras> cameras = rover.getCameras();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Emojis.LABEL).append(" ").append(rover.getName()).append(" Rover\n")
                .append(Emojis.ROCKET).append(" Launch date: ").append(DateUtils.dateFormatter(rover.getLaunchDate(), "yyyy-MM-dd", "MMM d, yyyy", Locale.ENGLISH)).append("\n")
                .append(Emojis.DATE).append(" Landing date: ").append(DateUtils.dateFormatter(rover.getLandingDate(), "yyyy-MM-dd", "MMM d, yyyy", Locale.ENGLISH)).append("\n")
                .append(Emojis.STAR).append(" Status: ").append(rover.getStatus()).append("\n")
                .append(Emojis.CAMERA).append(" Total photos: ").append(rover.getTotalPhotos()).append("\n\n")
                .append(Emojis.MAGNIFIER).append(" Cameras:\n");
        for (Cameras camera : cameras)
            stringBuilder.append("• ").append(camera.getName()).append(": ").append(camera.getFullName()).append("\n");
        stringBuilder.append("\n").append(Emojis.MOVIE_CAMERA).append("Find out more with the video link below\n");
        stringBuilder.append(rover.getName().equals("Curiosity") ? "https://youtu.be/P4boyXQuUIw?si=h0tWCRZXAPId6l10" : "https://youtu.be/5qqsMjy8Rx0?si=QAT-ZeW4L4oNnalM");
        return stringBuilder.toString();
    }

    /**
     * Retrieves rover information for the specified rover.
     *
     * @param roverName the name of the Mars rover ({@link MarsRover#MARS_CURIOSITY} or {@link MarsRover#MARS_PERSEVERANCE}).
     * @return the formatted rover information.
     */
    public String getRoverInfo(String roverName) {
        String roverUrl = BASE_URL + roverName + "/latest_photos" + ApiKey.API_KEY;
        try {
            List<MarsPhotos> photos = getMarsPhotosList(roverUrl, MarsImageCollectionWithDefaultDate.class);
            Rover rover = photos.getFirst().getRover();
            return createRoverInfoPost(rover);
        } catch (Exception e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return ServerConnectionError.FAILED_REQUEST;
        }
    }

    /**
     * Creates a formatted post containing information about a Mars photo.
     *
     * @param photos the Mars photo object containing information.
     * @return the formatted post.
     */
    public String createPost(MarsPhotos photos) {
        return Emojis.ROCKET + "Rover: " + photos.getRover().getName() + "\n\n" +
                Emojis.CAMERA + "Camera: " + photos.getCamera().getFull_name() + "\n\n" +
                Emojis.DATE + "Date: " + DateUtils.dateFormatter(photos.getEarth_date(), "yyyy-MM-dd", "MMM d, yyyy", Locale.ENGLISH) + "\n\n" +
                Emojis.PICTURE + "HD Image Link:\n" + photos.getImg_src();
    }
}


