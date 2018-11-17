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
                System.out.println(status.getText());
//                System.out.println(status.getId());
//                System.out.println(status.getCreatedAt());
//                System.out.println(status.getURLEntities()[0]);
//                System.out.println(status.getURLEntities()[0].getText());

                for(URLEntity url : urls)
                {
//                    System.out.println(url.getText());
                    System.out.println(url.getExpandedURL());
//                    System.out.println(url.getDisplayURL());
//                    System.out.println(url.getEnd());
//                    System.out.println(url.getStart());
//                    System.out.println(url.getURL());
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
