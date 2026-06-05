# MovieDiary Backend

MovieDiary Backend is a REST API built with Spring Boot that powers the MovieDiary application. It provides authentication, movie management, watchlist functionality, diary entries, and user profile features.

## Features

* JWT Authentication
* User Registration & Login
* Movie Search
* Popular Movies
* Trending Movies
* Movie Details
* Watchlist Management
* Diary Entries
* Movie Ratings & Reviews
* User Profile
* Secure Protected Routes

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* TMDB API

## API Modules

### Authentication

* Register User
* Login User

### Movies

* Get Popular Movies
* Get Trending Movies
* Search Movies
* Get Movie Details

### Watchlist

* Add Movie to Watchlist
* Remove Movie from Watchlist
* View Watchlist

### Diary

* Add Diary Entry
* Update Diary Entry
* Delete Diary Entry
* View Diary Entries

### Profile

* User Information
* Watchlist Statistics
* Diary Statistics
* Recent Activity

## Database

MySQL is used for storing:

* Users
* Watchlist Entries
* Diary Entries

## Running the Application

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Start Application

```bash
mvn spring-boot:run
```

The backend runs on:

```txt
http://localhost:8080
```

## Author

Balaji
