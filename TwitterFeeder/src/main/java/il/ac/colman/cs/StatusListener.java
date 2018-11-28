package il.ac.colman.cs;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.URLEntity;

import javax.print.DocFlavor;
import java.sql.SQLException;

public class StatusListener implements twitter4j.StatusListener {

    private DAL dal;

    public StatusListener() throws SQLException {
        this.dal = new DAL();
    }

    public void onStatus(Status status) {
        // In case the tweet language is English.
        if (status.getLang().equals("en")) {
            // Getting url links in tweet.
            URLEntity urls[] = status.getURLEntities();

            if(urls.length > 0)
            {
                // Running on all tweet links.
                for(URLEntity url : urls)
                {
                    String link = url.getExpandedURL();
                    String title = webHandler.getWebTitle(link);
                    String content = webHandler.getWebContent(link);

                    dal.InsertWebsite(link, status.getId(), title, content, status.getCreatedAt(), Screenshot.take(link));
//                    dal.PrintDBToConsole();
//                    System.exit(-1);
                }
            }
        }
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

    }

    public void onScrubGeo(long userId, long upToStatusId) {

    }

    public void onStallWarning(StallWarning warning) {

    }

    public void onException(Exception ex) {

    }
}
