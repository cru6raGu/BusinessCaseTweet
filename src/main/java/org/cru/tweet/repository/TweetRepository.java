package org.cru.tweet.repository;

import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.repository.internal.JpaGuidRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaGuidRepository<Tweet, Long> {
    Page<Tweet> findByFromUser(User user, Pageable pageable);
}
