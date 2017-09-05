package org.cru.tweet.domain.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.cru.tweet.constant.UserStatus;
import org.cru.tweet.domain.jpa.user.User;

public class TestUser {
    public static User createRandom() {
        User user = new User();
        user.setEmail(String.format("%s@example.com", RandomStringUtils.randomAlphabetic(10)));
        user.setFirst_name(RandomStringUtils.randomAlphabetic(10));
        user.setLast_name(RandomStringUtils.randomAlphabetic(10));
        user.setMiddle_name(RandomStringUtils.randomAlphabetic(10));
        user.setStatus(UserStatus.ACTIVE);
        user.setUsername(RandomStringUtils.randomAlphabetic(10));
        user.setGuid(RandomStringUtils.randomAlphabetic(30));
        user.setId(RandomUtils.nextLong());

        return user;
    }
}
