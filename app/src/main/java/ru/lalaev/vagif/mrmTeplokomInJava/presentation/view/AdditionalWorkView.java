package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;

public interface AdditionalWorkView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateAdditionals(ArrayList<AdditionalWorkClass> additionalList);
}
