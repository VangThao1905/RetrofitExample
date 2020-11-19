package vangthao.app.demoretrofit2.Retrofit2;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import vangthao.app.demoretrofit2.SinhVien;

public interface DataClient {

    @Multipart
    @POST("uploadimage.php")
    retrofit2.Call<String> uploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insert.php")
    Call<String> insertData(@Field("taikhoan") String taiKhoan,
                            @Field("matkhau") String matKhau,
                            @Field("hinhanh") String hinhAnh);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<SinhVien>> loginData(@Field("taikhoan") String taiKhoan,
                                   @Field("matkhau") String matKhau);

    @GET("delete.php")
    Call<String> deleteData(@Query("id") String id,
                            @Query("hinhanh") String hinhanh);
}
