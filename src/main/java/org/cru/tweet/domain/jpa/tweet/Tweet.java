package org.cru.tweet.domain.jpa.tweet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.domain.jpa.AbstractExternalImmutableDomainObject;
import org.cru.tweet.domain.jpa.AbstractExternalMutableDomainObject;
import org.cru.tweet.domain.jpa.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Table(name = "tweet")
public class Tweet extends AbstractExternalImmutableDomainObject<Long> {
    @Column(name = "tweet")
    private String tweet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToMany()
    @JoinTable(name = "tweet_message_relationship",
            joinColumns = @JoinColumn(name = "tweet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "to_user_id", referencedColumnName = "id"))
    private List<User> toUsers;
}
