package com.example.calculadorap1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculadorap1.Utilities.Convert;
import com.example.calculadorap1.Utilities.Utilities;
import com.example.calculadorap1.exceptions.convertException;
import com.example.calculadorap1.exceptions.utilitiesException;

public class MainActivity extends AppCompatActivity {

    MoneyConversion moneyConversion2 = new MoneyConversion();

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

        TextView textViewResultConversion = findViewById(R.id.textViewResult);
        TextView textViewResultEuro = findViewById(R.id.textViewResult2);
        // para usar el updateResult en utilities
        Utilities.init(textViewResultConversion, textViewResultEuro);


        //----------------------------------FUNCIONAMIENTO DE LOS BOTONES-------------------------------


        //BOTONES DE LOS NUMEROS
        Button[] numberButtons = {
                buttonNumber0, buttonNumber1, buttonNumber2,
                buttonNumber3, buttonNumber4, buttonNumber5,
                buttonNumber6, buttonNumber7, buttonNumber8, buttonNumber9
        };

        for (int i = 0; i < numberButtons.length; i++) {
            final String digit = String.valueOf(i);
            numberButtons[i].setOnClickListener(v -> {
                try {
                    appendAmount(digit); // función para añadir el número al amount
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (IllegalArgumentException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            });
        }



        // BOTON DEL PUNTO DECIMAL

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String currentAmount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount

                    if (!currentAmount.contains(".")) { // no hay un punto
                        if (currentAmount.isEmpty()) { // esta vacio
                            moneyConversion2.setAmount("0", false); // añadimos un 0 sin concatenar al objeto moneyConversion
                        }
                        moneyConversion2.setAmount(".", true); // añadimos el . concatenando al objeto moneyConversion
                        Utilities.updateResult(moneyConversion2.getAmount(), 2);
                    } else {
                        Utilities.toastText(MainActivity.this, "Ya has introducido un punto decimal", "s");
                    }
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            }
        });


        //BOTONES DE LAS MONEDAS
        Button[] currencyButtons = {buttonLliures, buttonDollar, buttonYen, buttonYuan};
        String[] currencies = {"LLIURES", "DOLLAR", "YEN", "YUAN"};

        for (int i = 0; i < currencyButtons.length; i++) {
            final int index = i;
            currencyButtons[i].setOnClickListener(v -> {
                try {
                    Utilities.updateSelectedButton(currencyButtons, currencyButtons[index]); // actualiza el botón seleccionado
                    askUserForToCurrency(currencies[index]); // pide y setea la moneda
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (convertException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (IllegalArgumentException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            });
        }


        //BOTON DE BORRAR

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //seteamos todo en 0 o nada
                try {
                    moneyConversion2.setAmount(" ", false);
                    moneyConversion2.setAmount(" ", false);
                    Utilities.updateResult("0", 1);
                    Utilities.updateResult("0", 2);
                    Utilities.updateSelectedButton(currencyButtons, null);
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            }
        });


        //BOTON DE TIRAR UN NUMERO PARA ATRAS

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String currentAmount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount

                    if (!currentAmount.isEmpty()) { // amount no esta vacio
                        String newAmount = currentAmount.substring(0, currentAmount.length() - 1); // elimina el ultimo caracter empezando por 0 y luego a la longitud le resta 1

                        moneyConversion2.setAmount(newAmount, false); // añadimos la nueva cantidad, sin concatenar al objeto moneyConversion
                        Utilities.updateResult(moneyConversion2.getAmount(), 2);
                    }
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            }
        });


        //BOTON DE RESULT

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Utilities.updateResult(" ", 1);
                    String amount = moneyConversion2.getAmount(); // obtenemos el valor actual de amount
                    String toCurrency = moneyConversion2.getToCurrency(); // obtenemos el valor de la moneda a la que se quiere convertir
                    if (amount.isEmpty() || toCurrency.isEmpty()) {  // falta cantidad o moneda
                        Utilities.toastText(MainActivity.this, "Debes introducir una cantidad y seleccionar una moneda", "s");
                        return;
                    }

                    moneyConversion2 = Convert.convertMoney(MainActivity.this, moneyConversion2); // lo convertimos
                    Utilities.updateResult(moneyConversion2.getResultString(), 1); // mostramos el resultado, llamando a convert

                } catch (convertException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (IllegalArgumentException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (utilitiesException e) {
                    Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                } catch (Exception e) {
                    Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                }
            }
        });
    }


    //-------------------------------FUNCIONES EXTRA-------------------------------


    // AÑADIR DIGITOS AL AMOUNT
    private void appendAmount(String d) {
        try {
            if (Utilities.checkDecimals(moneyConversion2.getAmount())) { // Comprobamos que no tenga mas de X decimales
                Utilities.toastText(this, "No puedes poner mas de dos decimales", "s");
                return;
            }
            moneyConversion2.setAmount(d, true); // añadimos la nueva cantidad concatenando al objeto moneyConversion
            Utilities.updateResult(moneyConversion2.getAmount(), 2);
        } catch (utilitiesException e) {
            Utilities.toastText(this, e.getMessage(), "s");
        } catch (Exception e) {
            Utilities.toastText(this, "Ha ocurrido un problema", "s");
        }
    }


    //COMPROBADOR Y SETEADOR DE CURRENY
    private void askUserForToCurrency(String currency) {

        double rate;
        try {
            rate = Convert.getRate(MainActivity.this, currency); // Pedimos la curreny al getRate, la función propia devuelve -1 si es null
        } catch (convertException e) {
            Utilities.toastText(MainActivity.this, e.getMessage(), "s");
            return;
        } catch (Exception e) {
            Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
            return;
        }

        if (rate == -1) { // Condicion de no existe la rate

            try {
                Utilities.showInputDialog(MainActivity.this, "Nuevo factor de conversión", "Introduce el valor en euros de 1 " + currency, inputText -> {
                            try {
                                double newRate = Double.parseDouble(inputText); // intentamos parsear el valor introducido
                                Convert.setRate(MainActivity.this, currency, newRate); // lo guardamos en el JSON
                                moneyConversion2.setToCurrency(currency); // la añadimos al objeto moneyConversion
                                Utilities.toastText(MainActivity.this, "Guardado: 1 " + currency + " = " + newRate + " €", "s");
                            } catch (NumberFormatException e) {
                                Utilities.toastText(MainActivity.this, "Número inválido", "s");
                            } catch (convertException e) {
                                Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                            } catch (utilitiesException e) {
                                Utilities.toastText(MainActivity.this, e.getMessage(), "s");
                            } catch (Exception e) {
                                Utilities.toastText(MainActivity.this, "Ha ocurrido un problema", "s");
                            }
                        }
                );
            } catch (utilitiesException e) {
                Utilities.toastText(MainActivity.this, e.getMessage(), "s");
            } catch (Exception e) {
                Utilities.toastText(MainActivity.this, "La ventana no se puede mostrar", "s");
            }
        } else { // Existe la rate
            moneyConversion2.setToCurrency(currency); // la añadimos al objeto moneyConversion
        }
    }

}