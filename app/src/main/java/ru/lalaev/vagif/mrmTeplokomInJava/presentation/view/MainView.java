package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.ChatFragment;

public interface MainView extends MvpView {

    //MOXY стратегии

  /*  @StateStrategyType(SkipStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()*/


   /* @StateStrategyType(AddToEndSingleStrategy::class)
    fun createPlane(works : ArrayList<Pair<String,WorkClass?>>)*/

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setWorkPlaneFragment(WorkPlaneFragment workPlaneFragment);


    @StateStrategyType(OneExecutionStateStrategy.class)
    void setChatFragment(ChatFragment chatFragment);

}
