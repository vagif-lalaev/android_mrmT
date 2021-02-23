package ru.lalaev.vagif.mrmTeplokomInJava.ui.fragment.addWork;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.File;
import java.util.List;
import java.util.Objects;

import ru.lalaev.vagif.mrmTeplokomInJava.R;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ExtraReglamentAndZIP;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.WorkAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres.planAdapter.TimeAdapter;
import ru.lalaev.vagif.mrmTeplokomInJava.presentation.view.WorkView;

import static android.app.Activity.RESULT_OK;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.RETROFIT;
import static ru.lalaev.vagif.mrmTeplokomInJava.presentation.presenters.WorkPlanePresenter.primaryRequestClass;
import static ru.lalaev.vagif.mrmTeplokomInJava.ui.activity.WorkerAcceptRequestActivity.positionGlobal;

public class WorkFragment extends MvpAppCompatFragment implements WorkView {

    @InjectPresenter
    public WorkPresenter mWorkPresenter;

    public TimeAdapter mTimeAdapter;
    public WorkAdapter mWorkAdapter;
    private boolean isAlreadyCreated;

    private RecyclerView mWorkRecycler;

    private ImageView photoCamera;
    private ImageView photoResult_1, photoResult_2, photoResult_3, photoResult_4;

    boolean isPhotoOk1, isPhotoOk2, isPhotoOk3, isPhotoOk4;

    @org.jetbrains.annotations.Nullable
    private String tempFileName1, tempFileName2, tempFileName3, tempFileName4;

    final int REQUEST_CODE_PHOTO1 = 1;
    final int REQUEST_CODE_PHOTO2 = 2;
    final int REQUEST_CODE_PHOTO3 = 3;
    final int REQUEST_CODE_PHOTO4 = 4;

    private FrameLayout deletePhoto1, deletePhoto2, deletePhoto3, deletePhoto4;

    public static final String MAKE_WORK = "make_work_";

