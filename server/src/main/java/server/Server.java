package server;

import com.google.gson.JsonArray;
import dataAccess.*;
import model.*;
import spark.*;
import service.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;

public class Server {
    private final Services services;
    public Server() {
//        services = new Services(new MemoryDataAccess());
        try {
            services = new Services(new MySQLDataAccess());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");
        Spark.delete("/db", this::clearAll);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logOutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.awaitInitialization();
        return Spark.port();
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public Object clearAll(Request req, Response res) throws DataAccessException {
        services.clear();
        res.status(200);
        return "{}";
    }

    public Object registerUser (Request req, Response res) throws DataAccessException {
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
            res.status(200);
            json = gson.toJson(authToken);
        }
        else {
            res.status(403);
            jsonObject.addProperty("message", "Error: already taken");
            json = gson.toJson(jsonObject);
        }

        return json;
    }

    public Object loginUser (Request req, Response res) throws DataAccessException {
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
            return json;

        }
        else if (username != null) {
            if (services.checkPassword(username, password)) {
                res.status(200);
                AuthData authData = new AuthData(username, services.getToken(username));
                json = gson.toJson(authData);
            } else {
                res.status(401);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: unauthorized");
                json = gson.toJson(newJson);
            }
            return json;
        }
        else {
            res.status(500);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: no user found");
            json = gson.toJson(newJson);
            return json;
        }
    }

    public Object logOutUser (Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        String username = services.getUsername(authToken);
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

    public Object listGames (Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        String username = services.getUsername(authToken);
        JsonObject jsonObject = new JsonObject();
        String json;
        Gson gson = new Gson();
        List<GameData> allGames;
        if (username != null) {
            allGames = services.listGames();
            if (!allGames.isEmpty()) {
                res.status(200);
                JsonArray jsonArray = getJsonElements(allGames);
                jsonObject.add("games", jsonArray);
                json = gson.toJson(jsonObject);
            }
            else {
                res.status(200);
                jsonObject.addProperty("message", "Info: no games");
                JsonArray jsonArray = new JsonArray();
                jsonObject.add("games", jsonArray);
                json = gson.toJson(jsonObject);
            }
        }
        else {
            res.status(401);
            jsonObject.addProperty("message", "Error: unauthorized");
            JsonArray jsonArray = new JsonArray();
            jsonObject.add("games", jsonArray);
            json = gson.toJson(jsonObject);
        }


        return json;
    }

    private static JsonArray getJsonElements(List<GameData> allGames) {
        JsonArray jsonArray = new JsonArray();
        for (GameData gameData : allGames) {
            JsonObject obj1 = new JsonObject();
            obj1.addProperty("gameID", gameData.getGameID());
            obj1.addProperty("whiteUsername", gameData.getWhiteUsername());
            obj1.addProperty("blackUsername", gameData.getBlackUsername());
            obj1.addProperty("gameName", gameData.getGameName());
            jsonArray.add(obj1);
        }
        return jsonArray;
    }

    public Object createGame (Request req, Response res) throws DataAccessException {
        JsonObject jsonObject = new Gson().fromJson(req.body(), JsonObject.class);

        String json;
        Gson gson = new Gson();
        if (jsonObject.isEmpty()) {
            res.status(500);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: gameID already used");
            json = gson.toJson(newJson);
            return json;
        }
        String gameName = jsonObject.get("gameName").getAsString();
        String authToken = req.headers("Authorization");

        String userName = services.getUsername(authToken);
        Integer gameID;


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



    public Object joinGame (Request req, Response res) throws DataAccessException {
        JsonObject jsonObject = new Gson().fromJson(req.body(), JsonObject.class);
        String json;
        Gson gson = new Gson();

        if (jsonObject.get("gameID").getAsInt() == 0) {
            res.status(400);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: invalid gameID");
            json = gson.toJson(newJson);
            return json;
        }
        else if (jsonObject.get("playerColor") == null) {
            JsonObject newJson = new JsonObject();
            String authToken = req.headers("Authorization");
            String userName = services.getUsername(authToken);
            if (userName == null) {
                res.status(401);
                newJson.addProperty("message", "Error: unauthorized");
            }
            else {
                Integer game = services.getGame(jsonObject.get("gameID").getAsInt());
                if (game > 0) {
                    res.status(200);
                    newJson.addProperty("gameID", game);
                } else {
                    res.status(400);
                    newJson.addProperty("message", "Error: no games");
                }
            }
            json = gson.toJson(newJson);
            return json;
        }

        String teamColor = jsonObject.get("playerColor").getAsString();
        Integer gameID = jsonObject.get("gameID").getAsInt();
        String authToken = req.headers("Authorization");
        String userName = services.getUsername(authToken);
        if (userName != null) {
            if (gameID.equals(services.getGame(gameID))) {
                boolean getPlayerColor = services.getPlayerColor(userName, teamColor, gameID);
                if (getPlayerColor) {
                    res.status(403);
                    JsonObject newJson = new JsonObject();
                    newJson.addProperty("message", "Error: invalid player color");
                    json = gson.toJson(newJson);
                    return json;
                }
                else if (services.joinGame(userName, teamColor, gameID)) {
                    res.status(200);
                    return "{}";
                }
                else {
                    res.status(403);
                    JsonObject newJson = new JsonObject();
                    newJson.addProperty("message", "Error: error joining game");
                    json = gson.toJson(newJson);
                    return json;
                }
            }
            else {
                res.status(500);
                JsonObject newJson = new JsonObject();
                newJson.addProperty("message", "Error: no game");
                json = gson.toJson(newJson);
                return json;
            }
        }
        else {
            res.status(401);
            JsonObject newJson = new JsonObject();
            newJson.addProperty("message", "Error: unauthorized");
            json = gson.toJson(newJson);
            return json;
        }
    }
}
