package ru.lalaev.vagif.mrmTeplokomInJava;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;

import java.io.IOException;

import ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitModule;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;

public class ApiTest {
    SharedPreferences sharedPreferences;
    LoginPresenter mLoginPresenter2;
    Context context;

    private final RetrofitInterface api = RetrofitModule.provideRetrofit(RetrofitModule.provideGson(),
            RetrofitModule.provideOkhttpClient(RetrofitModule.provideHttpCache(context),
                    RetrofitModule.providesHostSelectionInterceptor()));

    @Test
    public void testCurrentWeather() throws IOException {

        /*Call<RequestClass> call = (Call<RequestClass>) api.loadPlanDate("2019-11-20", "JWT " + sharedPreferences.getString("token", ""));
        mLoginPresenter2.auth2("teplocom@worker.ru", "qwerty123");


        Response<RequestClass> response = call.execute();

        Assert.assertTrue(response.isSuccessful());

        RequestClass body = response.body(); //получим тело ответа

        Assert.assertNotNull(body); // проверка на null

        //получаем ответ из тела
        String name = body.getClient();
        Assert.assertNotNull(name);
        Assert.assertTrue(name.length() > 0);*/

    }


}
