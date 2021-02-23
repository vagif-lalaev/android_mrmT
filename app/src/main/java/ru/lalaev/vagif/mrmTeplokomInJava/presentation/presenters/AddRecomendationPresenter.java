package ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitStatic;
import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.AddRecomendationView;

import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.RETROFIT;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.WorkerAcceptRequestActivity.positionGlobal;

@InjectViewState
public class AddRecomendationPresenter extends MvpPresenter<AddRecomendationView> {
    @Inject
    public RetrofitInterface mRetrofit;
    @Inject
    public SharedPreferences mSharedPreferences;

    private List<AdditionalWorkClass> mAdditionalList;

    public AddRecomendationPresenter() {
        this.mAdditionalList = new ArrayList<>();
    }

    void init() {
        //   ApplicationClass.dataComponent.inject(this)
        }

        public void addAddition() {
        mAdditionalList.add(new AdditionalWorkClass(
                "Нет связи, не работает М1 или М2 и др.. Поставить приборы после поверка;"));

            /*  viewState.updateAdditionals(mAdditionalList)*/

        }

    public void addRecommendation(Context context, String str) { //добавляем рекомендацию в базу //добавляем в базу рекомендацию текста, не более 2000смв. (было сделано в начале на втором дизайне, сейчас это не надо)
        Log.d(RETROFIT, "Статус действующий: " + primaryRequestClass.get(positionGlobal).second.getStatus());
        Call<WorkClass> call = RetrofitStatic.initRetrofit().putLoadRecommendation(primaryRequestClass.get(positionGlobal).second.getID(), str);
        call.enqueue(new Callback<WorkClass>() {
            @Override
            public void onResponse(Call<WorkClass> call, Response<WorkClass> response) {
                if (!response.isSuccessful()) {
                    Log.d(RETROFIT, "Рекомендация не добавлена");
                }
                if (response.isSuccessful()) {
                    Log.d(RETROFIT, "Рекомендация добавлена!");
                    Toast.makeText(context, "Рекомендация добавлена!", Toast.LENGTH_SHORT).show();
                }

                Log.d(RETROFIT, "Статус после: " + primaryRequestClass.get(positionGlobal).second.getStatus());
            }

            @Override
            public void onFailure(Call<WorkClass> call, Throwable t) {
                Log.d(RETROFIT, "ОШИБКА: " + t.getMessage());
            }
        });
    }

    @Override
    public void destroyView(AddRecomendationView view) {
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
