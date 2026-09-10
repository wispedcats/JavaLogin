package org.marseycat.springbootlogin.utils;

import org.springframework.http.ResponseEntity;

public class checkPassword {

    public ResponseEntity<String> checkPassword(String password) {
        if (    !password.contains("!") &&
                !password.contains("?") &&
                !password.contains("#") &&
                !password.contains("$") &&
                !password.contains(".") &&
                !password.contains("-") &&
                !password.contains("1") &&
                !password.contains("2") &&
                !password.contains("3") &&
                !password.contains("4") &&
                !password.contains("5") &&
                !password.contains("6") &&
                !password.contains("7") &&
                !password.contains("8") &&
                !password.contains("9") &&
                !password.contains("0")
        ) {
            return ResponseEntity
                    .status(400)
                    .body("Add at least 1 special character and 1 Number");
        }
        if (password.length() < 8) {
            return ResponseEntity
                    .status(400)
                    .body("Password too short. At least 8 characters long");
        }
        return null;
    }

}
