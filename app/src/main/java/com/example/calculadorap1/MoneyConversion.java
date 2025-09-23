package com.example.calculadorap1;

public class MoneyConversion {
    private double amount;
    private String fromCurrency;
    private String toCurrency;
    private Double result; // puede ser null

    public MoneyConversion(String amount, String fromCurrency, String toCurrency) {
       setAmount(amount);
         setFromCurrency(fromCurrency);
            setToCurrency(toCurrency);
            setResult(null);
    }

    // Getters y setters
    public double getAmount() {
        return amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public Double getResult() {
        return result;
    }

    public void setAmount(String amount) {
        try {
            double value = Double.parseDouble(amount);

            this.amount = value;

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

    public void setResult(Double result) {
        this.result = result;
    }
}
