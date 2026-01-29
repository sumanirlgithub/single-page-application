package com.jwtauthentication;

//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import com.google.gson.Gson;
//
///**
// * JWT authentication by writing code to authenticate and access a protected API endpoint.
// * Access the protected resources using JWT token
// * First you need to obtain the access token then use that token to access the protected resources
// * Send a POST request to auth/login with auth details to retrieve JWT tokens.
// * Extract both the access token and the refresh token from the response.
// * Define headers for authentication using the access token.
// * Make a GET request to access the protected /todos endpoint.
// * Raise HTTP exceptions for unsuccessful requests.
// */
//public class Main {
//    private static final String BASE_URL = "http://localhost:8000";
//    private static final String USERNAME = "johnsmith";
//    private static final String PASSWORD = "testpass123";
//
//    public static void main(String[] args) {
//        HttpClient client = HttpClient.newHttpClient();
//        Gson gson = new Gson();
//
//        try {
//            // TODO: Login with credentials
//            AuthTokens auhtTokens = login(gson, client);
//
//            // TODO: Fetch Todos using JWT
//            HttpRequest httpRequest = HttpRequest.newBuilder()
//                    .uri(URI.create(BASE_URL + "/todos"))
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + auhtTokens.accessToken)
//                    .build();
//
//            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            // TODO: Print Todos
//            // TODO: Handle exceptions
//            if (response.statusCode() != 200) {
//                throw new RuntimeException("Unable to access the protected resources . Status code: " + response.statusCode());
//            }
//
//            System.out.println("Response Body: " + response.body() );
//
//        } catch (Exception e) {
//            System.out.println("An HTTP error occurred: " + e.getMessage());
//        }
//    }
//
//    public static AuthTokens login(Gson gson, HttpClient client) throws Exception {
//
//        UserCredentials userCredentials = new UserCredentials(USERNAME, PASSWORD);
//        String requestBody = gson.toJson(userCredentials);
//
//        HttpRequest httpRequest = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/auth/login"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .build();
//
//        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        if (response.statusCode() != 200) {
//            throw new RuntimeException("Login failed. Status code: " + response.statusCode());
//        }
//        AuthTokens authTokens = gson.fromJson(response.body(), AuthTokens.class);
//        return authTokens;
//    }
//}