package server;

import dataAccess.*;
import model.*;
import spark.*;
import service.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Server {
    private final Services services;

    public Server() {
        services = new Services(new MemoryDataAccess());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::ClearGames);
        Spark.post("/user", this::RegisterUser);
        Spark.post("/session", this::LoginUser);
        Spark.delete("/session", this::LogooutUser);
        Spark.get("/game", this::ListGames);
        Spark.post("/game", this::CreateGame);
        Spark.put("/game", this::JoinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object ClearGames (Request req, Response res) throws DataAccessException {
        String json;
        if (services.clear()) {
            Gson gson = new Gson();
            json = gson.toJson(200);
//            System.out.println(json);
        }
        else {
            Gson gson = new Gson();
            int code = 500;
            String messsage = "Error: DB not emptied";
            json = gson.toJson(code + messsage);
//            System.out.println(json);
        }


        return json;
    }

    private Object RegisterUser (Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        String username = services.getUser(user.getUsername());
        JsonObject jsonObject = new JsonObject();
        String authToken;
        String json;
        Gson gson = new Gson();
        if (username == null) {
            username = services.createUser(user.getUsername(), user.getPassword(), user.getEmail());
            authToken = services.createAuth(username);
            jsonObject.addProperty("username:", username);
            jsonObject.addProperty("authToken:", authToken);
            json = gson.toJson(jsonObject.toString());
//            System.out.println(json);
        }
        else {
            jsonObject.addProperty("message", "Error: already taken");
            json = gson.toJson(jsonObject.toString());
//            System.out.println(json);
        }

        return json;
    }

    private Object LoginUser (Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        String username = services.getUser(user.getUsername());
        JsonObject jsonObject = new JsonObject();
        String authToken;
        String json;
        Gson gson = new Gson();
        if (username != null) {
            if (services.checkPassword(username, user.getPassword())) {

                jsonObject.addProperty("username:", username);
                jsonObject.addProperty("authToken:", services.getToken(username));
                json = gson.toJson(jsonObject.toString());
//                System.out.println(json);
            } else {
                jsonObject.addProperty("message", "Error: unauthorized");
                json = gson.toJson(jsonObject.toString());
//                System.out.println(json);
            }
        }
        else {
            jsonObject.addProperty("message", "Error: no user found");
            json = gson.toJson(jsonObject.toString());
//            System.out.println(json);
        }
        return json;
    }

    private Object LogooutUser (Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), AuthData.class);
        String validToken = services.getAuthToken(user.getUsername());
        JsonObject jsonObject = new JsonObject();
        String authToken;
        String json;
        Gson gson = new Gson();
        if (validToken != null) {
            if (services.deleteAuth(validToken)) {

                json = gson.toJson(200);
//                System.out.println(json);
            } else {
                jsonObject.addProperty("message", "Error: unauthorized");
                json = gson.toJson(jsonObject.toString());
//                System.out.println(json);
            }
        }
        else {
            jsonObject.addProperty("message", "Error: no user found");
            json = gson.toJson(jsonObject.toString());
//            System.out.println(json);
        }
        return json;
    }

    private Object ListGames (Request req, Response res) throws DataAccessException {

        return "";
    }

    private Object CreateGame (Request req, Response res) throws DataAccessException {

        return "";
    }



    private Object JoinGame (Request req, Response res) throws DataAccessException {

        return "";
    }
}
