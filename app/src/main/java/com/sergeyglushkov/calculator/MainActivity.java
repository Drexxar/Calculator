package com.sergeyglushkov.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textView);
        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = editText.getText().toString();
                String symbols = "([0-9]|[-+*/)(.])+"; //проверка на наличие запрещенных символов

                if (string.matches(symbols)) {
                    try {
                        double result = new Calculator().decide(editText.getText().toString());
                        textView.setText(Double.toString(result));
                    }
                    //Данное исключение возникает при попытке достать из стэка несуществующее число, что
                    //возникает при недостатке операндов для совершения операции
                    catch (EmptyStackException emptyStack) {
                        Toast.makeText(MainActivity.this, R.string.operand_error, Toast.LENGTH_SHORT).show();
                    }
                }
                //Выводит ошибку, если в строке присутствуют буквы или символы, не связанные с вычислением
                else {
                    Toast.makeText(MainActivity.this, R.string.symbol_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
