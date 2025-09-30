package com.example.calculadorap1.Utilities;

import android.content.Context;

import com.example.calculadorap1.MoneyConversion;
import com.example.calculadorap1.exceptions.convertException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Convert {

    private static final String FILE_NAME = "conversion_rates.json";

    // LEER JSON DEL ARCHIVO
    private static JSONObject loadRates(Context context) {
        try {
            FileInputStream conversion_rates = context.openFileInput(FILE_NAME);  // si no existe lanza excepción
            byte[] buffer = new byte[conversion_rates.available()]; // available() devuelve el tamaño del archivo en bytes y crea un array de ese tamaño de bytes
            conversion_rates.read(buffer); // lee el archivo y lo guarda en el array de bytes
            conversion_rates.close();
            return new JSONObject(new String(buffer)); // si no es JSON lanza excepción
        } catch (IOException | JSONException e) {
            return new JSONObject();
        } catch (Exception e) {
            throw new convertException("No se pudieron cargar las tasas");
        }

    }

    // GUARDAR JSON EN EL ARCHIVO
    private static void saveRates(Context context, JSONObject rates) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE); // crea el archivo o lo sobrescribe si ya existe
            fos.write(rates.toString().getBytes()); // escibre el JSON en el archivo con las nuevas tasas
            fos.close();
        } catch (IOException e) {
            throw new convertException("No se pudieron guardar las tasas");
        } catch (Exception e) {
            throw new convertException("Ha ocurrido un problema guardando las tasas");
        }

    }

    // GUARDAR O ACTUALIZAR
    public static void setRate(Context context, String currency, double rate) {
        try {
            JSONObject rates = loadRates(context); // carga las tasas existentes
            rates.put(currency, rate); // añade o actualiza la tasa de la moneda (el put por dentro, el mismo gestiona si existe o no)
            saveRates(context, rates); // guarda las tasas actualizadas
        } catch (JSONException e) {
            throw new convertException("No se pudo guardar la tasa");
        } catch (Exception e) {
            throw new convertException("Ha ocurrido un problema guardando la tasa");
        }
    }

    // LEER UNA TASA
    public static double getRate(Context context, String currency) {
        try {
            JSONObject rates = loadRates(context); // carga las tasas existentes
            if (rates.has(currency)) { // comprueba si existe la tasa para la moneda
                return rates.getDouble(currency); // devuelve la tasa de conversión
            }
            return -1;
        } catch (JSONException e) {
            throw new convertException("No se pudo obtener la tasa");
        } catch (Exception e) {
            throw new convertException("Ha ocurrido un problema obteniendo la tasa");
        }
    }

    // BORRAR TODAS LAS TASAS
    public static void clearRates(Context context) {
        try {
            context.deleteFile(FILE_NAME);
        } catch (Exception e) {
            throw new convertException("No se pudieron borrar las tasas");
        }
    }

    // CONVERTIR DINERO
    public static MoneyConversion convertMoney(Context context, MoneyConversion moneyConversion) {
        try {
            double euros = Double.parseDouble(moneyConversion.getAmount()); // tasa de la moneda EURO
            double toRate = getRate(context, moneyConversion.getToCurrency()); // tasa de la moneda que sea

            if (euros == -1 || toRate == -1) { // si no existe la tasa
                throw new convertException("Tasa no disponible");
            }

            moneyConversion.setResult(String.valueOf(euros / toRate));
            return moneyConversion;
        } catch (NumberFormatException e) {
            throw new convertException("La cantidad no es válida");
        } catch (Exception e) {
            throw new convertException("No se pudo hacer la conversión");
        }
    }
}
