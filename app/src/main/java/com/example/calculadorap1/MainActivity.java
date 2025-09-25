package com.example.calculadorap1;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String amount = "";
    String toCurrency = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Convert.clearRates(this);
        Convert.setRate(this, "EURO", 1.0);

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
        Button buttonYen = findViewById(R.id.buttonYen);
        Button buttonYuan = findViewById(R.id.buttonYuan);
        Button buttonLliures = findViewById(R.id.buttonLliures);

        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonResult = findViewById(R.id.buttonResult);

        TextView textViewResult2 = findViewById(R.id.textViewResult2);
        TextView textViewResult = findViewById(R.id.textViewResult);



        //BOTONES DE LOS NUMEROS Y EL PUNTO

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
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "2";
                updateResult(amount);
            }
        });

        buttonNumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "3";
                updateResult(amount);
            }
        });

        buttonNumber4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "4";
                updateResult(amount);
            }
        });

        buttonNumber5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "5";
                updateResult(amount);
            }
        });

        buttonNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "6";
                updateResult(amount);
            }
        });

        buttonNumber7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "7";
                updateResult(amount);
            }
        });

        buttonNumber8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "8";
                updateResult(amount);
            }
        });

        buttonNumber9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberCount.hasMoreThanTwoDecimals(amount)) {
                    Toast.makeText(MainActivity.this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();

                    return; // salir si ya hay más de dos decimales
                }
                amount += "9";
                updateResult(amount);
            }
        });

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amount.contains(".")) {
                    amount += ".";
                    updateResult(amount);
                } else {
                    Toast.makeText(MainActivity.this, "Ya has introducido un punto decimal", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //BOTONES DE LAS MONEDAS

        buttonDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askUserForToCurrency("DOLLAR");
            }
        });

        buttonYen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askUserForToCurrency("YEN");
            }

        });

        buttonYuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askUserForToCurrency("YUAN");
            }
        });

        buttonLliures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askUserForToCurrency("LLIURES");
            }
        });


        //BOTONES DE BORRAR Y CONVERTIR

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = "";
                toCurrency = "";
                textViewResult.setText("");
                updateResult("");
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amount.isEmpty()) {
                    amount = amount.substring(0, amount.length() - 1);
                    updateResult(amount);
                }
            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.isEmpty() || toCurrency.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Debes introducir una cantidad y seleccionar una moneda", Toast.LENGTH_SHORT).show();
                    return;
                }

                MoneyConversion moneyConversion;
                try {
                    moneyConversion = new MoneyConversion(amount, "EURO", toCurrency);
                    textViewResult.setText(Convert.convert(MainActivity.this, moneyConversion));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateResult(String result) {
        TextView textViewResult2 = findViewById(R.id.textViewResult2);
        textViewResult2.setText(result);
    }

    private void askUserForToCurrency(String currency) {

        double rate = Convert.getRate(this, currency);

        if (rate == -1) { // si no existe
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            new AlertDialog.Builder(this)
                    .setTitle("Nuevo factor de conversión")
                    .setMessage("Introduce el valor en euros de 1 " + currency)
                    .setView(input)
                    .setPositiveButton("Guardar", (dialog, which) -> {
                        try {
                            double newRate = Double.parseDouble(input.getText().toString());
                            Convert.setRate(this, currency, newRate);
                            toCurrency = currency; // ya puedes usarlo
                            Toast.makeText(this, "Guardado: 1 " + currency + " = " + newRate + " €", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else {
            toCurrency = currency; // existe
        }
    }

}