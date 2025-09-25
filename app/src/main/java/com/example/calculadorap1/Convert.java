package com.example.calculadorap1;

import android.content.Context;

import com.example.calculadorap1.exceptions.convertException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Convert {

    private static final String FILE_NAME = "conversion_rates.json";

    // Leer JSON del archivo
    private static JSONObject loadRates(Context context) {
        try {
            FileInputStream conversion_rates = context.openFileInput(FILE_NAME);  //Si no existe lanza excepción
            byte[] buffer = new byte[conversion_rates.available()]; // available() devuelve el tamaño del archivo en bytes y crea un array de ese tamaño de bytes
            conversion_rates.read(buffer); // lee el archivo y lo guarda en el array de bytes
            conversion_rates.close();
            return new JSONObject(new String(buffer)); //si no es JSON lanza excepción
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }


    // Guardar JSON en archivo
    private static void saveRates(Context context, JSONObject rates) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE); // crea el archivo o lo sobrescribe si ya existe
            fos.write(rates.toString().getBytes()); // escibre el JSON en el archivo con las nuevas tasas
            fos.close();
        } catch (IOException e) {
            throw new convertException("Error al guardar las tasas de conversión");
        }
    }

    // Guardar
    public static void setRate(Context context, String currency, double rate) {
        try {
            JSONObject rates = loadRates(context); // carga las tasas existentes
            rates.put(currency, rate); // añade o actualiza la tasa de la moneda (el put por dentro, el mismo gestiona si existe o no)
            saveRates(context, rates); // guarda las tasas actualizadas
        } catch (JSONException e) {
            throw new convertException("Error al setear la tasa de conversión");
        }
    }

    // Leer
    public static double getRate(Context context, String currency) {
        JSONObject rates = loadRates(context); // carga las tasas existentes
        if (rates.has(currency)) { // comprueba si existe la tasa para la moneda
            try {
                return rates.getDouble(currency); // devuelve la tasa de conversión
            } catch (JSONException e) {
                throw new convertException("Error al obtener la tasa de conversión");
            }
        }
        return -1;
    }

    public static void clearRates(Context context) {
        context.deleteFile(FILE_NAME);
    }

    // Convertir entre monedas
    public static MoneyConversion convertMoney(Context context, MoneyConversion moneyConversion) {
        double euros = Double.parseDouble(moneyConversion.getAmount()); // tasa de la moneda EURO
        double toRate = getRate(context, moneyConversion.getToCurrency()); // tasa de la moneda que sea

        if (euros == -1 || toRate == -1) {
            throw new convertException("Tasa de conversión no disponible para una de las monedas");
        }

        moneyConversion.setResult(String.valueOf(euros / toRate));
        return moneyConversion;
    }
}

