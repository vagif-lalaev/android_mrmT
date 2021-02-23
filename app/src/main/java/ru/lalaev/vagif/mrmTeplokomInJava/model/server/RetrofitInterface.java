package ru.lalaev.vagif.mrmTeplokomInJava.model.server;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.AdditionalWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.FileClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.SubWorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.User;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.WorkClass;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP.ZIP;
import ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.classes.AuthDataClass;

public interface RetrofitInterface {

    @POST(".")
    Observable getAnything();

    @Headers({"Accept: application/json",
            "Content-Type': 'application/json",
            "Cache-Control':'no-cache"
    })
    @POST("api-token-auth/")
    Observable<AuthDataClass> auth(@Body RequestBody var1);

    @Headers({"Accept: application/json",
            "Content-Type': 'application/json",
            "Cache-Control':'no-cache"
    })
    @POST("api-token-auth/")
    Call<AuthDataClass> authCall(@Body RequestBody var1);

    @GET("works/")
    Observable<Response<ResponseBody>> loadPlanDate(@Query("date") String var1, @Header("Authorization") String var2);

    @GET("works/")
    Observable<Response<ResponseBody>> loadPlan(@Header("Authorization") String var2);

    @GET("works/")
    Call<WorkClass> loadPlanT(@Query("date") String var1, @Header("Authorization") String var2);

    @GET("works/")
    Call<List<WorkClass>> loadPlanNew();

    @GET("works/")
    Call<List<WorkClass>> loadPlanDay(@Query("date") String date);

    @FormUrlEncoded
    @PUT("works/") // статус
    Call<WorkClass> putLoadStatus(@Field("ID") long id, @Field("Status") long status);

    @FormUrlEncoded
    @PUT("works/") // рекомендации (не используються, ушли от этого из-за 3 дизайна)
    Call<WorkClass> putLoadRecommendation(@Field("ID") long id, @Field("Recommendation") String recommendation);

    @FormUrlEncoded
    @PUT("works/") // Дата
    Call<WorkClass> putLoadDate(@Field("ID") long id, @Field("Date") String date);

    @FormUrlEncoded
    @PUT("works/") // фото Base64
    Call<WorkClass> putLoadPhoto(
            @Field("ID") long id,
            @Field("Foto1") String foto1,
            @Field("Foto2") String foto2,
            @Field("Foto3") String foto3,
            @Field("Foto4") String foto4
    );

    @Multipart
    @POST("api/")
    Call<FileClass> uploadFile( //фото ориг.
            @PartMap HashMap<String, RequestBody> map,
            @Part MultipartBody.Part file
    );


    @FormUrlEncoded
    @POST("api/")
    Call<AdditionalWorkClass> resultIdForAddImage(
           @Field("Work_id") long id,
           @Field("Req") String req,
           @Field("Title") String title
    );

    @FormUrlEncoded
    @POST("api/")
    Call<AdditionalWorkClass> resultIdForRecommendationImage(
           @Field("Work_id") long id,
           @Field("Req") String req,
           @Field("Title") String title,
           @Field("Date") String date
    );

    @FormUrlEncoded
    @POST("api/")
    Call<AdditionalWorkClass> deleteEvent( //удаляем созданные события
           @Field("ID") String idEvent,
           @Field("Req") String req
    );



    @GET("admin/")
    Call<String> testCall();

    @FormUrlEncoded
    @POST("api/")
    Call<List<User>> userInfo( //груз.дан.User
           @Field("Req") String req
    );

    @FormUrlEncoded
    @POST("api/")
    Call<List<SubWorkClass>> getReglamentWorks ( //получение регламентных работ
            @Field("ID") int id,
            @Field("Req") String req
    );

    @FormUrlEncoded
    @POST("api/")
    Call<SubWorkClass> setReglamentWorksIsStatus ( //отправка статусов регламентных работ
            @Field("ID") int id,
            @Field("Req") String req,
            @Field("Status") byte isStataus
    );

    @FormUrlEncoded
    @POST("api/")
    Call<List<ZIP>> getListZIP ( //список работ ЗИП
        @Field("ID") int idWork,
        @Field("Req") String req
        );


}
