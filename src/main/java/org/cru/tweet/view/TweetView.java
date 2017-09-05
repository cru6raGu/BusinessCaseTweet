package org.cru.tweet.view;

import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.view.internal.AbstractExternalView;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TweetView extends AbstractExternalView {
    private String tweet;
    private String guid;
    private Date createTime;
    private UserView fromUser;
    private List<UserView> toUsers;
}
