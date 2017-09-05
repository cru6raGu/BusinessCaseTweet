package org.cru.tweet.util;

import java.util.UUID;

public class GuidUtil {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");

    }
}
