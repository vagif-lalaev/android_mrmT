package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;
import java.util.List;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;

public interface RecomendedWorkView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateRecomendation(ArrayList<AdditionalWorkClass> recomendationList);
}
