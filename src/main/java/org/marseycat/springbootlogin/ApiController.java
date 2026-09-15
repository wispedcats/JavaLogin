package org.marseycat.springbootlogin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.marseycat.springbootlogin.utils.checkPassword;
import org.marseycat.springbootlogin.utils.rateLimit;
import org.marseycat.springbootlogin.utils.genSessionToken;
import org.marseycat.springbootlogin.utils.userData;

import java.time.LocalDate;
import java.util.*;


@RestController
public class ApiController {

    private final JdbcTemplate jdbcTemplate;

    public ApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(
            @RequestHeader(value = "username", required = true) String username,
            @RequestHeader(value = "password", required = true) String password,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();

        if (!rateLimit.allow(ip)) {
            return ResponseEntity
                    .status(403)
                    .body("bro stop spamming get out :sob");
        }

        checkPassword checker = new checkPassword();

        Date now = new Date();

        ResponseEntity<String> result = checker.checkPassword(password);

        if (result != null) {
            return result;
        }

        UUID uuid = UUID.randomUUID();

        String token = genSessionToken.generateToken();

        try {
            String role = null;
            if (username.equals("admin")) {
                role = "admin";
            } else {
                role = "user";
            }

            jdbcTemplate.update("""
                            INSERT INTO Users (uuid, username, password, ip, token, latestLogin, role, banned, banReason)
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                            """,
                    uuid,
                    username,
                    password,
                    ip,
                    token,
                    now,
                    role,
                    "false",
                    "NONE"
            );
            return ResponseEntity.ok(
                    Map.of(
                            "status", 200,
                            "uuid", uuid,
                            "token", token
                    ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(e.getMessage());
        }
    }

    @PostMapping("api/v1/auth")
    public ResponseEntity<?> auth(
            @RequestHeader(value = "auth", required = true) String authToken
    ) {
        userData userData = new userData(jdbcTemplate);

        Map<String, Object> data = userData.returnUserDataToken(authToken);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(
            @RequestHeader(value = "username", required = true) String username,
            @RequestHeader(value = "password", required = true) String password,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();
        if (!rateLimit.allow(ip)) {
            return ResponseEntity
                    .status(403)
                    .body("broo stop spamming :sob");
        }

        Integer count = jdbcTemplate.queryForObject("""
                select count(*)
                FROM users
                WHERE username = ? AND password = ?;
                
                
                """, Integer.class, username, password);

        if (count != null && count > 0) {
            userData userData = new userData(jdbcTemplate);

            Map<String, Object> data = userData.getUserData(username);

            String role = (String) data.get("role");

            LocalDate now = LocalDate.now();

            jdbcTemplate.update("""
                    UPDATE users
                    SET latestLogin = ?
                    WHERE username = ?;
                    """,
                    now,
                    username
            );
            return ResponseEntity.ok(
                    Map.of(
                            "success", "true",
                            "token", "token check",
                            "role", role
                    )
            );
        }
        return ResponseEntity
                .status(401)
                .body("Invalid username or password");
    }
}
