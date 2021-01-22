package com.blb.wfx_mber;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WfxMberApplicationTests {

    public static void showInfo(int a,int b)
    {
        a++;
        b++; }

    public static void main(String[] args) {
        Integer a = 5; Integer b = 10;
        System.out.println(a + "," + b);
        showInfo(a,b);
        System.out.println(a + "," + b);
    }

    @Test
    void contextLoads() {
    }

}
