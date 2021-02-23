package ru.lalaev.vagif.mrmTeplokomInJava.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.AddAdditionalPresenter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.BaseView;

import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.RETROFIT;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.WorkerAcceptRequestActivity.positionGlobal;
import static ru.lalaev.vagif.mrmTeplokomInJava.utils.UploadPhotoRetrofit.uploadTextResultIdForImageAddWork;

public class AddAdditionalActivity extends MvpAppCompatActivity implements BaseView {

    @InjectPresenter
    public AddAdditionalPresenter mAddAdditionalPresenter;

    public AdditionalWorkPresenter additionalWorkPresenter;

    public TimeAdapter mTimeAdapter;
    private boolean isAlreadyCreated;
    public NavController mNavController;
    private AdditionalWorkClass additionalWorkClass = new AdditionalWorkClass();
    //    public static List<AdditionalWorkClass> listWork_id_add = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;

    private ImageView photoCamera;
    private ImageView photoResult_1, photoResult_2, photoResult_3, photoResult_4;

    boolean isPhotoOk1, isPhotoOk2, isPhotoOk3, isPhotoOk4;

    final int REQUEST_CODE_PHOTO1 = 5;
    final int REQUEST_CODE_PHOTO2 = 6;
    final int REQUEST_CODE_PHOTO3 = 7;
    final int REQUEST_CODE_PHOTO4 = 8;

    @Nullable
    public static String photoAddWork;
    public static String textAddWork;
    public static String idResponse;

