package org.marseycat.springbootlogin.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class checkAuth {
    private static JdbcTemplate jdbcTemplate;

    public checkAuth(JdbcTemplate jdbcTemplate) {
        checkAuth.jdbcTemplate = jdbcTemplate;
    }
    public Map<String, Object> checkAuth(String token) {

        if (token == null) {
            return Map.of(
                    "status", 403
            );
        }
        if (token.length() < 6) {
            return Map.of(
                    "status", 403
            );
        }

           Map<String, Object> getUserHealth = jdbcTemplate.queryForMap(
               """
               SELECT banned, banReason
               from users
               WHERE token = ?
               """, token);

        if (getUserHealth.get("status").equals("true")) {
            return Map.of(
                    "status", 403,
                    "body", "Sorry Your account has been locked",
                    "reason", getUserHealth.get("banReason")
            );
        }

        return Map.of(
                "status", 403
        );
    }
}
