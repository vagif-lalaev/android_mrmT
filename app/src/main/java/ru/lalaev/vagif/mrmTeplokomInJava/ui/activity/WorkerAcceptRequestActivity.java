package ru.lalaev.vagif.mrmTeplokomInJava.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ZIP;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitStatic;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkerAcceptRequestPresenter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.WorkerAcceptRequestView;
import ru.lalaev.vagif.mrmTeplokomInJava.utils.ApplicationClass;

import static ru.lalaev.vagif.mrmTeplokomInJava.model.di.RetrofitStatic.initRetrofit;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.RETROFIT;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.WorkPlaneFragment.IN_WORK_3;

public class WorkerAcceptRequestActivity extends MvpAppCompatActivity implements WorkerAcceptRequestView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @InjectPresenter
    public WorkerAcceptRequestPresenter mAcceptRequestPresenter;
    public TimeAdapter mTimeAdapter;
    public NavController mNavController;

    private StringBuilder resultDateAndTime = new StringBuilder();

    private TextView titleRequest;
    private TextView address;
    private TextView textDateAndTime;
    private TextView commentariesСlient;
    private TextView organizationNameTxt;
    public static int positionGlobal;
    private Button mStartWorkBtn, change_time, locationButton;
    private LinearLayout instrumentObzor;

    private List<ZIP> listZIP = new ArrayList<>();
    public static List<ZIP> tempListZIP;
    private FrameLayout telefon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_accept_request);

        titleRequest = findViewById(R.id.title_request);
        address = findViewById(R.id.address);
        textDateAndTime = findViewById(R.id.date_and_time);
        commentariesСlient = findViewById(R.id.commentaries_client);
        organizationNameTxt = findViewById(R.id.organizationNameTxt);
        mStartWorkBtn = findViewById(R.id.mStartWorkBtn);
        locationButton = findViewById(R.id.locationButton);
        change_time = findViewById(R.id.change_time);
        instrumentObzor = findViewById(R.id.instrumentObzor);
        telefon = findViewById(R.id.telefon);

        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        //обрабатываем полученный интент
        Intent intentStart = getIntent();
        if (intentStart.hasExtra(TimeAdapter.SELECT_GREEN_CARD_EXTRA)) {
            positionGlobal = intentStart.getIntExtra(TimeAdapter.SELECT_GREEN_CARD_EXTRA, 0);
            titleRequest.setText(WorkPlanePresenter.primaryRequestClass.get(positionGlobal).second.getTitle());
            address.setText(WorkPlanePresenter.primaryRequestClass.get(positionGlobal).second.getAddress());
            textDateAndTime.setText(WorkPlanePresenter.primaryRequestClass.get(positionGlobal).second.getDate());
            commentariesСlient.setText(WorkPlanePresenter.primaryRequestClass.get(positionGlobal).second.getComment());
            organizationNameTxt.setText(primaryRequestClass.get(positionGlobal).second.getClient());
        }

        findViewById(R.id.mBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        locationButton.setOnClickListener(v -> { //локация по адресу
            String geoUri = String.format("geo:0,0?q=%s", primaryRequestClass.get(positionGlobal).second.getAddress());
            Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

            if (geoIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(geoIntent);
            }
        });


        change_time.setOnClickListener(v -> {

            Toast toast = Toast.makeText(this, "Выберите дату", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 20);
            toast.show();

            openDate();
        });


        if (Objects.requireNonNull(primaryRequestClass.get(positionGlobal).second).getStatus() == IN_WORK_3) {
            TextView txt = findViewById(R.id.mStartWorkBtn);
            txt.setText("В работе...");
            mStartWorkBtn.setBackground(ApplicationClass.instanse.getDrawable(R.drawable.oranch_button));
        }

        mStartWorkBtn.setOnClickListener(v -> {
            Intent intent = new Intent((Context) WorkerAcceptRequestActivity.this, WorkerRequsetActivity.class);
            statusInWork(); //смена статуса
            startActivity(intent);
        });

        instrumentObzor.setOnClickListener(v -> {
            toolReview();
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadingListZIP(listZIP);
                tempListZIP = listZIP;
            }
        }).start();

    }

    private void makePhoneCall(String number) { //звонок клиенту
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(WorkerAcceptRequestActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WorkerAcceptRequestActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 77);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(WorkerAcceptRequestActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void toolReview() { //обзор инструментов котр.потребуються для работы
        String[] listZIPinfo = new String[listZIP.size()];
        for (int i = 0; i < listZIP.size(); i++) {
            listZIPinfo[i] = listZIP.get(i).getTitle();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ЗИП которые Вам потребуются!")
//                .setItems(listZIP.toArray(new String[0]), (dialog, which) -> {
                .setItems(listZIPinfo, (dialog, which) -> {
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                });

        if (listZIP.size() > 0)
            builder.show();
        else
            Toast.makeText(this, "В данной работе ЗИП не требуется!", Toast.LENGTH_SHORT).show();
    }

    private List<ZIP> loadingListZIP(List<ZIP> list) { //получаем список ЗИП из базы
        Call<List<ZIP>> call = RetrofitStatic.initRetrofit().getListZIP(
                (int) primaryRequestClass.get(positionGlobal).second.getID(),
                "16"
        );
        List<ZIP> tempList;
        try {
            tempList = call.execute().body();
            for (ZIP zip : tempList) {
                list.add(new ZIP(zip.getID(), zip.getTitle()));
                Log.d(RETROFIT, "Получаем список ЗИП " + zip.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private void statusInWork() { //cмен.статус при начин. работ
        Call<WorkClass> call = initRetrofit().putLoadStatus(Objects.requireNonNull(primaryRequestClass.get(positionGlobal).second).getID(), IN_WORK_3);
        call.enqueue(new Callback<WorkClass>() {
            @Override
            public void onResponse(Call<WorkClass> call, Response<WorkClass> response) {
            }

            @Override
            public void onFailure(Call<WorkClass> call, Throwable t) {
            }
        });
    }


    private void openDate() { //выбор даты
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //получаем результат даты
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        resultDateAndTime.setLength(0);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        resultDateAndTime.append(currentDateString)
                .delete(resultDateAndTime.length() - 2, resultDateAndTime.length());

        Toast toast = Toast.makeText(this, "Выберите время", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();

        openTime();

    }

    private void openTime() {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) { // получаем результат времени
        resultDateAndTime.append(hourOfDay).append(":").append(minute);
        textDateAndTime.setText(resultDateAndTime.toString());
        String dateResult = DateConvert.getMyDateConvert(resultDateAndTime.toString(), "d MMM yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
//        Log.d(TEG, "ДО конвертации получаем это: " + resultDateAndTime.toString());
//        Log.d(TEG, "После конвертации получаем это: " + dateResult);
        initPutDate(dateResult); //put в базу
    }


    private void initPutDate(String date) { //мен.дату в базе
        Log.d(RETROFIT, primaryRequestClass.get(positionGlobal).second.getDate());
        Call<WorkClass> call = initRetrofit().putLoadDate(primaryRequestClass.get(positionGlobal).second.getID(), date);
        call.enqueue(new Callback<WorkClass>() {
            @Override
            public void onResponse(Call<WorkClass> call, Response<WorkClass> response) {
                if (!response.isSuccessful()) {
                    Log.d(RETROFIT, "Дата не изменилась!");
                }
                if (response.isSuccessful()) {
                    Log.d(RETROFIT, "Дата изменилась");
                }
            }

            @Override
            public void onFailure(Call<WorkClass> call, Throwable t) {
                Log.d(RETROFIT, "ОШИБКА ДАТЫ: " + t.getMessage());

            }
        });

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
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    public WorkerAcceptRequestPresenter getmAcceptRequestPresenter() {
        return mAcceptRequestPresenter;
    }

    public void setmAcceptRequestPresenter(WorkerAcceptRequestPresenter mAcceptRequestPresenter) {
        this.mAcceptRequestPresenter = mAcceptRequestPresenter;
    }

    public List<ZIP> getListZIP() {
        return listZIP;
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
