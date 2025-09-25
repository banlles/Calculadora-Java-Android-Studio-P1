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

import com.example.calculadorap1.exceptions.convertException;

public class MainActivity extends AppCompatActivity {

    String amount = "";
    String toCurrency = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Convert.clearRates(this);

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


        //BOTONES DE LOS NUMEROS

        buttonNumber0.setOnClickListener(v -> appendDigit("0"));
        buttonNumber1.setOnClickListener(v -> appendDigit("1"));
        buttonNumber2.setOnClickListener(v -> appendDigit("2"));
        buttonNumber3.setOnClickListener(v -> appendDigit("3"));
        buttonNumber4.setOnClickListener(v -> appendDigit("4"));
        buttonNumber5.setOnClickListener(v -> appendDigit("5"));
        buttonNumber6.setOnClickListener(v -> appendDigit("6"));
        buttonNumber7.setOnClickListener(v -> appendDigit("7"));
        buttonNumber8.setOnClickListener(v -> appendDigit("8"));
        buttonNumber9.setOnClickListener(v -> appendDigit("9"));


        // BOTON DEL PUNTO DECIMAL

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amount.contains(".")) { //no hay un punto
                    if (amount.isEmpty()) {  // no hay ningun numero aun
                        amount = "0";
                    }
                    amount += ".";
                    updateResult(amount);
                } else {
                    Toast.makeText(MainActivity.this, "Ya has introducido un punto decimal", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //BOTONES DE LAS MONEDAS

        buttonLliures.setOnClickListener(e -> askUserForToCurrency("LLIURES"));
        buttonDollar.setOnClickListener(e -> askUserForToCurrency("DOLLAR"));
        buttonYen.setOnClickListener(e -> askUserForToCurrency("YEN"));
        buttonYuan.setOnClickListener(e -> askUserForToCurrency("YUAN"));



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
                    amount = amount.substring(0, amount.length() - 1); // eliminar el último carácter empezando por 0
                    updateResult(amount);
                }
            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText(" ");
                if (amount.isEmpty() || toCurrency.isEmpty()) {  // falta cantidad o moneda
                    Toast.makeText(MainActivity.this, "Debes introducir una cantidad y seleccionar una moneda", Toast.LENGTH_SHORT).show();
                    return;
                }

                MoneyConversion moneyConversion;
                try {
                    moneyConversion = new MoneyConversion(amount, "EURO", toCurrency); //creamos el objeto
                    moneyConversion = Convert.convert(MainActivity.this, moneyConversion); // lo convertimos
                    textViewResult.setText(moneyConversion.getResultString());  // mostramos el resultado, llamando a convert
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void appendDigit(String d) {
        if (NumberCount.hasMoreThanTwoDecimals(amount)) {
            Toast.makeText(this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();
            return;
        }
            amount += d;
        updateResult(amount);
    }

    public void updateResult(String result) {
        TextView textViewResult2 = findViewById(R.id.textViewResult2);
        textViewResult2.setText(result);
    }

    private void askUserForToCurrency(String currency) {

        double rate;
        try {
            rate = Convert.getRate(this, currency);
        } catch (convertException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado al obtener la tasa de conversión", Toast.LENGTH_SHORT).show();
            return;
        }

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
                        } catch (convertException e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error inesperado al guardar la tasa de conversión", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else {
            toCurrency = currency; // existe
        }
    }

}