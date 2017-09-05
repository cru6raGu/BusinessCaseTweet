package org.cru.tweet.domain.jpa.tweet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.domain.jpa.AbstractExternalImmutableDomainObject;
import org.cru.tweet.domain.jpa.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tweet_follower")
public class TweetFollower extends AbstractExternalImmutableDomainObject<Long> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower_user_id")
    protected User followerUser;
}
