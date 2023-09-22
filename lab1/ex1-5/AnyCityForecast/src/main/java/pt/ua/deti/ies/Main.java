package pt.ua.deti.ies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

// TODO: Add city list
public class Main {
    private static final Logger logger = LogManager.getLogger(WeatherStarter.class);

    public Main() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AnyCityForecast(), 0, //initial delay
                                  20 * 1000); //subsequent rate
    }


    static class AnyCityForecast extends TimerTask {
        public void run() {
            List<Integer> cityIdsList = List.of(1010500, 1010500);

            while (true) {
                try {
                    int CITY_ID = cityIdsList.get((int) ((Math.random() * (cityIdsList.size()))));
                    var forecast = WeatherStarter.getWeather(CITY_ID);
                    if (forecast == null) throw new IOException("No results for this request");
                    logger.info(String.format("""
                                                      Forecast for city %d at %s
                                                      Max temperature: %4.1f
                                                      Min temperature: %4.1f
                                                      Rain probability: %4.1f
                                                      Wind: %d %s""",
                                              CITY_ID,
                                              forecast.getForecastDate(),
                                              Double.parseDouble(forecast.getTMax()),
                                              Double.parseDouble(forecast.getTMin()),
                                              Double.parseDouble(forecast.getPrecipitaProb()),
                                              forecast.getClassWindSpeed(),
                                              forecast.getPredWindDir()));
                    TimeUnit.SECONDS.sleep(20);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                } catch (InterruptedException e) {
                    logger.error("Interrupted sleep! Exiting...");
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