    @org.jetbrains.annotations.Nullable
    private String tempFileName1;
    @org.jetbrains.annotations.Nullable
    private String tempFileName2;
    @org.jetbrains.annotations.Nullable
    private String tempFileName3;
    @org.jetbrains.annotations.Nullable
    private String tempFileName4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_additional);

        photoCamera = findViewById(R.id.photoCamera);
        photoResult_1 = findViewById(R.id.photoResult_1);
        photoResult_2 = findViewById(R.id.photoResult_2);
        photoResult_3 = findViewById(R.id.photoResult_3);
        photoResult_4 = findViewById(R.id.photoResult_4);

        autoCompleteTextView = findViewById(R.id.text_work);

        init();
    }

    private void init() {
        this.findViewById(R.id.mCancelBtn).setOnClickListener(v -> {
            this.setResult(Activity.RESULT_CANCELED);

            this.finish();
        });

        this.findViewById(R.id.mConfirmBtn).setOnClickListener(v -> {
            this.setResult(Activity.RESULT_OK);

            textAddWork = autoCompleteTextView.getText().toString().trim();

            if (textAddWork.equals("")) {
                Toast.makeText(this, "Введите наименования работы!", Toast.LENGTH_SHORT).show();
            } else {

                if (tempFileName1 != null) {
                    photoAddWork = tempFileName1;
                } else photoAddWork = "";

                MyThreadForEventAdd thread = new MyThreadForEventAdd();
                thread.start(); //1птк
                MyThreadPhotoEvent photoEvent = new MyThreadPhotoEvent();
                try {
                    thread.join();
                    photoEvent.start(); //2птк
                    Log.d(RETROFIT, "получаем id ADD Works" + idResponse);

                    /*if (!photoEvent.isAlive()) { //todo удаляем файлы после их отправки (тут подумать как верно очищать остатки от доп работ (3 file))
                        if (tempFileName2 != null)
                            new File(tempFileName2).delete();
                        if (tempFileName3 != null)
                            new File(tempFileName3).delete();
                        if (tempFileName4 != null)
                            new File(tempFileName4).delete();
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(RETROFIT, "init: 1" + textAddWork);

                this.finish();
            }


        });

        photoCamera.setOnClickListener((View.OnClickListener) v -> {
            UploadPhotoRetrofit.verifyStoragePermissions(this);
            initPhotoCamera();
        });

        imageView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initPhotoCamera() {
        try {
            if (!isPhotoOk1)
                originalTakeActionPhoto(REQUEST_CODE_PHOTO1);
            else if (!isPhotoOk2)
                originalTakeActionPhoto(REQUEST_CODE_PHOTO2);
            else if (!isPhotoOk3)
                originalTakeActionPhoto(REQUEST_CODE_PHOTO3);
            else if (!isPhotoOk4)
                originalTakeActionPhoto(REQUEST_CODE_PHOTO4);
            else
                Toast.makeText(this, "Больше нельзя снять фото!", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Ваше устройство не поддерживает съемку!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO1:
                    tempFileName1 = PhotoNavigator.fileName;

                    PhotoNavigator.miniaturePicasso100x100(tempFileName1, photoResult_1);

                    isPhotoOk1 = true;
                    break;

                case REQUEST_CODE_PHOTO2:
                    tempFileName2 = PhotoNavigator.fileName;

                    PhotoNavigator.miniaturePicasso100x100(tempFileName2, photoResult_2);

                    isPhotoOk2 = true;
                    break;

                case REQUEST_CODE_PHOTO3:
                    tempFileName3 = PhotoNavigator.fileName;

                    PhotoNavigator.miniaturePicasso100x100(tempFileName3, photoResult_3);

                    isPhotoOk3 = true;
                    break;

                case REQUEST_CODE_PHOTO4:
                    tempFileName4 = PhotoNavigator.fileName;

                    PhotoNavigator.miniaturePicasso100x100(tempFileName4, photoResult_4);

                    isPhotoOk4 = true;
                    break;
            }
        }
    }

    private void imageView() { //об.наж.кнп.отснят.матер.
        photoResult_1.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageActivityWork.class);
            intent.putExtra("tempFileName1", tempFileName1);
            startActivity(intent);
        });

        photoResult_2.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageActivityWork.class);
            intent.putExtra("tempFileName2", tempFileName2);
            startActivity(intent);
        });

        photoResult_3.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageActivityWork.class);
            intent.putExtra("tempFileName3", tempFileName3);
            startActivity(intent);
        });

        photoResult_4.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageActivityWork.class);
            intent.putExtra("tempFileName4", tempFileName4);
            startActivity(intent);
        });
    }


    private void originalTakeActionPhoto(int REQUEST_CODE_PHOTO) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            photoFile = PhotoNavigator.createPhotoFileAndName(this);
            if (photoFile != null) {
                Uri outputFileUri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.provider",
                        photoFile
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        }
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorToast(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();

    }

    public String getTempFileName1() {
        return tempFileName1;
    }


    public class MyThreadForEventAdd extends Thread {
        @Override
        public void run() {
            super.run();
            idResponse = uploadTextResultIdForImageAddWork(
                    primaryRequestClass.get(positionGlobal).second.getID(),
                    "5",
                    textAddWork
            );

            additionalWorkClass.setIdEvent(idResponse);
//            listWork_id_add.add(additionalWorkClass);
        }
    }

    public class MyThreadPhotoEvent extends Thread {
        @Override
        public void run() {
            super.run();
            if (idResponse != null) {
                Log.i(RETROFIT, "IIIIIDDDDDD thread " + idResponse);

                if (tempFileName1 != null)
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName1,
                            "100",
                            Long.parseLong(idResponse),
                            getApplicationContext(),
                            "EW_"
                    );
                if (tempFileName2 != null)
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName2,
                            "100",
                            Long.parseLong(idResponse),
                            getApplicationContext(),
                            "EW_"
                    );
                if (tempFileName3 != null)
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName3,
                            "100",
                            Long.parseLong(idResponse),
                            getApplicationContext(),
                            "EW_"
                    );
                if (tempFileName4 != null)
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName4,
                            "100",
                            Long.parseLong(idResponse),
                            getApplicationContext(),
                            "EW_"
                    );
            }
        }
    }

    public AdditionalWorkPresenter getAdditionalWorkPresenter() {
        return additionalWorkPresenter;
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
