package org.marseycat.springbootlogin.utils;

import org.apache.catalina.util.RateLimiter;

import java.util.Map;
import java.util.HashMap;

public class rateLimit {
    private static final Map<String, Integer> requests = new HashMap<>();
    private static final Map<String, Long> timestamps = new HashMap<>();

    public static boolean allow(String ip) {
        long now = System.currentTimeMillis();

        if (!timestamps.containsKey(ip) ||
            now - timestamps.get(ip) > 60_000) {
            timestamps.put(ip, now);
            requests.put(ip, 1);

            return true;
        }

        int count = requests.getOrDefault(ip, 0);

        if (count >= 3) {
            return false;
        };

        requests.put(ip, count + 1);

        return true;
    }
}
