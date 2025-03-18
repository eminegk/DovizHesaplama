package com.example.dovizkuru;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dovizkuru.model.ExchangeRate;
import com.example.dovizkuru.repository.CurrencyRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom, spinnerTo;
    private EditText amountInput;
    private Button convertButton;
    private TextView resultText;
    private CurrencyRepository currencyRepository;
    private Map<String, Double> rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI bileşenlerini tanımla
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        amountInput = findViewById(R.id.amountInput);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        currencyRepository = new CurrencyRepository();

        // API'den veri çek
        currencyRepository.fetchExchangeRates(new CurrencyRepository.ExchangeRateCallback() {
            @Override
            public void onSuccess(ExchangeRate exchangeRate) {
                rates = exchangeRate.getRates();
                setupSpinners(new ArrayList<>(rates.keySet()));
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Hata: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        // Butona tıklanınca çeviri işlemi başlat
        convertButton.setOnClickListener(v -> convertCurrency());
    }

    private void setupSpinners(List<String> currencyList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    private void convertCurrency() {
        String fromCurrency = spinnerFrom.getSelectedItem().toString();
        String toCurrency = spinnerTo.getSelectedItem().toString();
        String amountStr = amountInput.getText().toString();

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Lütfen bir miktar girin", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        if (rates != null && rates.containsKey(fromCurrency) && rates.containsKey(toCurrency)) {
            double fromRate = rates.get(fromCurrency);
            double toRate = rates.get(toCurrency);

            // USD bazlı dönüşüm (USD üzerinden diğer para birimlerine geçiş yapıyoruz)
            double convertedAmount = (amount / fromRate) * toRate;

            resultText.setText(String.format("Sonuç: %.2f %s", convertedAmount, toCurrency));
        } else {
            Toast.makeText(this, "Döviz kuru bilgisi alınamadı", Toast.LENGTH_SHORT).show();
        }
    }
}
