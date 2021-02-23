package ru.lalaev.vagif.mrmTeplokomInJava.ui.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.MainPresenter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.MainView;
import ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.ChatFragment;

import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.TEG;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mMainPresenter;

    public TimeAdapter mTimeAdapter;
    public NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_main);
        } catch (Exception e) {
            Log.e(TEG, "onCreateView", e);
            e.getStackTrace();
            throw e;
        }
        init();
    }

    private void init() {
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setupMenu();
        mMainPresenter.setWorkPlaneFragment();
    }

    private void setupMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.mBottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
    }

    @Override
    public void setWorkPlaneFragment(WorkPlaneFragment workPlaneFragment) {
        //
    }

    @Override
    public void setChatFragment(ChatFragment chatFragment) {
        FragmentTransaction mTransaction = this.getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.mFragmentContainer, chatFragment);
        mTransaction.commit();
    }

    public MainPresenter getmMainPresenter() {
        return mMainPresenter;
    }

    public void setmMainPresenter(MainPresenter mMainPresenter) {
        this.mMainPresenter = mMainPresenter;
    }

    public TimeAdapter getmTimeAdapter() {
        return mTimeAdapter;
    }

    public void setmTimeAdapter(TimeAdapter mTimeAdapter) {
        this.mTimeAdapter = mTimeAdapter;
    }

    public NavController getmNavController() {
        return mNavController;
    }

    public void setmNavController(NavController mNavController) {
        this.mNavController = mNavController;
    }
}
