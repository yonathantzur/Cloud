package il.ac.colman.cs.util;

import il.ac.colman.cs.ExtractedLink;
import org.jsoup.Jsoup;

/**
 * Extract content from links
 */
public class LinkExtractor {
    public ExtractedLink extractContent(String url) {
    /*
    Use JSoup to extract the text, title and description from the URL.

    Extract the page's content, without the HTML tags.
    Extract the title from title tag or meta tags, prefer the meta title tags.
    Extract the description the same as you would the title.

    For title and description tags, if there are multiple (which is usually the case)
    take the first.
     */

        return new ExtractedLink(url,
                this.getWebContent(url),
                this.getWebTitle(url),
                this.getWebDescription(url),
                ScreenshotGenerator.takeScreenshot(url));
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
