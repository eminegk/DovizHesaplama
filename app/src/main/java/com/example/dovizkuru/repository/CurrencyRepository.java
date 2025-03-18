

package com.example.dovizkuru.repository;  // Doğru paket adı olduğundan emin ol

import com.example.dovizkuru.model.ExchangeRate;
import com.example.dovizkuru.network.ApiClient;
import com.example.dovizkuru.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CurrencyRepository {
    private static final String API_KEY = "402f4d52855cd3a94af94363c8a2ebc1"; // API anahtarı
    private ApiService apiService;

    public CurrencyRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void fetchExchangeRates(final ExchangeRateCallback callback) {
        Call<ExchangeRate> call = apiService.getExchangeRates(API_KEY);
        call.enqueue(new Callback<ExchangeRate>() {
            @Override
            public void onResponse(Call<ExchangeRate> call, Response<ExchangeRate> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Veri alınamadı");
                }
            }

            @Override
            public void onFailure(Call<ExchangeRate> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ExchangeRateCallback {
        void onSuccess(ExchangeRate exchangeRate);
        void onFailure(String errorMessage);
    }
}
