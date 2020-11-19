package vangthao.app.lastanswersstackexchange.data.remote;


public class APIUtils {
    public static final String Base_Url = "https://api.stackexchange.com/2.2/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(Base_Url).create(SOService.class);
    }
}
