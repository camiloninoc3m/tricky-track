package co.edu.uniandes.trickytrack.retrofit;

public class Util {
    public static final String BASE_URL = "http://3.16.31.253:8080/";
    public static GenericaInterfazRetrofitJson getService() {
        return RetrofitClient.getClient(BASE_URL).create(GenericaInterfazRetrofitJson.class);
    }
}
