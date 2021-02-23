package ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.lalaev.vagif.mrmTeplokomInJava.model.server.RetrofitInterface;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.MainView;
import ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.ChatFragment;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    @Inject
    public RetrofitInterface mRetrofit;
    @Inject
    public SharedPreferences mSharedPreferences;
    public WorkPlaneFragment mWorkPlaneFragment;
    public ChatFragment mChatFragment;

    public void setWorkPlaneFragment() {
        this.mWorkPlaneFragment = new WorkPlaneFragment();
        this.getViewState().setWorkPlaneFragment(mWorkPlaneFragment);
    }

    public final void setChatFragment() {

    }

    @Override
    public void destroyView(MainView view) {
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

    public WorkPlaneFragment getmWorkPlaneFragment() {
        return mWorkPlaneFragment;
    }

    public void setmWorkPlaneFragment(WorkPlaneFragment mWorkPlaneFragment) {
        this.mWorkPlaneFragment = mWorkPlaneFragment;
    }

    public ChatFragment getmChatFragment() {
        return mChatFragment;
    }

    public void setmChatFragment(ChatFragment mChatFragment) {
        this.mChatFragment = mChatFragment;
    }
}
