# conference-service

REStFul API including full information about the conference. The service designed for companies wishing to manage a
conference.

## Endpoints

### /lecture

* `GET` - get all lecture

### /lecture/{login}

* `GET` - get lecture by login

### /reservation/

* `POST` - lecture booking

### /reservation/{login}{email}{lectureSubject}

* `DELETE` - cancel reservation

### /statistic/lectureStatistics

* `GET` get statistic of lecture statistics
  Method GET statistic/thematicPathsStatistic

### /statistic/thematicPathsStatistic/

* `GET` get statistic of thematic paths statistic

### /user/{login}{oldEmail}{newEmail}

* `PUT` - change user's email

### /user/

* `GET` get all users

## Technologies used

* JPA
* Docker
* Maven
* Spring Boot
* Hibernate
* Postgres
* Mockito
* JUnit5

## Database structure

![img.png](img.png)

## How to run.

* download the project
* use 'docker compose up' command
* run server