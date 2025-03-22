<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather Forecast</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <div class="header-container">
            <h1 class="logo">WeatherPro</h1>
            <form action="MyServlet" method="post" class="search-form">
                <input type="text" name="city" placeholder="Search city..." required>
                <button type="submit">Search</button>
            </form>
        </div>
    </header>

    <div class="main-container">
        <div class="weather-card">
            <h2 class="city-name">${city}</h2>
            <p class="weather-condition">${weatherCondition}</p>
            <div class="temperature">${temperature}°C</div>
            <p class="date">${date}</p>
            
            <div class="additional-info">
                <div><strong>Humidity:</strong> ${humidity}%</div>
                <div><strong>Wind Speed:</strong> ${windSpeed} m/s</div>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 WeatherPro. All rights reserved.</p>
    </footer>
</body>
</html>
