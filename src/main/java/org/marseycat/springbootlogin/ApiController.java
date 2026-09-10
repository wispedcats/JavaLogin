package org.marseycat.springbootlogin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.marseycat.springbootlogin.utils.checkPassword;
import org.marseycat.springbootlogin.utils.rateLimit;
import org.marseycat.springbootlogin.utils.genSessionToken;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;





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
            jdbcTemplate.update("""
                            INSERT INTO Users (uuid, username, password, ip, token, latestLogin)
                            VALUES (?, ?, ?, ?, ?, ?)
                            """,
                    uuid,
                    username,
                    password,
                    ip,
                    token,
                    now
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

        String latestLogin = "";

        Integer count = jdbcTemplate.queryForObject("""
                select count(*)
                FROM users
                WHERE username = ? AND password = ?;
                
                """, Integer.class, username, password);

        Map<String, Object> UserData = jdbcTemplate.queryForMap(
                """
                SELECT ip, latestLogin
                FROM users
                WHERE username = ?
                """, username);

        System.out.println(UserData.get("ip"));

        String token = genSessionToken.generateToken();


        if (count != null && count > 0) {
            return ResponseEntity.ok(
                    Map.of(
                            "success", "true",
                            "token", token,
                            "role", "user",
                            "latestLogin", latestLogin
                    )
            );
        }

        return ResponseEntity
                .status(401)
                .body("Invalid username or password");
    }
}
