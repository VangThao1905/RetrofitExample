package vangthao.app.demoretrofit2.Retrofit2;

public class APIUtils {
    public static final String BaseUrl = "http://192.168.1.73/QuanLySinhVien/";

    public static DataClient getData(){
        return RetrofitClient.getClient(BaseUrl).create(DataClient.class);
    }
}
