package ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;

@InjectViewState
public class AddAdditionalPresenter extends MvpPresenter<AddAdditionalView> {
    @Inject
    public RetrofitInterface mRetrofit;
    @Inject
    public SharedPreferences mSharedPreferences;

    private List<AdditionalWorkClass> mAdditionalList;

    public AddAdditionalPresenter() {
        this.mAdditionalList = new ArrayList<>();
    }

    void init() {
        //   ApplicationClass.dataComponent.inject(this)
    }

    public void addAddition() {
        mAdditionalList.add(new AdditionalWorkClass(
                "Нет связи, не работает М1 или М2 и др.. Поставить приборы после поверка, еще 3;"));

//        getViewState().updateAdditionals((ArrayList<AdditionalWorkClass>) mAdditionalList);

    }

    @Override
    public void destroyView(AddAdditionalView view) {
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

    public ArrayList<AdditionalWorkClass> getmAdditionalList() {
        return (ArrayList<AdditionalWorkClass>) this.mAdditionalList;
    }

    public void setmAdditionalList(ArrayList<AdditionalWorkClass> mAdditionalList) {
        this.mAdditionalList = mAdditionalList;
    }

}
