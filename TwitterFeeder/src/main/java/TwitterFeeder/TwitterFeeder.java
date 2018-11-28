package TwitterFeeder;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFeeder {
  public static void main(String[] args) throws Exception {
      // load the sqlite-JDBC driver using the current class loader
      Class.forName("org.sqlite.JDBC");

    // Create configuration and set twitter auth access tokens.
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(System.getenv("TWITTER_API_KEY"))
        .setOAuthConsumerSecret(System.getenv("TWITTER_API_SECRET_KEY"))
        .setOAuthAccessToken(System.getenv("TWITTER_ACCESS_TOKEN"))
        .setOAuthAccessTokenSecret(System.getenv("TWITTER_ACCESS_TOKEN_SECRET"));

    // Create twitter stream.
    TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
    TwitterStream twitterStream = tf.getInstance();

    twitterStream.addListener(new StatusListener());

    // Create twitter tweets filter.
    FilterQuery tweetFilterQuery = new FilterQuery();
    tweetFilterQuery.track(new String[]{"apple"});

    twitterStream.filter(tweetFilterQuery);


  }
}
