package org.marseycat.springbootlogin.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class genSessionToken {
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        return token;
    }
}
