package ui;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.*;
import model.*;
import java.net.URL;
import java.net.URI;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;


public class ServerFacade {
    private final String urlStr;

    public ServerFacade(Integer port) throws MalformedURLException{
        urlStr = "http://localhost:" + port.toString() + "/";

    }

    public Object registerUser(String username, String password, String email) throws IOException{
        // Set up URL
        String urlAddr = urlStr + "user";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Send Request
        connection.connect();

        // Set up body
        try(OutputStream requestBody = connection.getOutputStream();) {
            String requestBodyData = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"email\":\"" + email + "\"}";
            requestBody.write(requestBodyData.getBytes());
        }

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read in the HTTP response
            InputStream responseBody = connection.getInputStream();
            return readInputStream(responseBody);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();

            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);
            return responseBodyString;
        }
    }

    public String loginUser(String username, String password) throws IOException{
        // Set up URL
        String urlAddr = urlStr + "session";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Send Request
        connection.connect();

        // Set up body
        try(OutputStream requestBody = connection.getOutputStream();) {
            String requestBodyData = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
            requestBody.write(requestBodyData.getBytes());
        }

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read in the HTTP response
            InputStream responseBody = connection.getInputStream();
            String responseBodyString = readInputStream(responseBody);

            // Parse to get the username and return it
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            return jsonObject.get("authToken").getAsString();
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();

            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);

            return responseBodyString;
        }
    }

    public Object logoutUser(String authToken) throws IOException{
        String urlAddr = urlStr + "session";
        URL url = new URL(urlAddr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.addRequestProperty("authorization", authToken);
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            return readInputStream(responseBody);
        }
        else {
            InputStream responseBody = connection.getErrorStream();
            return readInputStream(responseBody);
        }
    }

    public Object listGames(String authToken) throws IOException{
        // Set up URL
        String urlAddr = urlStr + "game";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        // Add header
        connection.addRequestProperty("authorization", authToken);

        // Send Request
        connection.connect();

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read in the HTTP response
            InputStream responseBody = connection.getInputStream();
            String responseBodyString = readInputStream(responseBody);

            // Parse to get the username and return it
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            return responseBodyString;
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();

            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            String message = jsonObject.get("message").getAsString();

            return responseBodyString;
        }
    }

    public String createGame(String authToken, String gameName) throws IOException{
        // Set up URL
        String urlAddr = urlStr + "game";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Add header
        connection.addRequestProperty("authorization", authToken);

        // Send Request
        connection.connect();

        // Set up body
        try(OutputStream requestBody = connection.getOutputStream();) {
            String requestBodyData = "{\"gameName\":\"" + gameName + "\"}";;
            requestBody.write(requestBodyData.getBytes());
        }

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read in the HTTP response
            InputStream responseBody = connection.getInputStream();
            String responseBodyString = readInputStream(responseBody);

            // Parse to get the username and return it
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            return jsonObject.get("gameID").getAsString();
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();

            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            String message = jsonObject.get("message").getAsString();

            return responseBodyString;
        }
    }

    public Object joinGame(String authToken, String playerColor, String gameID) throws IOException{
        // Set up URL
        String urlAddr = urlStr + "game";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        // Add header
        connection.addRequestProperty("authorization", authToken);

        // Send Request
        connection.connect();

        // Set up body
        try(OutputStream requestBody = connection.getOutputStream();) {
            String requestBodyData = "{\"playerColor\":\"" + playerColor + "\", \"gameID\":\"" + gameID + "\"}";
            requestBody.write(requestBodyData.getBytes());
        }

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Read in the HTTP response
            InputStream responseBody = connection.getInputStream();
            String responseBodyString = readInputStream(responseBody);
            return responseBodyString;
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();

            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);
            return responseBodyString;
        }
    }

    public void clear()  throws IOException{
        // Set up URL
        String urlAddr = urlStr + "db";
        URL url = new URL(urlAddr);

        // Set up HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        // Send Request
        connection.connect();

        // Receive Response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            String responseBodyString = readInputStream(responseBody);
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read response body from InputStream
            String responseBodyString = readInputStream(responseBody);
        }
    }

    private static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
