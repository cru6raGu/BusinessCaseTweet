package org.cru.tweet.repository;

import org.cru.tweet.domain.jpa.tweet.TweetFollower;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.repository.internal.JpaGuidRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetFollowerRepository extends JpaGuidRepository<TweetFollower, Long> {
    List<TweetFollower> findByUser(User user);
}

