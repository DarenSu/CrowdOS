package com.example.util;


import com.example.entity.Liveness;

import java.security.MessageDigest;

public class CheckLivenessIsEmpty {

    public static Integer checkLivenessIsEmpty(Liveness liveness){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            if (Three(liveness) == 1){
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * Convert byte to hexadecimal
     * @param bytes
     * @return
     */
    private static Integer Three(Liveness liveness){
        Integer integer = 0;
        Integer integer1 = 0;
        if (liveness.getUserId() == null) {integer++;}
        if (liveness.getTotal() == null) {integer++;integer1++;}
        if (liveness.getLivenessId() == null) {integer++;}
        if (liveness.getTemp() == null) {integer++;}
        if (liveness.getTotalMouth() == null) {integer++;}
        if (liveness.getTotalWeek() == null) {integer++;}
        if (liveness.getTotalYear() == null) {integer++;}
        if (liveness.getOnlineTime() == null) {integer++;integer1++;}
        if (integer1 > 0){
            return 0;
        }
        else if (integer <3 ){
            return 1;
        }
        return 0;
    }
}