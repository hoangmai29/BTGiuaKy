package com.example.CICD_BT;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {

    @Test
    void simpleTest() {
        int expected = 10;
        int actual = 5 + 5;
        assertEquals(expected, actual, "5 + 5 phải bằng 10");
    }
}
