package ru.lalaev.vagif.mrmTeplokomInJava.model.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, RetrofitModule.class})
public interface DataComponent {
    void inject(LoginPresenter loginPresenter);
}
