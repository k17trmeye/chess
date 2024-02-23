package server;

import dataAccess.*;
import model.*;
import spark.*;
import service.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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
        Spark.delete("/session", this::LogOutUser);
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
        services.clear();
        res.status(200);
        return "{}";
    }

    private Object RegisterUser (Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        String username = services.getUser(user.getUsername());
        JsonObject jsonObject = new JsonObject();
        AuthData authToken;
        String json;
        Gson gson = new Gson();

        if (user.getPassword() == null || user.getEmail() == null) {
            res.status(400);
            jsonObject.addProperty("message", "Error: already taken");
            json = gson.toJson(jsonObject);
        }
        else if (username == null) {
            username = services.createUser(user.getUsername(), user.getPassword(), user.getEmail());
            authToken = services.createAuth(username);
            if (authToken == null) {
                res.status(403);
                jsonObject.addProperty("message", "Error: authToken already taken");
                json = gson.toJson(jsonObject);
            }
            else {
                res.status(200);
                json = gson.toJson(authToken);
            }

        }
        else {
            res.status(403);
            jsonObject.addProperty("message", "Error: already taken");
            json = gson.toJson(jsonObject);
        }

        return json;
    }

    private Object LoginUser (Request req, Response res) throws DataAccessException {
        JsonObject jsonObject = new Gson().fromJson(req.body(), JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        String json;
        Gson gson = new Gson();
        if (password == null) {
            res.status(500);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: no password provided");
            json = gson.toJson(newJson);
        }
        else if (username != null) {
            if (services.checkPassword(username, password)) {
                if (!services.returnLoggedIn(username)) {
                    services.setLoggedIn(username);
                    res.status(200);
                    AuthData authData = new AuthData(username, services.getToken(username));
                    json = gson.toJson(authData);
                }
                else {
                    res.status(200);
                    AuthData authData = new AuthData(username, services.getToken(username));
                    json = gson.toJson(authData);
//                    return "{}";
                }
            } else {
                res.status(401);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: unauthorized");
                json = gson.toJson(newJson);
            }
        }
        else {
            res.status(500);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: no user found");
            json = gson.toJson(newJson);
        }
        return json;
    }

    private Object LogOutUser (Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        String username = services.getUsername(authToken);
        JsonObject jsonObject = new JsonObject();
        String json;
        Gson gson = new Gson();
        if (username != null) {
            if (services.deleteAuth(authToken)) {
                res.status(200);
                return "{}";
            } else {
                res.status(500);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: auth not deleted");
                json = gson.toJson(newJson);
            }
        }
        else {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: unauthorized");
            json = gson.toJson(newJson);
        }
        return json;
    }

    private Object ListGames (Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        String username = services.getUsername(authToken);
        JsonObject jsonObject = new JsonObject();
        String json;
        Gson gson = new Gson();
        List<GameData> allGames = new ArrayList<>();
        if (username != null) {
            allGames = services.listGames();
            res.status(200);
            for (GameData gameData : allGames) {
                jsonObject.addProperty("GameID", gameData.getGameID());
                jsonObject.addProperty("whiteUsername", gameData.getWhiteUsername());
                jsonObject.addProperty("blackUsername", gameData.getBlackUsername());
                jsonObject.addProperty("GameName", gameData.getGameName());
            }
            json = gson.toJson(jsonObject);
        }
        else {
            res.status(401);
            jsonObject.addProperty("message", "Error: unauthorized");
            json = gson.toJson(jsonObject);
        }


        return json;
    }

    private Object CreateGame (Request req, Response res) throws DataAccessException {
        JsonObject jsonObject = new Gson().fromJson(req.body(), JsonObject.class);
        String gameName = jsonObject.get("gameName").getAsString();
        String authToken = req.headers("Authorization");

        String userName = services.getUsername(authToken);
        Integer gameID;

        String json;
        Gson gson = new Gson();
        if (gameName == null) {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: no gameName provided");
            json = gson.toJson(newJson);
        }
        else if (userName != null) {
            gameID = services.createGame(gameName);
            if (gameID > 0) {
                JsonObject newJson = new JsonObject();
                res.status(200);
                newJson.addProperty("gameID", gameID);
                json = gson.toJson(newJson);
            }
            else {
                res.status(500);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: gameID already used");
                json = gson.toJson(newJson);
            }

        }
        else {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: unauthorized");
            json = gson.toJson(newJson);
        }

        return json;
    }



    private Object JoinGame (Request req, Response res) throws DataAccessException {
        JsonObject jsonObject = new Gson().fromJson(req.body(), JsonObject.class);
        String json;
        Gson gson = new Gson();
        if (jsonObject.get("playerColor") == null || jsonObject.get("gameID") == null) {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: bad request");
            json = gson.toJson(newJson);
            return json;
        }
        String teamColor = jsonObject.get("playerColor").getAsString();
        Integer gameID = jsonObject.get("gameID").getAsInt();
        String authToken = req.headers("Authorization");

        String userName = services.getUsername(authToken);

        if (userName != null) {
            if (gameID.equals(services.getGame(gameID))) {
                if (services.joinGame(userName, teamColor, gameID)) {
                    res.status(200);
                    return "{}";
                }
                else {
                    res.status(403);
                    JsonObject newJson = new JsonObject();
                    newJson.addProperty("message", "Error: already taken");
                    json = gson.toJson(newJson);
                }
            }
            else {
                res.status(500);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: bad request");
                json = gson.toJson(newJson);
            }
        }
        else {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: unauthorized");
            json = gson.toJson(newJson);
        }

        return json;
    }
}
