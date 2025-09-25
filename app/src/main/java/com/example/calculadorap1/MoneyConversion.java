package com.example.calculadorap1;

public class MoneyConversion {
    private String amount = "";
    private String fromCurrency = "EUR"; // valor por defecto
    private String toCurrency =  "";
    private String result; // puede ser null


    public MoneyConversion() {
    }

    public MoneyConversion(String amount, String fromCurrency, String toCurrency) {
        setAmount(amount, false);
        setFromCurrency(fromCurrency);
        setToCurrency(toCurrency);
        setResult(null);
    }

    // Getters y setter

    public String getAmount() {
        return String.valueOf(amount);
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public String getResult() {
        return result;
    }

    public String getResultString() {
        return String.valueOf(result);
    }

    public void setAmount(String amount, boolean concat) {
        try {
            if (concat) {
                this.amount += amount;
            } else {
                this.amount = amount;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor introducido no es un número válido");
        }
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
