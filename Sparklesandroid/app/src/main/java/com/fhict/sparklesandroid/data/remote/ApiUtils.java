package com.fhict.sparklesandroid.data.remote;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://sparklesapi.azurewebsites.net/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
