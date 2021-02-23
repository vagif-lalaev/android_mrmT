package ru.lalaev.vagif.mrmTeplokomInJava.model.di;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lalaev.vagif.mrmTeplokomInJava.BuildConfig;
import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;
import ru.lalaev.vagif.mrmTeplokomInJava.utils.ApplicationClass;

@Module
public class RetrofitModule {
    private static Context context;
    private final ApplicationClass applicationData;

    public RetrofitModule(Context context, ApplicationClass applicationData) {
        this.context = context;
        this.applicationData = applicationData;
    }

    @Provides
    @Singleton
    public static Cache provideHttpCache(Context context) {
        long cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    public static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public static HostSelectionInterceptor providesHostSelectionInterceptor() {
        return new HostSelectionInterceptor();
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkhttpClient(Cache cache, HostSelectionInterceptor hostSelectionInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            client.addInterceptor(interceptor)
                    .connectTimeout(10L, TimeUnit.SECONDS)
                    .readTimeout(10L, TimeUnit.SECONDS)
                    .cache(cache);
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return client.build();
    }

    @Provides
    @Singleton
    public static RetrofitInterface provideRetrofit(Gson moshy, OkHttpClient okHttpClient) {
        return new retrofit2.Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(moshy))
                .baseUrl(
                        context.getResources().getString(R.string.BASE_URL)
                )
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
                .create(RetrofitInterface.class);
    }
}
