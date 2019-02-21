package il.ac.colman.cs;

public class ExtractedLink {
    private final String url;
    private final String title;
    private final String content;
    private final String description;
    private final String timestamp;
    private final String screenshotURL;
    private final String track;

    public ExtractedLink(String url, String title, String content, String description, String timestamp, String screenshotURL, String track) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.description = description;
        this.timestamp = timestamp;
        this.screenshotURL = screenshotURL;
        this.track = track;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getScreenshotURL() {
        return screenshotURL;
    }

    public String getTrack() {
        return track;
    }

}
