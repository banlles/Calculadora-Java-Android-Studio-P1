package com.example.calculadorap1.Utilities;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.calculadorap1.exceptions.convertException;
import com.example.calculadorap1.exceptions.utilitiesException;

import java.util.function.Consumer;

public class Utilities {

    //TOAST TEXT
    public static void toastText(Context context, String text, String typeToast) {
        try {
            switch (typeToast) { // switch para elegir el tipo de toast
                case "s":
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    break;

                case "l":
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (NullPointerException e) {
            throw new utilitiesException("Falta información para mostrar el mensaje");
        } catch (WindowManager.BadTokenException e) {
            throw new utilitiesException("No se puede mostrar el mensaje en este momento");
        } catch (IllegalStateException e) {
            throw new utilitiesException("No se puede mostrar el mensaje ahora");
        } catch (Exception e) {
            throw new utilitiesException("Ha ocurrido un problema mostrando el mensaje");
        }
    }


    // INPUT DIALOG
    public static void showInputDialog(Context context, String title, String message, Consumer<String> onResult) {
        try {
            EditText input = new EditText(context);

            new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setView(input)
                    .setPositiveButton("Guardar", (dialog, which) -> {
                        String text = input.getText().toString(); // Obtener el texto introducido
                        if (onResult != null) {  // Verificar que onResult no es null
                            onResult.accept(text); // Llamar al consumidor con el texto
                        }
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();

        } catch (NullPointerException e) {
            throw new utilitiesException("No se ha podido abrir la ventana");
        } catch (WindowManager.BadTokenException e) {
            throw new utilitiesException("La ventana no se puede mostrar");
        } catch (IllegalStateException e) {
            throw new utilitiesException("La ventana no se puede mostrar ahora");
        } catch (Exception e) {
            throw new utilitiesException("Ha ocurrido un problema mostrando la ventana");
        }
    }

    // UPDATE TEXTVIEWS
    private static TextView tvConversion;
    private static TextView tvEuro;

    public static void init(TextView conversion, TextView euro) {
        try {
            tvConversion = conversion;
            tvEuro = euro;
        } catch (Exception e) {
            throw new utilitiesException("No se pudieron preparar los textos");
        }
    }

    // PONER TEXTO EN TEXTVIEW
    public static void updateResult(String result, int TextView) {
        try {
            switch (TextView) { // switch para elegir el TextView
                case 1:
                    tvConversion.setText(result);
                    break;
                case 2:
                    tvEuro.setText(result);
                    break;
                default:
                    throw new utilitiesException("Opción no válida");
            }
        } catch (NullPointerException e) {
            throw new utilitiesException("No hay texto disponible para mostrar");
        } catch (Exception e) {
            throw new utilitiesException("No se pudo actualizar el texto");
        }
    }

    // COMPROBAR DECIMALES
    public static boolean checkDecimals(String number) {
        try {
            int decimales = 2;

            if (number == null || number.isEmpty()) { // comprueba si el número es nulo o vacío
                return false;
            }

            if (number.contains(".")) { // comprueba si el número tiene decimales
                String decimals = number.substring(number.indexOf(".") + 1); // obtiene la parte decimal del número
                return decimals.length() >= decimales; // comprueba si tiene al menos 2 decimales
            }

            return false; // si no tiene decimales, devuelve false
        } catch (Exception e) {
            throw new utilitiesException("No se pudo comprobar los decimales");
        }
    }

    // ACTUALIZAR BOTONES SELECCIONADOS
    public static void updateSelectedButton(Button[] buttons, Button selected) {
        try {
            for (Button b : buttons) {
                b.setSelected(false); // desmarca todos
            }
            if (selected != null) {
                selected.setSelected(true); // marca solo si no es null (HECHO PARA EL BOTON CE)
            }
        } catch (Exception e) {
            if (selected != null) {
                Toast.makeText(selected.getContext(), "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

