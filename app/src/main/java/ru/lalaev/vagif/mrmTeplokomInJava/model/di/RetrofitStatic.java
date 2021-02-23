package ru.lalaev.vagif.mrmTeplokomInJava.model.di;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;

import static ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitModule.provideGson;
import static ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitModule.provideRetrofit;

public class RetrofitStatic {

    public static RetrofitInterface initRetrofit() {
        RetrofitInterface retrofitInterface;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request origin = chain.request();

                Request.Builder builder = origin.newBuilder()
                        .header("Authorization", "JWT " + new LoginPresenter().getTokenSharedPreferences());

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofitInterface = provideRetrofit(provideGson(), okHttpClient);
        return retrofitInterface;
    }

}
