package org.cru.tweet.domain.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.cru.tweet.constant.UserStatus;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestTweet {
    public static Tweet createRandom(User user, List<User> toUsers) {
        Tweet tweet = new Tweet();
        tweet.setGuid(RandomStringUtils.randomAlphabetic(10));
        tweet.setFromUser(user);
        tweet.setToUsers(toUsers);
        tweet.setCreateTime(new Date());
        tweet.setId(RandomUtils.nextLong());
        tweet.setTweet(RandomStringUtils.randomAlphabetic(10));

        return tweet;
    }

    public static Tweet createRandom(User user, List<User> toUsers, String tweetMessage) {
        Tweet tweet = createRandom(user, toUsers);
        tweet.setTweet(tweetMessage);

        return tweet;
    }

    public static Page<Tweet> createRandom(User user, List<User> toUsers, int size) {
        List<Tweet> tweetList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            tweetList.add(createRandom(user, toUsers));
        }

        return new PageImpl<Tweet>(tweetList, new PageRequest(0, size), toUsers.size());

    }
}
