package com.example.calculadorap1;

public class NumberCount {

    public static boolean hasMoreThanTwoDecimals(String number) {
        if (number == null || number.isEmpty()) {
            return false; // no hay nada que comprobar
        }

        if (number.contains(".")) {
            String decimals = number.substring(number.indexOf(".") + 1);
            return decimals.length() > 2;
        }

        return false; // no tiene punto, entonces no tiene decimales
    }

}
