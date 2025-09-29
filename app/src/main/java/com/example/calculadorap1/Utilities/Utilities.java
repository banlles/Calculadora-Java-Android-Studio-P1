package com.example.calculadorap1.Utilities;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.calculadorap1.exceptions.utilitiesException;

import java.util.function.Consumer;

public class Utilities {

    public static void toastText(Context context, String text, String typeToast) {
        try {
            switch (typeToast) {
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


    public static void showInputDialog(Context context, String title, String message, Consumer<String> onResult) {
        try {
            EditText input = new EditText(context);

            new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setView(input)
                    .setPositiveButton("Guardar", (dialog, which) -> {
                        String text = input.getText().toString();
                        if (onResult != null) {
                            onResult.accept(text);
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

    public static void updateResult(String result, int TextView) {
        try {
            switch (TextView) {
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

    public static boolean checkDecimals(String number) {
        try {
            int decimales = 2;

            if (number == null || number.isEmpty()) {
                return false;
            }

            if (number.contains(".")) {
                String decimals = number.substring(number.indexOf(".") + 1);
                return decimals.length() >= decimales;
            }

            return false;
        } catch (Exception e) {
            throw new utilitiesException("No se pudo comprobar los decimales");
        }
    }

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

