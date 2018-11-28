package TwitterFeeder;

import org.jsoup.Jsoup;
import java.io.IOException;

public class webHandler {
    public static String getWebTitle(String url) {

        try {
            return Jsoup.connect(url).get().title();
        } catch (IOException e) {
            return null;
        }
    }

    public static String getWebContent(String url) {
        try {
            return Jsoup.connect(url).get().body().text();
        } catch (IOException e) {
            return null;
        }
    }
}
