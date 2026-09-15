package org.marseycat.springbootlogin.utils;


import org.springframework.web.client.RestClient;
import org.marseycat.springbootlogin.utils.userData;

import java.time.Duration;
import java.util.Date;

public class checkSessionExpiry {
    private final RestClient restClient = null;
    
    public void checkExpiry(String newIp) {
        Date now = new Date();


    }
}

/*
curl -X POST \
    -H "username: maxi" \
    -H 'password: !1234567'\
    http://localhost:8080/api/v1/login

curl -X POST \
    -H "username: admin" \
    -H 'password: !1234567'\
    http://localhost:8080/api/v1/login


* */