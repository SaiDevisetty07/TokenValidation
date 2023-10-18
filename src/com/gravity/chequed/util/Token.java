package com.gravity.chequed.util;

import org.apache.commons.codec.net.URLCodec;
import java.util.Scanner;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class Token {
    static final String HEAD = "mzH4H";
    static final String NEWTON = "newton";
    static final char CHARACTER_DELIMITER = '|';
    static final String PART_DELIMITER = ":";

    private Token() {/* private constructor */}

    public static String generateToken(String identify) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
        // current date add an expiration range
        long millis = now.getTimeInMillis() + TimeUnit.MINUTES.toMillis(10);
        String originalToken = HEAD + PART_DELIMITER + identify + PART_DELIMITER + NEWTON + ":" + millis;

        int length = originalToken.length();
        StringBuilder newToken = new StringBuilder(length * 2);

        for (int i = 0; i < length; i++) {
            int tmp = originalToken.charAt(i);
            tmp += 12 + i;

            newToken.append(tmp).append(CHARACTER_DELIMITER);

            tmp = originalToken.charAt(length - i - 1);
            tmp += 2 - i;

            newToken.append(tmp).append(CHARACTER_DELIMITER);
        }

        URLCodec urlCodec = new URLCodec("ASCII");

        return new String(urlCodec.encode(Base64.getEncoder().encode(newToken.toString().getBytes(StandardCharsets.UTF_8))));
    }

    public static void main(String[] args) {
 
    	Scanner sc = new Scanner(System.in);

        System.out.print("Enter the identify string: ");
        String identify = sc.nextLine();

        String token = Token.generateToken(identify);
        System.out.println("Generated Token: " + token);
        
        sc.close();
    }
}
