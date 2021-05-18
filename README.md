# Spring Boot REST API (CRUD)

This is a sample Java / Gradle / Spring Boot (version 2.4.5) application that can be used to create, read, update and delete stories. Create, Update and Delete operation needs the authentication and authorization.

## How to Run
You can run it using `gradle` or `docker`.

To run using gradle:
* Clone this repository
* `cd CRUD-REST-API-SpringBoot`
* Make sure you are using JDK 11 and Gradle 6.8.x and Mysql running on port 3306
* You can build the project and run the tests by running ```gradle clean bootRun```
* Once successfully built, you can run the service by one of these two methods:

To run using Docker
* Stop if your Mysql in running on port 3306
* Clone this repository
* `cd CRUDCRUD-REST-API-SpringBoot`
* Run `docker-compose up --build`

It runs on `port 8080`

## About the Service

The service is a simple story blog REST service with authentication enabled. It uses Mysql database to store the data.

You will need authentication for creating a story and authorization to edit and delete a story.
Here are some endpoints you can call:

### Create a user resource

```
POST /register
Accept: application/json
Content-Type: application/json

{
"username": "simanta",
"email": "simanta@simantaturja.me"
"password": "123456"
}

RESPONSE: HTTP 201 (Created)
```

### Login to user resource (login is needed to create, edit and delete a story)

```
POST /login
Accept: application/json
Content-Type: application/json

{
"username": "simanta",
"password": "123456"
}

RESPONSE: HTTP 200 (OK)
CONTENT: JWT Token
```


### Create a story resource (Authentication needed but no authorization is needed)

```
POST /story
Accept: application/json
Content-Type: application/json

{
"title": "This is a dummy story title",
"description": "This is a dummy story description"
}

RESPONSE: HTTP 201 (Created)
```

### Get information about stories. (No authentication and authorization needed)

```
http://localhost:8080/story
http://localhost:8080/story/{id}

```
Fetch all stories
```
GET /story
Accept: application/json

RESPONSE: 200 (OK)
CONTENT: List of stories
```

Fetch one particular story by `id`
```
GET /story/1
Accept: application/json

RESPONSE: 200 (OK)
CONTENT: a story in json format
CONTENT Structure -> { "id": 1, "title": "dummy story", "description":"dummy description"} 
```


### Update a story resource (Authentication and Authorization needed)

```
PUT /story/1
Accept: application/json
Content-Type: application/json
Authorization: Bearer {token}

{
"title": "This is a dummy story title (edited)",
"description": "This is a dummy story description (Edited)"
}

RESPONSE: HTTP 204 (No Content)
```


### Delete a story resource (Authentication and Authorization needed)

```
DELETE /story/1
Authorization: Bearer {JWT token}


RESPONSE: HTTP 200 (No Content)
```
