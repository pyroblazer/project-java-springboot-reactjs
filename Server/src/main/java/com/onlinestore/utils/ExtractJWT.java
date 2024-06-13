package com.onlinestore.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String claim) {
        // Decode the JWT token
        DecodedJWT jwt = JWT.decode(token);

        // Extract the claim
        String claimValue = jwt.getClaim(claim).asString();

        // Return the claim value
        return claimValue;
    }
}