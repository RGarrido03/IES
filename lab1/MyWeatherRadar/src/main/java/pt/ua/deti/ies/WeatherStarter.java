package pt.ua.deti.ies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import pt.ua.deti.ies.ipma_client.IpmaCityForecast;
import pt.ua.deti.ies.ipma_client.IpmaService;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {
    public static void main(String[] args) {
        int CITY_ID = Integer.parseInt(args[0]);

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.ipma.pt/open-data/")
                                                  .addConverterFactory(GsonConverterFactory.create()).build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();

                System.out.printf("""
                                          Forecast for %s
                                          Max temperature: %4.1f
                                          Min temperature: %4.1f
                                          Rain probability: %4.1f
                                          Wind: %d %s""",
                                  firstDay.getForecastDate(),
                                  Double.parseDouble(firstDay.getTMax()), Double.parseDouble(firstDay.getTMin()),
                                  Double.parseDouble(firstDay.getPrecipitaProb()), firstDay.getClassWindSpeed(),
                                  firstDay.getPredWindDir());
            } else {
                System.out.println("No results for this request!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}