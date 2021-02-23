package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.ViewPagerAdapter;

public interface WorkerRequestView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initViewPager(ViewPagerAdapter mViewPagerAdapter);
}
