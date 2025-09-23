package com.example.calculadorap1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String amount = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonNumber0 = findViewById(R.id.buttonNumber0);
        Button buttonNumber1 = findViewById(R.id.buttonNumber1);
       Button buttonNumber2 = findViewById(R.id.buttonNumber2);
       Button buttonNumber3 = findViewById(R.id.buttonNumber3);
       Button buttonNumber4 = findViewById(R.id.buttonNumber4);
       Button buttonNumber5 = findViewById(R.id.buttonNumber5);
       Button buttonNumber6 = findViewById(R.id.buttonNumber6);
       Button buttonNumber7 = findViewById(R.id.buttonNumber7);
       Button buttonNumber8 = findViewById(R.id.buttonNumber8);
       Button buttonNumber9 = findViewById(R.id.buttonNumber9);

       Button buttonPoint = findViewById(R.id.buttonPoint);

       Button buttonDollar = findViewById(R.id.buttonDollar);

       TextView textViewResult2 = findViewById(R.id.textViewResult2);

       buttonNumber0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "0";
                updateResult(amount);

            }
        });

        buttonNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "1";
                updateResult(amount);
            }
        });

        buttonNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "2";
            }
        });

        buttonNumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "3";
            }
        });

        buttonNumber4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "4";
            }
        });

        buttonNumber5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "5";
            }
        });

        buttonNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "6";
            }
        });

        buttonNumber7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "7";
            }
        });

        buttonNumber8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "8";
            }
        });

        buttonNumber9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "9";
            }
        });

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amount.contains(".")) {
                    amount += ".";
                }
            }
        });


        buttonDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MoneyConversion conversion = new MoneyConversion(amount, "EUR", "USD");
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: número no válido" + e, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void updateResult(String result) {
        TextView textViewResult2 = findViewById(R.id.textViewResult2);
            textViewResult2.setText(result);

    }
}