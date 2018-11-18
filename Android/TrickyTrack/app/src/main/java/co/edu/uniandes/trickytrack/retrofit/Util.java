package co.edu.uniandes.trickytrack.retrofit;

public class Util {
    public static final String BASE_URL = "http://18.188.142.213:8080/";
    public static GenericaInterfazRetrofitJson getService() {
        return RetrofitClient.getClient(BASE_URL).create(GenericaInterfazRetrofitJson.class);
    }
}
