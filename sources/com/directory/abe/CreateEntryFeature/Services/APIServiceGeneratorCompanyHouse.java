package com.directory.abe.CreateEntryFeature.Services;

import android.util.Base64;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.auth.AUTH;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit.Builder;

public class APIServiceGeneratorCompanyHouse {
    public static String API_BASE_URL = "https://api.companieshouse.gov.uk/";
    private static Builder builder = new Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (!(username == null || password == null)) {
            final String basic = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), 2);
            httpClient.addInterceptor(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    return chain.proceed(original.newBuilder().header(AUTH.WWW_AUTH_RESP, basic).header("Accept", "applicaton/json").method(original.method(), original.body()).build());
                }
            });
        }
        return builder.client(httpClient.build()).build().create(serviceClass);
    }
}
