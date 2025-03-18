package com.example.dovizkuru.model;


import java.util.Map;

public class ExchangeRate {
    private String base;
    private String date;
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}
