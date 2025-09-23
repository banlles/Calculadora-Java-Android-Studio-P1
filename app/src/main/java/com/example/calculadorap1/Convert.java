package com.example.calculadorap1;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Convert {

    private static final String FILE_NAME = "conversion_rates.json";

    // Leer JSON del archivo
    private static JSONObject loadRates(Context context) {
        try {
            FileInputStream conversion_rates = context.openFileInput(FILE_NAME);  //Si no existe lanza excepción
            byte[] buffer = new byte[conversion_rates.available()];
            conversion_rates.read(buffer);
            conversion_rates.close();
            return new JSONObject(new String(buffer)); //Si no es JSON lanza excepción
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }


    // Guardar JSON en archivo
    private static void saveRates(Context context, JSONObject rates) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(rates.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar
    public static void setRate(Context context, String currency, double rate) {
        try {
            JSONObject rates = loadRates(context);
            rates.put(currency, rate); // guardar en minúsculas
            saveRates(context, rates);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Leer
    public static double getRate(Context context, String currency) {
        JSONObject rates = loadRates(context);
        if (rates.has(currency)) {
            try {
                return rates.getDouble(currency);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void clearRates(Context context) {
        context.deleteFile(FILE_NAME);
    }

    // Convertir entre monedas
    public static String convert(Context context, MoneyConversion conversion) {
        double fromRate = getRate(context, conversion.getFromCurrency());
        double toRate = getRate(context, conversion.getToCurrency());

        if (fromRate == -1 || toRate == -1) {
            throw new IllegalArgumentException("Falta el factor de conversión");
        }

        double inEuros = conversion.getAmount() * fromRate;
        return String.valueOf(inEuros / toRate);
    }
}

