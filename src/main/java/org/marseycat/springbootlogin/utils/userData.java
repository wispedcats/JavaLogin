package org.marseycat.springbootlogin.utils;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Map;

public class userData {
        private final JdbcTemplate jdbcTemplate;

        public userData(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public Map<String, Object> getUserData(String username) {
            return jdbcTemplate.queryForMap(
                        """
                            SELECT ip, latestLogin, role
                            FROM users
                            WHERE username = ?
                            LIMIT 1;
                            """, username);
        }

        public Map<String, Object> returnUserDataToken(String token) {
            // check session expiry
            try {
                return jdbcTemplate.queryForMap(
                        """
                                SELECT username, ip, latestLogin, role
                                FROM users
                                WHERE token = ?
                                LIMIT 1;
                                """, token);
            } catch (EmptyResultDataAccessException e) {
                return Map.of(
                        "status", 403
                );
            }
        }
    }