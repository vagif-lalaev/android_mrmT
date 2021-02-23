package ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ExtraReglamentAndZIP;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.SubWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitStatic;

import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.RETROFIT;

public class WorkAdapter extends RecyclerView.Adapter {

    public static String TESTreglament = "TESTreglament";
    private List<ExtraReglamentAndZIP> extraReglamentAndZIPList;
    private final PublishSubject<ExtraReglamentAndZIP> onClickFieldSubject;
    private final PublishSubject<ExtraReglamentAndZIP> onClickChatSubject;
    private final PublishSubject<ExtraReglamentAndZIP> onClickTimeSetSubject;

    public WorkAdapter() {
        this.onClickFieldSubject = PublishSubject.create();
        this.onClickChatSubject = PublishSubject.create();
        this.onClickTimeSetSubject = PublishSubject.create();
        this.extraReglamentAndZIPList = new ArrayList<>();
    }

    public final Observable<ExtraReglamentAndZIP> getPositionFieldClicks() {
        return this.onClickFieldSubject.hide();
    }

    public final Observable<ExtraReglamentAndZIP> getPositionChatClicks() {
        return this.onClickChatSubject.hide();
    }

    public final Observable<ExtraReglamentAndZIP> getPositionTimeSetClicks() {
        return this.onClickTimeSetSubject.hide();
    }

    public void updateCounts(List<ExtraReglamentAndZIP> numbers) {
        this.extraReglamentAndZIPList = numbers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int positionTyp) {
        if (extraReglamentAndZIPList.get(positionTyp).getTitleZIP().equals("-")) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View itemLayoutView;
        switch (viewType) {
            case 0:
                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_criteria, parent, false);
                vh = new ViewHolderReg((ViewGroup) itemLayoutView);
                break;
            case 1:
                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_criteria_zip, parent, false);
                vh = new ViewHolderZIP((ViewGroup) itemLayoutView);
                break;
        }

        return vh;
    }

    boolean is = false;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        boolean statusVar;
        if (extraReglamentAndZIPList != null) {
            ExtraReglamentAndZIP unit = this.extraReglamentAndZIPList.get(position);
            switch (getItemViewType(position)) {
                case 0: //регл.раб.
                    ViewHolderReg reg0 = (ViewHolderReg) holder;

                    if (position == 0)
                        reg0.visionWork.setVisibility(View.VISIBLE);
                    else
                        reg0.visionWork.setVisibility(View.GONE);

                    reg0.titleReglament.setText(unit.getTitleReglament());

                    statusVar = unit.getStatusReglament() == 1; //если unit.getStatusReglament() == 1 то isStatus = true
                    reg0.isStatus.setChecked(statusVar);
                    break;

                case 1: //ЗИП
                    ViewHolderZIP zip1 = (ViewHolderZIP) holder;

                    zip1.ZIPTitle.setText(unit.getTitleZIP());

                    if (position == 0 ||
                            extraReglamentAndZIPList.get(position - 1).getTitleZIP().equals("-") &&
                                    extraReglamentAndZIPList.get(position).getTitleReglament().equals("-"))
                        zip1.visionWork.setVisibility(View.VISIBLE);
                    else
                        zip1.visionWork.setVisibility(View.GONE);

                    zip1.count.setText(String.valueOf(unit.getCostZIP()));
                    break;
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) { //here we avoid memory leaks
        onClickChatSubject.onComplete();
        onClickFieldSubject.onComplete();
        onClickTimeSetSubject.onComplete();
    }

    @Override
    public int getItemCount() {
        return extraReglamentAndZIPList.size();
    }

    public class ViewHolderReg extends RecyclerView.ViewHolder {

        TextView titleReglament;
        Switch isStatus;
        ConstraintLayout visionWork;

        public ViewHolderReg(ViewGroup view) {
            super(view);
            titleReglament = view.findViewById(R.id.mCriteriaTxt);
            isStatus = view.findViewById(R.id.mCriteriaSwitch);
            visionWork = view.findViewById(R.id.visionWork);

            isStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int positionReglament = getAdapterPosition();
                    if (isChecked) {
                        extraReglamentAndZIPList.get(positionReglament).setStatusReglament((byte) 1);
                    } else {
                        extraReglamentAndZIPList.get(positionReglament).setStatusReglament((byte) 0);
                    }
                    updateReglametIsStatus(extraReglamentAndZIPList.get(positionReglament).getIdReglament(),
                            "14", extraReglamentAndZIPList.get(positionReglament).getStatusReglament()); //отправка статусов в базу
                }
            });
        }
    }

    public class ViewHolderZIP extends RecyclerView.ViewHolder { //ViewHolder для ЗИП

        TextView ZIPTitle;
        Button down;
        Button up;
        TextView count;
        ConstraintLayout visionWork;

        public ViewHolderZIP(ViewGroup view) {
            super(view);
            ZIPTitle = view.findViewById(R.id.ZIPTitle);
            down = view.findViewById(R.id.down);
            up = view.findViewById(R.id.up);
            count = view.findViewById(R.id.count);
            visionWork = view.findViewById(R.id.visionWork);

            down.setOnClickListener(v -> {
                downCheck(extraReglamentAndZIPList, getAdapterPosition());
                notifyDataSetChanged();
            });

            up.setOnClickListener(v -> {
                upCheck(extraReglamentAndZIPList, getAdapterPosition(), view.getContext());
                notifyDataSetChanged();
            });
        }
    }

    private void upCheck(List<ExtraReglamentAndZIP> list, int position, Context context) {
        if (list.get(position).getCostZIP() < 99) {
            list.get(position).setCostZIP(list.get(position).getCostZIP() + 1);
            Log.d(TESTreglament, "ViewHolderZIP: " + list.get(position).getCostZIP());
        } else {
            Toast.makeText(context, R.string.limit, Toast.LENGTH_SHORT).show();
        }
    }

    private void downCheck(List<ExtraReglamentAndZIP> list, int position) { //уменьшаем на 1
        if (list.get(position).getCostZIP() > 0) {
            list.get(position).setCostZIP(list.get(position).getCostZIP() - 1);
            Log.d(TESTreglament, "ViewHolderZIP: " + list.get(position).getCostZIP());
        } else {
//            Toast.makeText(context, R.string.limit, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateReglametIsStatus(int idReglament, String req, byte isStatus) {
        RetrofitStatic.initRetrofit().setReglamentWorksIsStatus(idReglament, req, isStatus)
                .enqueue(new Callback<SubWorkClass>() {
                    @Override
                    public void onResponse(Call<SubWorkClass> call, Response<SubWorkClass> response) {
                        if (!response.isSuccessful())
                            Log.d(RETROFIT, "Статус регламентных работы не изменился, ошибка!");
                        if (response.isSuccessful())
                            Log.d(RETROFIT, "Статус регламентных работы успешно изменился!");
                    }

                    @Override
                    public void onFailure(Call<SubWorkClass> call, Throwable t) {
                        t.getMessage();
                        Log.d(RETROFIT, t.getMessage());
                    }
                });
    }
}
