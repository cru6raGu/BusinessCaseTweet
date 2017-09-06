package org.cru.tweet.service.tweet;

import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.view.create.tweet.TweetCreateView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService {
    Tweet findTweetsByUserAndTweetGuid(User user, String tweetGuid);
    Page<Tweet> findTweetsByUser(User user, Pageable pageable);
    Tweet createTweet(User user, TweetCreateView tweetCreateView);
}