    public SharedPreferences pathFoto1, pathFoto2, pathFoto3, pathFoto4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);

        mWorkRecycler = view.findViewById(R.id.mWorkRecycler);

        photoCamera = view.findViewById(R.id.photoCamera);
        photoResult_1 = view.findViewById(R.id.photoResult_1);
        photoResult_2 = view.findViewById(R.id.photoResult_2);
        photoResult_3 = view.findViewById(R.id.photoResult_3);
        photoResult_4 = view.findViewById(R.id.photoResult_4);

        deletePhoto1 = view.findViewById(R.id.deletePhoto1);
        deletePhoto2 = view.findViewById(R.id.deletePhoto2);
        deletePhoto3 = view.findViewById(R.id.deletePhoto3);
        deletePhoto4 = view.findViewById(R.id.deletePhoto4);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentManager().addOnBackStackChangedListener(() -> {
            if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() == 0)
                isAlreadyCreated = true;
        });

        loadingPhotoForPreferences(pathFoto1, REQUEST_CODE_PHOTO1);
        loadingPhotoForPreferences(pathFoto2, REQUEST_CODE_PHOTO2);
        loadingPhotoForPreferences(pathFoto3, REQUEST_CODE_PHOTO3);
        loadingPhotoForPreferences(pathFoto4, REQUEST_CODE_PHOTO4);

        cacheMiniature();

        init();

        deletePhotoImage();

        if (savedInstanceState == null && !isAlreadyCreated)
            mWorkPresenter.initWorks();

    }

    void init() {
        mWorkAdapter = new WorkAdapter();

        mWorkRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mWorkRecycler.setAdapter(mWorkAdapter);


        photoCamera.setOnClickListener((View.OnClickListener) v -> {
            UploadPhotoRetrofit.verifyStoragePermissions(Objects.requireNonNull(getActivity())); //разрш.
            initPhotoCamera();
        });

        imageView();
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
                Toast.makeText(getContext(), "Больше нельзя снять фото!", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Ваше устройство не поддерживает съемку!";
            Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO1:
                    tempFileName1 = PhotoNavigator.fileName;
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName1,
                            "99",
                            primaryRequestClass.get(positionGlobal).second.getID(),
                            getActivity(),
                            "W_"
                    );

                    PhotoNavigator.miniaturePicasso100x100(tempFileName1, photoResult_1);

                    isPhotoOk1 = true;
                    deletePhoto1.setVisibility(View.VISIBLE);

                    savePhotoForPreferences(pathFoto1, tempFileName1, REQUEST_CODE_PHOTO1);

                    break;

                case REQUEST_CODE_PHOTO2:
                    tempFileName2 = PhotoNavigator.fileName;
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName2,
                            "99",
                            primaryRequestClass.get(positionGlobal).second.getID(),
                            getActivity(),
                            "W_"
                    );

                    PhotoNavigator.miniaturePicasso100x100(tempFileName2, photoResult_2);

                    isPhotoOk2 = true;
                    deletePhoto2.setVisibility(View.VISIBLE);

                    savePhotoForPreferences(pathFoto2, tempFileName2, REQUEST_CODE_PHOTO2);

                    break;

                case REQUEST_CODE_PHOTO3:
                    tempFileName3 = PhotoNavigator.fileName;
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName3,
                            "99",
                            primaryRequestClass.get(positionGlobal).second.getID(),
                            getActivity(),
                            "W_"
                    );

                    PhotoNavigator.miniaturePicasso100x100(tempFileName3, photoResult_3);

                    isPhotoOk3 = true;
                    deletePhoto3.setVisibility(View.VISIBLE);

                    savePhotoForPreferences(pathFoto3, tempFileName3, REQUEST_CODE_PHOTO3);

                    break;

                case REQUEST_CODE_PHOTO4:
                    tempFileName4 = PhotoNavigator.fileName;
                    UploadPhotoRetrofit.uploadFilePhoto(
                            tempFileName4,
                            "99",
                            primaryRequestClass.get(positionGlobal).second.getID(),
                            getActivity(),
                            "W_"
                    );

                    PhotoNavigator.miniaturePicasso100x100(tempFileName4, photoResult_4);

                    isPhotoOk4 = true;
                    deletePhoto4.setVisibility(View.VISIBLE);

                    savePhotoForPreferences(pathFoto4, tempFileName4, REQUEST_CODE_PHOTO4);

                    break;
            }
        }
    }

    private void imageView() {
        photoResult_1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ImageActivityWork.class);
            intent.putExtra("tempFileName1", tempFileName1);
            startActivity(intent);
        });

        photoResult_2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ImageActivityWork.class);
            intent.putExtra("tempFileName2", tempFileName2);
            startActivity(intent);
        });

        photoResult_3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ImageActivityWork.class);
            intent.putExtra("tempFileName3", tempFileName3);
            startActivity(intent);
        });

        photoResult_4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ImageActivityWork.class);
            intent.putExtra("tempFileName4", tempFileName4);
            startActivity(intent);
        });
    }


    private void originalTakeActionPhoto(int REQUEST_CODE_PHOTO) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            File photoFile = null;
            photoFile = PhotoNavigator.createPhotoFileAndName(getContext());
            if (photoFile != null) {
                Uri outputFileUri = FileProvider.getUriForFile(
                        Objects.requireNonNull(getContext()),
                        "com.example.android.provider",
                        photoFile
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        }
    }

    private void deletePhotoImage() {
        deletePhoto1.setOnClickListener(v -> {
            new AlertDialog.Builder(this.getContext()).setMessage("Удалить фото?").setNegativeButton("НЕТ", (dialog, which) -> {
            }).setPositiveButton("ДА", (dialog, which) -> {
                new File(tempFileName1).delete();
                tempFileName1 = null;
                removePreference(pathFoto1, REQUEST_CODE_PHOTO1);
                isPhotoOk1 = false;
                photoResult_1.setImageBitmap(null);
                deletePhoto1.setVisibility(View.GONE);
            }).create().show();
        });
        deletePhoto2.setOnClickListener(v -> {
            new AlertDialog.Builder(this.getContext()).setMessage("Удалить фото?").setNegativeButton("НЕТ", (dialog, which) -> {
            }).setPositiveButton("ДА", (dialog, which) -> {
                new File(tempFileName2).delete();
                tempFileName2 = null;
                removePreference(pathFoto2, REQUEST_CODE_PHOTO2);
                isPhotoOk2 = false;
                photoResult_2.setImageBitmap(null);
                deletePhoto2.setVisibility(View.GONE);
            }).create().show();
        });
        deletePhoto3.setOnClickListener(v -> {
            new AlertDialog.Builder(this.getContext()).setMessage("Удалить фото?").setNegativeButton("НЕТ", (dialog, which) -> {
            }).setPositiveButton("ДА", (dialog, which) -> {
                new File(tempFileName3).delete();
                tempFileName3 = null;
                removePreference(pathFoto3, REQUEST_CODE_PHOTO3);
                isPhotoOk3 = false;
                photoResult_3.setImageBitmap(null);
                deletePhoto3.setVisibility(View.GONE);
            }).create().show();
        });
        deletePhoto4.setOnClickListener(v -> {
            new AlertDialog.Builder(this.getContext()).setMessage("Удалить фото?").setNegativeButton("НЕТ", (dialog, which) -> {
            }).setPositiveButton("ДА", (dialog, which) -> {
                new File(tempFileName4).delete();
                tempFileName4 = null;
                removePreference(pathFoto4, REQUEST_CODE_PHOTO4);
                isPhotoOk4 = false;
                photoResult_4.setImageBitmap(null);
                deletePhoto4.setVisibility(View.GONE);
            }).create().show();
        });
    }

    private String generateKeyForPreference(int REQUEST_CODE_PHOTO) {
        return MAKE_WORK + primaryRequestClass.get(positionGlobal).second.getID() + "_" + REQUEST_CODE_PHOTO;
    }

    private void savePhotoForPreferences(SharedPreferences sharedPreferences, String tempFileName, int REQUEST_CODE_PHOTO) { //сохр.фото.preference
        sharedPreferences = getContext().getSharedPreferences(MAKE_WORK, Context.MODE_PRIVATE); //регистр.SharedPref
        SharedPreferences.Editor editorSave = sharedPreferences.edit();
        String strNameKey = generateKeyForPreference(REQUEST_CODE_PHOTO);
        editorSave.putString(strNameKey, tempFileName).apply();
    }

    private void loadingPhotoForPreferences(SharedPreferences sharedPreferences, int REQUEST_CODE_PHOTO) { //загруж.фото.preference
        sharedPreferences = getContext().getSharedPreferences(MAKE_WORK, Context.MODE_PRIVATE); //регистр.SharedPref
        String strNameKey = generateKeyForPreference(REQUEST_CODE_PHOTO);
        Log.d(RETROFIT, "loadingPhotoForPreferences: " + strNameKey);
        if (sharedPreferences.contains(strNameKey)) {
            switch (REQUEST_CODE_PHOTO) {
                case REQUEST_CODE_PHOTO1:
                    tempFileName1 = sharedPreferences.getString(strNameKey, null);
                    break;
                case REQUEST_CODE_PHOTO2:
                    tempFileName2 = sharedPreferences.getString(strNameKey, null);
                    break;
                case REQUEST_CODE_PHOTO3:
                    tempFileName3 = sharedPreferences.getString(strNameKey, null);
                    break;
                case REQUEST_CODE_PHOTO4:
                    tempFileName4 = sharedPreferences.getString(strNameKey, null);
                    break;
            }
        }
    }

    private void removePreference(SharedPreferences sharedPreferences, int REQUEST_CODE_PHOTO) {
        sharedPreferences = getContext().getSharedPreferences(MAKE_WORK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorRemove = sharedPreferences.edit();
        editorRemove.remove(generateKeyForPreference(REQUEST_CODE_PHOTO));
        editorRemove.apply();
    }

    private void cacheMiniature() { //кешируем миниатюры
        if (tempFileName1 != null) { //кеш
            PhotoNavigator.miniaturePicasso100x100(tempFileName1, photoResult_1);
            isPhotoOk1 = true;
            deletePhoto1.setVisibility(View.VISIBLE);
        }
        if (tempFileName2 != null) {
            PhotoNavigator.miniaturePicasso100x100(tempFileName2, photoResult_2);
            isPhotoOk2 = true;
            deletePhoto2.setVisibility(View.VISIBLE);
        }
        if (tempFileName3 != null) {
            PhotoNavigator.miniaturePicasso100x100(tempFileName3, photoResult_3);
            isPhotoOk3 = true;
            deletePhoto3.setVisibility(View.VISIBLE);
        }
        if (tempFileName4 != null) {
            PhotoNavigator.miniaturePicasso100x100(tempFileName4, photoResult_4);
            isPhotoOk4 = true;
            deletePhoto4.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateAdapter(List<ExtraReglamentAndZIP> extraReglamentAndZIPList) {
        mWorkAdapter.updateCounts(extraReglamentAndZIPList);
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
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    public WorkPresenter getmWorkPresenter() {
        return mWorkPresenter;
    }

    public void setmWorkPresenter(WorkPresenter mWorkPresenter) {
        this.mWorkPresenter = mWorkPresenter;
    }

    public TimeAdapter getmTimeAdapter() {
        return mTimeAdapter;
    }

    public void setmTimeAdapter(TimeAdapter mTimeAdapter) {
        this.mTimeAdapter = mTimeAdapter;
    }

    public WorkAdapter getmWorkAdapter() {
        return mWorkAdapter;
    }

    public void setmWorkAdapter(WorkAdapter mWorkAdapter) {
        this.mWorkAdapter = mWorkAdapter;
    }

}
