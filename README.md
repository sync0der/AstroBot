# AstroBot v1.0

AstroBot is a Telegram bot developed in Java that leverages NASA's APIs to provide users with access to various NASA services. Users can fetch stunning astronomy pictures, Earth snapshots from space, Mars rover photos, detailed Mars rover information, and images from NASA's vast image library.

# Important Info!
[Astro Bot](https://t.me/astroexlporer_bot) is not yet fully deployed, as its functionalities are still being enhanced. While development and feature additions are ongoing, you can explore its current capabilities in the video. Please [watch the video](https://t.me/astrofunctions)  to get acquainted with the functionality.

## Features

AstroBot enables users to fetch:
- 📍 **Astronomy Picture of the Day (APOD)**
- 📍 **Earth snapshots from the EPIC camera**
- 📍 **Mars Rover photos**
- 📍 **Mars Rover information**
- 📍 **Images from the NASA Image Library**
##
- All errors are logged and sent to the developer by [Astro Logger](https://t.me/astrologger_bot).

## Commands

Below are the commands that the bot can process:

### `/apod`
Gets the Astronomy Picture of the Day for the specified date (in format: `yyyy-mm-dd`) or for the default (the latest) date.

### `/epic`
Gets snapshots of Earth from the Earth Polychromatic Imaging Camera for the specified date (in format: `yyyy-mm-dd`) or for the default (the latest) date. The service is available starting from `2015-06-13`.

### `/rover`
Fetches photos from the Mars Rovers for the specified date (in format: `yyyy-mm-dd`) or for the default (the latest) day.
- **Curiosity Rover** is available starting from `2012-06-08`.
- **Perseverance Rover** - from `2021-02-18`.

### `/roverinfo`
Obtains information about the Curiosity or Perseverance rover.

### `/image [topic]`
Receives a random image from the NASA Image Library based on the provided topic.
- **Example:** `/image Galaxy`

## Usage

Add AstroBot to your Telegram and start interacting with it using the commands mentioned above. Enjoy exploring the universe with the help of NASA's amazing data!

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.

## Acknowledgements

- [NASA APIs](https://api.nasa.gov/) for providing access to their incredible data.
- [Telegram Bots Java Library](https://github.com/rubenlagus/TelegramBots) for facilitating easy interaction with the Telegram Bot API.
