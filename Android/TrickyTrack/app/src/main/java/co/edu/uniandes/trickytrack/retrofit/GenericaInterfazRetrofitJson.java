package co.edu.uniandes.trickytrack.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GenericaInterfazRetrofitJson {
    @GET
    Call<List<Example>> getPlaces(@Url String url);
}
