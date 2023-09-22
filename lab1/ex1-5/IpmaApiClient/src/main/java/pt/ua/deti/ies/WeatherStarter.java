package pt.ua.deti.ies;

import pt.ua.deti.ies.ipma_client.CityForecast;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import pt.ua.deti.ies.ipma_client.IpmaCityForecast;
import pt.ua.deti.ies.ipma_client.IpmaService;

import java.io.IOException;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {
    public static CityForecast getWeather(int CITY_ID) throws IOException {
        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.ipma.pt/open-data/")
                                                  .addConverterFactory(GsonConverterFactory.create()).build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint

        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID);

        Response<IpmaCityForecast> apiResponse = callSync.execute();
        IpmaCityForecast forecast = apiResponse.body();

        if (forecast != null) {
            return forecast.getData().listIterator().next();
        }
        return null;
    }
}