package il.ac.colman.cs;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFeeder {
  public static void main(String[] args) {
    // Create configuration and set twitter auth access tokens.
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("J1OAPrEmfcVBmlxhJ2mU6jC2j")
        .setOAuthConsumerSecret("0LEXcpoqWlNnYGaLSi4dsNVa5uPDvpertZ7PaqX28H1cKhUuH2")
        .setOAuthAccessToken("1036263641132294144-izWyYYAnOFRqWq8mZGM11bsSV8YJ6K")
        .setOAuthAccessTokenSecret("1IswirWEDHM4GRP6NN3qqpckCD4SYs5z26aMmhZLKPN0y");

    // Create our Twitter stream
    TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
    TwitterStream twitterStream = tf.getInstance();

    twitterStream.addListener(new StatusListener());

    FilterQuery tweetFilterQuery = new FilterQuery();
    tweetFilterQuery.track(new String[]{"apple"});

    twitterStream.filter(tweetFilterQuery);
  }
}
