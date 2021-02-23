package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ExtraReglamentAndZIP;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.SubWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ZIP;

public interface WorkView extends BaseView {

    /*@StateStrategyType(AddToEndSingleStrategy.class)
    void updateAdapter(List<SubWorkClass> mWorkList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateAdapterZIP(List<ZIP> ZIPList);*/

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateAdapter(List<ExtraReglamentAndZIP> extraReglamentAndZIPList);
}
