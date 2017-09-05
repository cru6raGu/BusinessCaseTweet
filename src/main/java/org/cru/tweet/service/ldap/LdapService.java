package org.cru.tweet.service.ldap;

import org.cru.tweet.domain.jpa.user.User;

public interface LdapService {
    User getLoggedInUser();
    User getLoggedInUser(String userGuid);
}
