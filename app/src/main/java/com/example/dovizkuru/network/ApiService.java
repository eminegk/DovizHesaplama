package com.example.dovizkuru.network;



import com.example.dovizkuru.model.ExchangeRate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("latest")  // API endpoint'i
    Call<ExchangeRate> getExchangeRates(@Query("access_key") String apiKey);
}
