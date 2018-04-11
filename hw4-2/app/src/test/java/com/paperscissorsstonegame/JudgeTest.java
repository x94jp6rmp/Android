package com.paperscissorsstonegame;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by snow on 2018/4/7.
 */

public class JudgeTest {
    @Test
    public void Judge(){
        Judge result = new Judge();

        assertEquals(result.getResult(1,1),"draw");
        assertEquals(result.getResult(1,2),"lose");
        assertEquals(result.getResult(1,3),"win");
        assertEquals(result.getResult(2,1),"win");
        assertEquals(result.getResult(2,2),"draw");
        assertEquals(result.getResult(2,3),"lose");
        assertEquals(result.getResult(3,1),"lose");
        assertEquals(result.getResult(3,2),"win");
        assertEquals(result.getResult(3,3),"draw");
    }
}
