package ru.lalaev.vagif.mrmTeplokomInJava.presentation.view;


import android.support.v4.util.Pair;
import com.arellomobile.mvp.viewstate.strategy.*;
import java.util.List;

import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;

public interface WorkPlaneView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void createPlane(List<Pair<String, WorkClass>> works);
}
