package org.cru.tweet.service.ldap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service("ldapService")
@Transactional(propagation = Propagation.REQUIRED)
public class LdapServiceImpl implements LdapService {
    @Resource
    UserService userService;

    @Override
    public User getLoggedInUser() {
        return userService.getLoggedInUser("aafe5ce04be4486a84268f69a5ab3cd1");
    }

    @Override
    public User getLoggedInUser(String userGuid) {
        //TODO demo only code. should be removed.
        if (StringUtils.isNoneBlank(userGuid)) {
            return userService.getLoggedInUser(userGuid);
        } else {
            return getLoggedInUser();
        }
    }
}
