package il.ac.colman.cs.util;

import il.ac.colman.cs.ExtractedLink;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extract content from links
 */
public class LinkExtractor {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CloudWatch cw;

    public LinkExtractor() {
        cw = new CloudWatch();
    }

    public ExtractedLink extractContent(String url) {
        long startTime = System.nanoTime();
        String screenshotURL = ScreenshotGenerator.takeScreenshot(url);
        long endScreenshotTime = (System.nanoTime() - startTime) / 1000000; // In millisecond
        cw.SendMetric("screenshot_time", (double)endScreenshotTime);

        ExtractedLink linkObj = new ExtractedLink(url,
                this.getWebTitle(url),
                this.getWebContent(url),
                this.getWebDescription(url),
                format.format(new Date()),
                screenshotURL,
                System.getProperty("track"));

        long endLinkScanTime = (System.nanoTime() - startTime) / 1000000; // In millisecond

        cw.SendMetric("process_tweet", (double)endLinkScanTime);

        return linkObj;
    }

    private String getWebContent(String url) {
        try {
            return Jsoup.connect(url).get().body().text();
        } catch (Exception e) {
            return null;
        }
    }

    private String getWebTitle(String url) {
        try {
            return Jsoup.connect(url).get().title();
        } catch (Exception e) {
            return null;
        }
    }

    private String getWebDescription(String url) {
        try {
            return Jsoup.connect(url).get().select("meta[name=description]").get(0)
                    .attr("content");
        } catch (Exception e) {
            return null;
        }
    }
}
