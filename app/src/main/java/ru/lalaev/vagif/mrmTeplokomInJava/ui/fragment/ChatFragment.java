package ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.ChatView;

public class ChatFragment extends MvpAppCompatFragment implements ChatView {

    @InjectPresenter
    public ChatPresenter mChatPresenter;
    public TimeAdapter mTimeAdapter;
    private boolean isAlreadyCreated = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = this.getFragmentManager();
        Objects.requireNonNull(fragmentManager).addOnBackStackChangedListener(() -> {
            if (fragmentManager != null && fragmentManager.getBackStackEntryCount() == 0)
                this.isAlreadyCreated = true;
            });

        init();

        if (savedInstanceState == null && !isAlreadyCreated) {
            //спр
        }

    }

    private void init() {

    }

    public void showProgress() {
        //    (activity as MainActivity).showProgress()
    }

    public void hideProgress() {
        //  (activity as MainActivity).hideProgress()
    }

    public void showErrorToast(String errorMsg) {
        Toast.makeText((Context)this.getActivity(),errorMsg,  Toast.LENGTH_SHORT).show();
    }



    public ChatPresenter getmChatPresenter() {
        return mChatPresenter;
    }

    public void setmChatPresenter(ChatPresenter mChatPresenter) {
        this.mChatPresenter = mChatPresenter;
    }

    public TimeAdapter getmTimeAdapter() {
        return mTimeAdapter;
    }

    public void setmTimeAdapter(TimeAdapter mTimeAdapter) {
        this.mTimeAdapter = mTimeAdapter;
    }
}
