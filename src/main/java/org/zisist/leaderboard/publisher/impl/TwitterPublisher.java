package org.zisist.leaderboard.publisher.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.publisher.Publisher;
import org.zisist.model.Configuration;
import org.zisist.model.TopPlayer;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.Date;
import java.util.List;

/**
 * Publishes to twitter the results of the algorithm's execution<br>
 *
 * Created by zis.tax@gmail.com on 12/10/2012 at 2:56 AM
 */
@Service("twitterPublisher")
public class TwitterPublisher implements Publisher {

    @Value("#{leaderboardProps['twitter.consumer.key']}")
    private String consumerKey;
    @Value("#{leaderboardProps['twitter.consumer.secret']}")
    private String consumerSecret;
    @Value("#{leaderboardProps['twitter.token']}")
    private String token;
    @Value("#{leaderboardProps['twitter.token.secret']}")
    private String tokenSecret;

    @Override
    public void publish(List<TopPlayer> topPlayerList, Configuration conf) {
        // instantiate a new Twitter instance
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));

            Date postDate = new Date();
            twitter.updateStatus("Publishing Top of the World! " + postDate);
            for(int i=0;i<topPlayerList.size(); i++) {
                StringBuilder message = new StringBuilder("");
                TopPlayer topPlayer = topPlayerList.get(i);
                message.append(i+1).append(".").append(topPlayer.getName()).append(" -- ")
                        .append(topPlayer.getPoints()).append(" -- ")
                        .append(topPlayer.getCountry()).append(" || ").append(postDate);
                twitter.updateStatus(message.toString());
                Thread.sleep(100);
            }

        } catch (TwitterException e) {
            throw new IllegalStateException("Illegal access while trying to tweet", e);
        } catch (Exception e) {
            throw new IllegalStateException("Illegal access while trying to tweet", e);
        }
    }
}
