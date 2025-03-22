package MyPackage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // API Key
        String apiKey = "de291f99041d4766446cde26103c2cbb";

        // Get the city from the form input
        String city = request.getParameter("city");

        // Check if city is valid StringUtils.ise`	
        if (city == null || city.isEmpty()) {
            request.setAttribute("error", "City parameter is missing or invalid.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Create the URL for the OpenWeatherMap API request
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try (InputStream inputStream = new URL(apiUrl).openStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             Scanner scanner = new Scanner(reader)) {

            // Read the response
            StringBuilder responseContent = new StringBuilder();
            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }

            // Use Gson to parse the JSON response
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
//            System.out.println(jsonObject);

            // Date & Time
            long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000; // Convert to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date(dateTimestamp));

            // Temperature
            double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
            int temperatureCelsius = (int) (temperatureKelvin - 273.15);

            // Humidity
            int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();

            // Wind Speed
            double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();

            // Weather Condition
            String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

            // Set the data as request attributes
            request.setAttribute("date", date);
            request.setAttribute("city", city);
            request.setAttribute("temperature", temperatureCelsius);
            request.setAttribute("weatherCondition", weatherCondition);
            request.setAttribute("humidity", humidity);
            request.setAttribute("windSpeed", windSpeed);
            request.setAttribute("weatherData", responseContent.toString());
            
            

        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred while fetching weather data.");
        }

        // Forward the request to the weather.jsp page for rendering
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

