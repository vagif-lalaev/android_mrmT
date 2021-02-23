package ru.lalaev.vagif.mrmTeplokomInJava.utils;

import android.app.Application;
import android.content.Context;

import ru.lalaev.vagif.mrmTeplokomInJava.model.di.DaggerDataComponent;
import ru.lalaev.vagif.mrmTeplokomInJava.model.di.DataComponent;
import ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitModule;

public class ApplicationClass extends Application {

    public static ApplicationClass instanse;
    public static DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instanse = this;
        this.initializeDaggerGraph();
    }

    public final void initializeDaggerGraph() {
        dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule((Context) this))
                .retrofitModule(new RetrofitModule((Context) this, this))
                .build();
    }

    public static DataComponent getComponent() {
        return dataComponent;
    }
}
