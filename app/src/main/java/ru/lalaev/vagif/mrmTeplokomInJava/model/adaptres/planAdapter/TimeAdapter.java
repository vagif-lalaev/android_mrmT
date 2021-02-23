package ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.WorkerAcceptRequestActivity;
import ru.lalaev.vagif.mrmTeplokomInJava.utils.ApplicationClass;

import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.TEG;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.EXPRESS_WORK_2;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.EXTRA_WORK_1;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.IN_WORK_3;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.NOT_OK_0;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.OK_WORK_200;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.PLAN_WORK_0;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.REJECT_WORK_4;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> implements Filterable {
    public List<Pair<String, WorkClass>> mUnitList;
    public static List<Pair<String, WorkClass>> mUnitListFull;
    public static List<Pair<String, WorkClass>> statusList;
    private PublishSubject<WorkClass> onClickFieldSubject;
    private PublishSubject<WorkClass> onClickChatSubject;
    private PublishSubject<WorkClass> onClickTimeSetSubject;

    View workView;
    Context context;

    private WorkClass workClass;

    public static String SELECT_GREEN_CARD_EXTRA = "select_card";


    public View getWorkView() {
        return workView;
    }

    public TimeAdapter(Context context) {
        this.onClickFieldSubject = PublishSubject.create();
        this.onClickChatSubject = PublishSubject.create();
        this.onClickTimeSetSubject = PublishSubject.create();
        this.context = context;
        mUnitList = new ArrayList<>();
    }

    public final Observable<WorkClass> getPositionFieldClicks() {
        return onClickFieldSubject.hide();
    }

    public final Observable<WorkClass> getPositionChatClicks() {
        return onClickChatSubject.hide();
    }

    /*   public final Observable<WorkClass> getPositionTimeSetClicks() {
           return onClickTimeSetSubject.hide();
       }
   */
    public void updateCounts(List<Pair<String, WorkClass>> numbers) {
        this.mUnitList = numbers;
        mUnitListFull = new ArrayList<>(numbers);
        statusList = new ArrayList<>(numbers);
        notifyDataSetChanged();
    }

    /**
     * создаем новую ячейку
     */
    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_time, parent, false);
//        ViewGroup v = (ViewGroup) view;

//        LayoutInflater inflater = (LayoutInflater) ApplicationClass.instanse.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
//        workView = inflater.inflate(R.layout.inflater_green_card, null);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_green_card_3, parent, false);
        ViewGroup v = (ViewGroup) view;


        return new ViewHolder(v);
    }


    Pair<String, WorkClass> unit;

    /**
     * привязываем данные к ячейке
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.ViewHolder holder, int position) {

        unit = mUnitList.get(position);

//        ((TextView) holder.mViewGroup.findViewById(R.id.mTimeTxt)).setText((CharSequence) unit.first);

        if (unit.second != null) {

            workClass = unit.second;

            holder.textTitle.setText(workClass.getTitle());
            holder.mOrganizationNameTxt.setText(workClass.getClient());
            holder.mLocationTxt.setText(workClass.getAddress());
            holder.timeForPlan.setText(workClass.getDate());

            if ((int) workClass.getStatus() == NOT_OK_0) {
                switch ((int) workClass.getKind()) {
                    case PLAN_WORK_0:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_gry_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.plannedFragment));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorStatusPlanGry));
                        break;
                    case EXTRA_WORK_1:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_blue_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.additionalFragment));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorStatusForFragmentBlue));
                        break;
                    case EXPRESS_WORK_2:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_red_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.urgentFragment));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorStatusForFragmentRed));
                        break;
                }
            } else {
                switch ((int) workClass.getStatus()) {
                    case IN_WORK_3:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_oranch_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.inWork));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorStatusForFragmentOranch));
                        break;
                    case REJECT_WORK_4:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_black_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.rejected));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorStatusForFragmentRedNo));
                        break;
                    case OK_WORK_200:
//                        holder.mColorFrame.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.round_status_green_fragment));
                        holder.StatusTextFragment.setText(ApplicationClass.instanse.getText(R.string.additionalOK));
                        holder.StatusTextFragment.setTextColor(ApplicationClass.instanse.getColor(R.color.colorGreenNew));
                        holder.textTitle.setPaintFlags(holder.textTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); //зачерк.текст
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * возвращаем количество элементов списка
     */
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mUnitList.size();
    }

    /**
     * View holder
     * Хранит информацию о ячейке
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //        private ViewGroup mViewGroup;
        TextView textTitle;
        TextView mOrganizationNameTxt;
        TextView mLocationTxt;
        //        FrameLayout mColorFrame;
        TextView StatusTextFragment;
        TextView timeForPlan;


        public ViewHolder(ViewGroup mViewGroup) {
            super(mViewGroup);
//            this.mViewGroup = mViewGroup;
            textTitle = mViewGroup.findViewById(R.id.textTitle);
            mOrganizationNameTxt = mViewGroup.findViewById(R.id.mOrganizationNameTxt);
            mLocationTxt = mViewGroup.findViewById(R.id.mLocationTxt);
//            mColorFrame = mViewGroup.findViewById(R.id.mColorFrame);
            StatusTextFragment = mViewGroup.findViewById(R.id.StatusTextFragment);
            timeForPlan = mViewGroup.findViewById(R.id.timeForPlan);


            mViewGroup.setOnClickListener(new View.OnClickListener() { //карточка
                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition();
                    Log.d(TEG, "onClick: " + positionIndex);

                    //для вызова интента при нажатии карточки WorkerAcceptRequestActivity
                    Intent workerAcceptRequestActivity = new Intent(context, WorkerAcceptRequestActivity.class);
                    workerAcceptRequestActivity.putExtra(SELECT_GREEN_CARD_EXTRA, positionIndex);

                    Log.d(TEG, "onClick: до условия: " + primaryRequestClass.get(positionIndex).second.getStatus());
                    if (Objects.requireNonNull(primaryRequestClass.get(positionIndex).second).getStatus() == OK_WORK_200) {
                        Log.d(TEG, "onClick: после условия: " + primaryRequestClass.get(positionIndex).second.getStatus());
                        Toast.makeText(context, "Работа выполнена!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TEG, "onClick: условие не выполнено: " + primaryRequestClass.get(positionIndex).second.getStatus());
                        context.startActivity(workerAcceptRequestActivity);
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        onClickChatSubject.onComplete(); //here we avoid memory leaks
        onClickFieldSubject.onComplete();
        onClickTimeSetSubject.onComplete();
    }


    @Override
    public Filter getFilter() { //get поиска
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pair<String, WorkClass>> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mUnitListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pair<String, WorkClass> pair : mUnitListFull) {
                    if (pair.second.getAddress().toLowerCase().contains(filterPattern)) //поиск по location
                        filteredList.add(pair);
                    if (pair.second.getClient().toLowerCase().contains(filterPattern)) //по organization
                        filteredList.add(pair);
                    if (pair.second.getTitle().toLowerCase().contains(filterPattern)) //по названию
                        filteredList.add(pair);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUnitList.clear();
            mUnitList.addAll((Collection<Pair<String, WorkClass>>) results.values);
            notifyDataSetChanged();
        }
    };
}
