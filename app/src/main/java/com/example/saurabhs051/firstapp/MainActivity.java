package com.example.saurabhs051.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText myet;
    private TextView mytv;
    private Button mybtn;
    int count=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myet= findViewById(R.id.et1);
        mytv= findViewById(R.id.tv1);
        mybtn= findViewById(R.id.btn1);
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mytv.setText("");
                try{
                    mytv.setText(String.valueOf(evaluate(myet.getText().toString())));
                    count++;
                    Toast.makeText(getBaseContext(),"Thank You !!",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"Wrong Format",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /////////////////////////////////////////////
    public int evaluate(String expression)
    {
        DatabaseReference myRef;
        myRef= FirebaseDatabase.getInstance().getReference("Equations");

        //FirebaseDatabase fd=FirebaseDatabase.getInstance("https://focused-world-197101.firebaseio.com");
        //DatabaseReference myref=fd.getReference(expression);
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i] == ' '){
                continue;
            }

            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }

            else if (tokens[i] == '(')
                ops.push(tokens[i]);

            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        //myref.setValue(k);
        int k=values.pop();
        Info info=new Info(expression,k);
        myRef.child(Integer.toString(count)).setValue(info);
        return k;
    }

    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        return !((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'));
    }

    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
