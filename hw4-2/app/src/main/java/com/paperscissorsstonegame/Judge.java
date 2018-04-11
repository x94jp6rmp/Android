package com.paperscissorsstonegame;

/**
 * Created by snow on 2018/4/7.
 */

public class Judge {
    String getResult(int player , int computer){
        String result = "";
        if(player == 1){
            if(computer == 1)
                result = "draw";
            else if(computer == 2)
                result = "lose";
            else if(computer == 3)
                result = "win";
        }
        else if(player == 2){
            if(computer == 1)
                result = "win";
            else if(computer == 2)
                result = "draw";
            else if(computer == 3)
                result = "lose";
        }
        else if(player == 3){
            if(computer == 1)
                result = "lose";
            else if(computer == 2)
                result = "win";
            else if(computer == 3)
                result = "draw";
        }
        return result;
    };
}
