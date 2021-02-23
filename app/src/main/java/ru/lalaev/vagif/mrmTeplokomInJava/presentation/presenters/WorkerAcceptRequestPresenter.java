package ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.WorkerAcceptRequestView;

@InjectViewState
public class WorkerAcceptRequestPresenter extends MvpPresenter<WorkerAcceptRequestView> {

    @Inject
    public RetrofitInterface mRetrofit;
    @Inject
    public SharedPreferences mSharedPreferences;

    private void init(){
        //  ApplicationClass.dataComponent.inject(this)
    }

    @Override
    public void destroyView(WorkerAcceptRequestView view) {
        super.destroyView(view);
    }

    public RetrofitInterface getmRetrofit() {
        return mRetrofit;
    }

    public void setmRetrofit(RetrofitInterface mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }
}
