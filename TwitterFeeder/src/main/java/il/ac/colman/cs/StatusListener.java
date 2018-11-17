package il.ac.colman.cs;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.URLEntity;

public class StatusListener implements twitter4j.StatusListener {
    public void onStatus(Status status) {
        // In case the tweet language is English.
        if (status.getLang().equals("en")) {
            // Getting url links in tweet.
            URLEntity urls[] = status.getURLEntities();

            if(urls.length > 0)
            {
                System.out.println("--------------------------------------------------");
                System.out.println("ID: " + status.getId());
                System.out.println("TEXT: " + status.getText());
                System.out.println("DATE: " + status.getCreatedAt());

                // Running on all tweet links.
                int urlIndex = 1;
                for(URLEntity url : urls)
                {
                    System.out.println("LINK " + urlIndex++ + ": " + url.getExpandedURL());
                }

                System.out.println("--------------------------------------------------");
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
