package ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;

import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.RecomendedWorkView;

import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.AddRecommendationActivity.idResponse;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.AddRecommendationActivity.photoRecommendation1;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.AddRecommendationActivity.strDateForServer;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.AddRecommendationActivity.strRecommendation;

@InjectViewState
public class RecomendedWorkPresenter extends MvpPresenter<RecomendedWorkView> {
    @Inject
    public RetrofitInterface mRetrofit;
    @Inject
    public SharedPreferences mSharedPreferences;

    private List<AdditionalWorkClass> mAdditionalList;

    public RecomendedWorkPresenter() {
        this.mAdditionalList = new ArrayList<>();
    }

    void init() {
        //   ApplicationClass.dataComponent.inject(this)
    }

    public void addRecomendation() {

        mAdditionalList.add(new AdditionalWorkClass(idResponse,
                strRecommendation,
                photoRecommendation1,
                strDateForServer
        ));

        photoRecommendation1 = "";

        getViewState().updateRecomendation((ArrayList<AdditionalWorkClass>) mAdditionalList);

    }

    @Override
    public void destroyView(RecomendedWorkView view) {
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
