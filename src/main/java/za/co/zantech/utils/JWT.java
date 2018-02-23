package za.co.zantech.utils;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import za.co.zantech.messagespec.UserSpec;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by zandrewitte on 2017/06/05.
 * JWT
 */
public class JWT {

    private static Key _key = MacProvider.generateKey();

    public static Key getKey() {
        return _key;
    }

    public static String createJTWToken(UserSpec.User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", user.getUserName());
        claims.put("role", user.getRole());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5);

        return Jwts.builder()
                .setIssuer("CABLEPRO_API")
                .setClaims(claims)
                .setExpiration(cal.getTime())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
    }
}
