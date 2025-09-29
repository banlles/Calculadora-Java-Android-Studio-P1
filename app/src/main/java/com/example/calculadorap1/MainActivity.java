package com.example.calculadorap1;

import android.content.Context;
import android.os.Bundle;
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
    TextView textViewResult2;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Convert.clearRates(this);


        //----------------------------------ESCUCHA DE LOS BOTONES-------------------------------


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

        textViewResult2 = findViewById(R.id.textViewResult2);
        textViewResult = findViewById(R.id.textViewResult);


        //----------------------------------FUNCIONAMIENTO DE LOS BOTONES-------------------------------


        //BOTONES DE LOS NUMEROS

        buttonNumber0.setOnClickListener(v -> appendAmount("0"));
        buttonNumber1.setOnClickListener(v -> appendAmount("1"));
        buttonNumber2.setOnClickListener(v -> appendAmount("2"));
        buttonNumber3.setOnClickListener(v -> appendAmount("3"));
        buttonNumber4.setOnClickListener(v -> appendAmount("4"));
        buttonNumber5.setOnClickListener(v -> appendAmount("5"));
        buttonNumber6.setOnClickListener(v -> appendAmount("6"));
        buttonNumber7.setOnClickListener(v -> appendAmount("7"));
        buttonNumber8.setOnClickListener(v -> appendAmount("8"));
        buttonNumber9.setOnClickListener(v -> appendAmount("9"));


        // BOTON DEL PUNTO DECIMAL

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentAmount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount

                if (!currentAmount.contains(".")) { // no hay un punto
                    if (currentAmount.isEmpty()) { // esta vacio
                        moneyConversion2.setAmount("0", false); // añadimos un 0 sin concatenar al objeto moneyConversion
                    }
                    moneyConversion2.setAmount(".", true); // añadimos el . concatenando al objeto moneyConversion
                    updateResult(moneyConversion2.getAmount(), 2);
                } else {
                    toastText(MainActivity.this, "Ya has introducido un punto decimal", "s");
                }
            }
        });


        //BOTONES DE LAS MONEDAS

        buttonLliures.setOnClickListener(e -> askUserForToCurrency("LLIURES"));
        buttonDollar.setOnClickListener(e -> askUserForToCurrency("DOLLAR"));
        buttonYen.setOnClickListener(e -> askUserForToCurrency("YEN"));
        buttonYuan.setOnClickListener(e -> askUserForToCurrency("YUAN"));


        //BOTON DE BORRAR

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //seteamos todo en 0 o nada
                moneyConversion2.setAmount(" ", false);
                moneyConversion2.setAmount(" ", false);
                textViewResult.setText("0");
                updateResult("0", 2);
            }
        });


        //BOTON DE TIRAR UN NUMERO PARA ATRAS

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentAmount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount

                if (!currentAmount.isEmpty()) { // amount no esta vacio
                    String newAmount = currentAmount.substring(0, currentAmount.length() - 1); // elimina el ultimo caracter empezando por 0 y luego a la longitud le resta 1

                    moneyConversion2.setAmount(newAmount, false); // añadimos la nueva cantidad, sin concatenar al objeto moneyConversion
                    updateResult(moneyConversion2.getAmount(), 2);
                }
            }
        });


        //BOTON DE RESULT

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResult(" ", 1);
                String amount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount
                String toCurrency = moneyConversion2.getToCurrency(); // obtenemos el valor de la moneda a la que se quiere convertir
                if (amount.isEmpty() || toCurrency.isEmpty()) {  // falta cantidad o moneda
                    toastText(MainActivity.this, "Debes introducir una cantidad y seleccionar una moneda", "s");

                    return;
                }

                try {
                    moneyConversion2 = Convert.convertMoney(MainActivity.this, moneyConversion2); // lo convertimos
                    updateResult(moneyConversion2.getResultString(), 1); // mostramos el resultado, llamando a convert
                } catch (IllegalArgumentException e) {
                    toastText(MainActivity.this, e.getMessage(), "s");
                }
            }
        });
    }


    //-------------------------------FUNCIONES DE AYUDA / PARA EVITAR REPETIR CODIGO-------------------------------

    private void toastText(Context context, String text, String typeToast) {
        switch (typeToast) {
            case "s":
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                break;

            case "l":
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    // AÑADIR DIGITOS AL AMOUNT
    private void appendAmount(String d) {
        if (checkDecimals(moneyConversion2.getAmount())) { // Comprobamos que no tenga mas de X decimales
            toastText(this, "No puedes poner mas de dos decimales", "s");
            return;
        }
        moneyConversion2.setAmount(d, true); // añadimos la nueva cantidad concatenando al objeto moneyConversion
        updateResult(moneyConversion2.getAmount(), 2);
    }


    //COMPROBADOR DE DECIMALES
    public static boolean checkDecimals(String number) {
        int decimales = 2;

        if (number == null || number.isEmpty()) { // no hay nada en number
            return false;
        }

        if (number.contains(".")) { //miramos si tiene punto
            String decimals = number.substring(number.indexOf(".") + 1); //guardamos en decimals los caracteres que hay apartir del punto
            return decimals.length() >= decimales; //True o false si tiene mas de X decimales
        }

        return false; // no tiene punto, entonces no tiene decimales
    }


    //PARA IMPRIMIR EN LOS TEXTVIEWRESULT LOS RESULTADOS (DOS TEXT VIEW, EL DE € Y EL DE LA CONVERSION)
    public void updateResult(String result, int TextView) {
        switch (TextView) {
            case 1: //CONVERSION
                textViewResult.setText(result);
                break;

            case 2: //EURO
                textViewResult2.setText(result);
                break;
        }
    }


    //COMPROBADOR Y SETEADOR DE CURRENY
    private void askUserForToCurrency(String currency) {

        double rate;
        try {
            rate = Convert.getRate(MainActivity.this, currency); // Pedimos la curreny al getRate, la función propia devuelve -1 si es null
        } catch (convertException e) {
            toastText(MainActivity.this, e.getMessage(), "s");
            return;
        } catch (Exception e) {
            toastText(MainActivity.this, "Error inesperado al obtener la tasa de conversión", "s");
            return;
        }

        if (rate == -1) { // Condicion de no existe la rate

            EditText input = new EditText(MainActivity.this);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Nuevo factor de conversión")
                    .setMessage("Introduce el valor en euros de 1 " + currency)
                    .setView(input)
                    .setPositiveButton("Guardar", (dialog, which) -> {
                        try {
                            double newRate = Double.parseDouble(input.getText().toString()); // inotroducimos el input a un double al objeto moneyConversion
                            Convert.setRate(MainActivity.this, currency, newRate); // seteamos la rate al objeto moneyConversion
                            moneyConversion2.setToCurrency(currency); // la añadimos al objeto moneyConversion
                            toastText(MainActivity.this, ("Guardado: 1 " + currency + " = " + newRate + " €"), "s");
                        } catch (NumberFormatException e) {
                            toastText(MainActivity.this, "Número inválido", "s");
                        } catch (convertException e) {
                            toastText(MainActivity.this, e.getMessage(), "s");
                        } catch (Exception e) {
                            toastText(MainActivity.this, "Error inesperado al guardar la tasa de conversión", "s");
                        }
                    })
                    .show();
        } else { // Existe la rate
            moneyConversion2.setToCurrency(currency); // la añadimos al objeto moneyConversion
        }
    }

}