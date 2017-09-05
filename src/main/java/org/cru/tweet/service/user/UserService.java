package org.cru.tweet.service.user;

import org.cru.tweet.domain.jpa.user.User;

public interface UserService {
    User getLoggedInUser(String userGuid);
}
