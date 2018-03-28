package com.example.saurabhs051.firstapp;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by saurabhs051 on 27/3/18.
 */

public class Info {
    String exp;
    int val;
    ArrayList var;

    public Info(){

    }

    public Info(String exp, int val) {
        this.exp = exp;
        this.val = val;
        ////////////////////////////////
        var=new ArrayList();
        char[] tokens = exp.toCharArray();
        for(int i=0;i<tokens.length;i++){
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                var.add(Integer.parseInt(sbuf.toString()));
            }
        }
    }
}
