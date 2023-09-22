package pt.ua.deti.ies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private Main() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AnyCityForecast(), 0, //initial delay
                                  20 * 1000); //subsequent rate
    }

    static class AnyCityForecast extends TimerTask {
        public void run() {
            List<Integer> cityIdsList =
                    List.of(1010500, 1020500, 1030300, 1040200, 1050200, 1060300, 1070500, 1080500, 1081505, 1090700,
                            1100900, 1110600, 1121400, 1131200, 1141600, 1160900, 1171400, 1182300);

            try {
                int CITY_ID = cityIdsList.get((int) ((Math.random() * (cityIdsList.size()))));
                var forecast = WeatherStarter.getWeather(CITY_ID);
                if (forecast == null) throw new IOException("No results for this request");
                logger.info(String.format("""
                                                  Forecast for city %d
                                                  Max temperature: %4.1f
                                                  Min temperature: %4.1f
                                                  Rain probability: %4.1f
                                                  Wind: %d %s""",
                                          CITY_ID,
                                          Double.parseDouble(forecast.getTMax()),
                                          Double.parseDouble(forecast.getTMin()),
                                          Double.parseDouble(forecast.getPrecipitaProb()),
                                          forecast.getClassWindSpeed(),
                                          forecast.getPredWindDir()));
            } catch (IOException e) {
                logger.error(e.getMessage());
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

