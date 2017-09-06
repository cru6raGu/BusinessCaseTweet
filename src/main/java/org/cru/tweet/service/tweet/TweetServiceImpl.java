package org.cru.tweet.service.tweet;

import lombok.extern.slf4j.Slf4j;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.tweet.TweetFollower;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.repository.TweetFollowerRepository;
import org.cru.tweet.repository.TweetRepository;
import org.cru.tweet.util.GuidUtil;
import org.cru.tweet.view.create.tweet.TweetCreateView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service("tweetService")
@Transactional(propagation = Propagation.REQUIRED)
public class TweetServiceImpl implements TweetService {
    @Resource
    TweetRepository tweetRepository;

    @Resource
    TweetFollowerRepository tweetFollowerRepository;

    @Override
    public Tweet findTweetsByUserAndTweetGuid(User user, String tweetGuid) {
        return tweetRepository.findByFromUserAndGuid(user, tweetGuid);
    }

    @Override
    public Page<Tweet> findTweetsByUser(User user, Pageable pageable) {
        return tweetRepository.findByFromUser(user, pageable);
    }

    @Override
    public Tweet createTweet(User user, TweetCreateView tweetCreateView) {
        if (Objects.isNull(user) || Objects.isNull(tweetCreateView)) {
            //TODO fix exceptions
            throw new RuntimeException();
        }

        Tweet tweet = new Tweet();
        tweet.setTweet(tweetCreateView.getTweet());
        tweet.setFromUser(user);
        //TODO move guid generation to a JPA listener.
        tweet.setGuid(GuidUtil.generateUUID());

        List<TweetFollower> tweetFollowers = tweetFollowerRepository.findByUser(user);

        List<User> followers = tweetFollowers.stream().map(TweetFollower::getFollowerUser).collect(Collectors.toList());
        tweet.setToUsers(followers);

        return tweetRepository.saveAndFlush(tweet);
    }
}
