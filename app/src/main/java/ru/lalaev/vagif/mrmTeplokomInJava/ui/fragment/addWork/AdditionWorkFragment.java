package ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.addWork;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.AdditionalWorkView;
import ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.AddAdditionalActivity;

import static android.content.Context.MODE_PRIVATE;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.WorkerAcceptRequestActivity.positionGlobal;

public class AdditionWorkFragment extends MvpAppCompatFragment implements AdditionalWorkView {

    @InjectPresenter
    public AdditionalWorkPresenter mAdditionalWorkPresenter;
    public TimeAdapter mTimeAdapter;
    private boolean isAlreadyCreated;
    public AdditionalWorkAdapter mAdditionalWorkAdapter;

    RecyclerView mAdditionalRecycler;
    Button mAdditionBtn;
    public static final String ADD_WORK = "add_work_";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_additional_work, container, false);

        mAdditionalRecycler = view.findViewById(R.id.mAdditionalRecycler);
        mAdditionBtn = view.findViewById(R.id.mAdditionBtn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFragmentManager().addOnBackStackChangedListener(() -> {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                isAlreadyCreated = true;
            }
        });

        init();

        if (savedInstanceState == null && !this.isAlreadyCreated) {
            //  mAdditionalWorkPresen ter.initWorks()
        }

        loadDataPreferences(ADD_WORK);
        updateAdditionals(mAdditionalWorkPresenter.getmAdditionalList());
    }


    private void init() {
        mAdditionalWorkAdapter = new AdditionalWorkAdapter();

        mAdditionalRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdditionalRecycler.setAdapter(mAdditionalWorkAdapter);

        mAdditionBtn.setOnClickListener((View.OnClickListener) (v) -> {
            Intent intent = new Intent(getActivity(), AddAdditionalActivity.class);
            startActivityForResult(intent, 1);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mAdditionalWorkPresenter.addAddition();
            saveDataPreferences(mAdditionalWorkPresenter.getmAdditionalList(), ADD_WORK, getContext());
        }


    }


    @Override
    public void updateAdditionals(ArrayList<AdditionalWorkClass> additionalList) {
        mAdditionalWorkAdapter.updateCounts(additionalList);
    }

    @Override
    public void showProgress() {
        //    (activity as MainActivity).showProgress()
    }

    @Override
    public void hideProgress() {
        //  (activity as MainActivity).hideProgress()
    }

    @Override
    public void showErrorToast(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();

    }


    public static void saveDataPreferences(List<AdditionalWorkClass> list, String nameSetting, Context context) { //сохраняем список добавленных событий
        String fileName = nameSetting + primaryRequestClass.get(positionGlobal).second.getID();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("list", json);
        editor.apply();
    }

    private void loadDataPreferences(String nameSetting) { //загружаем если есть список добавленных событий
        String fileName = nameSetting + primaryRequestClass.get(positionGlobal).second.getID();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(fileName, MODE_PRIVATE);
        if (sharedPreferences.contains("list")) { //решение ошибки при входе в работу "Начать работу"
            Gson gson = new Gson();
            String json = sharedPreferences.getString("list", null);
            Type type = new TypeToken<ArrayList<AdditionalWorkClass>>() {
            }.getType();
            mAdditionalWorkPresenter.setmAdditionalList(gson.fromJson(json, type));
        }
    }

    public AdditionalWorkPresenter getmAdditionalWorkPresenter() {
        return mAdditionalWorkPresenter;
    }

    public void setmAdditionalWorkPresenter(AdditionalWorkPresenter mAdditionalWorkPresenter) {
        this.mAdditionalWorkPresenter = mAdditionalWorkPresenter;
    }

    public TimeAdapter getmTimeAdapter() {
        return mTimeAdapter;
    }

    public void setmTimeAdapter(TimeAdapter mTimeAdapter) {
        this.mTimeAdapter = mTimeAdapter;
    }

    public AdditionalWorkAdapter getmAdditionalWorkAdapter() {
        return mAdditionalWorkAdapter;
    }

    public void setmAdditionalWorkAdapter(AdditionalWorkAdapter mAdditionalWorkAdapter) {
        this.mAdditionalWorkAdapter = mAdditionalWorkAdapter;
    }

}
