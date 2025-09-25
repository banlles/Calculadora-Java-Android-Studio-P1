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

    MoneyConversion moneyConversion2 = new MoneyConversion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Convert.clearRates(this);


        // ESCUCHANDO BOTONES DE LOS NUMEROS Y PUNTO
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


        // ESCUCHANDO BOTONES DE LAS MONEDAS
        Button buttonDollar = findViewById(R.id.buttonDollar);
        Button buttonYen = findViewById(R.id.buttonYen);
        Button buttonYuan = findViewById(R.id.buttonYuan);
        Button buttonLliures = findViewById(R.id.buttonLliures);


        // ESCUCHANDO BOTONES DE BORRAR Y CONVERTIR
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonResult = findViewById(R.id.buttonResult);


        // ESCUCHANDO LOS TEXTVIEW
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
                String currentAmount = moneyConversion2.getAmount(); // obtenemos el valor actual desde el objeto

                if (!currentAmount.contains(".")) { // no hay un punto
                    if (currentAmount.isEmpty()) {
                        moneyConversion2.setAmount("0", false);
                    }
                    moneyConversion2.setAmount(".", true);
                    updateResult(moneyConversion2.getAmount());
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
                moneyConversion2.setAmount(" ", false);
                moneyConversion2.setAmount(" ", false);
                textViewResult.setText("0");
                updateResult("0");
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentAmount = moneyConversion2.getAmount();

                if (!currentAmount.isEmpty()) {
                    String newAmount = currentAmount.substring(0, currentAmount.length() - 1); // elimina el ultimo caracter empezando por 0 y luego a la longitud le resta 1

                    moneyConversion2.setAmount(newAmount, false);
                    updateResult(moneyConversion2.getAmount());
                }
            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText(" ");
                String amount = moneyConversion2.getAmount();
                String toCurrency = moneyConversion2.getToCurrency();
                if (amount.isEmpty() || toCurrency.isEmpty()) {  // falta cantidad o moneda
                    Toast.makeText(MainActivity.this, "Debes introducir una cantidad y seleccionar una moneda", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    moneyConversion2 = Convert.convertMoney(MainActivity.this, moneyConversion2); // lo convertimos
                    textViewResult.setText(moneyConversion2.getResultString());  // mostramos el resultado, llamando a convert
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void appendDigit(String d) {
        if (NumberCount.hasMoreThanTwoDecimals(moneyConversion2.getAmount())) {
            Toast.makeText(this, "No puedes poner mas de dos decimales", Toast.LENGTH_SHORT).show();
            return;
        }
        moneyConversion2.setAmount(d, true);
        updateResult(moneyConversion2.getAmount());
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
                            moneyConversion2.setToCurrency(currency);
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
            moneyConversion2.setToCurrency(currency);
        }
    }

}