package com.snowalker;

import java.util.Random;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/28 23:30
 * @className
 * @desc
 */
public class AAA {


    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            String prefix = "137";
            for (int j = 0; j < 8; j++) {
                prefix += String.valueOf(random.nextInt(10));
            }
            System.out.println(prefix);
        }
    }
}
