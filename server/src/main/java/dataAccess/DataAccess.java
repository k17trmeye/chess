package dataAccess;

import model.*;
import org.eclipse.jetty.server.Authentication;

import java.util.ArrayList;
import java.util.List;

public class DataAccess {
    private List<AuthData> DA_AuthData;
    private List<GameData> DA_GameData;
    private List<UserData> DA_UserData;

    public DataAccess() {
        this.DA_AuthData = new ArrayList<>();
        this.DA_GameData = new ArrayList<>();
        this.DA_UserData = new ArrayList<>();
    }

    public void clear() throws DataAccessException {
        for (UserData userData : DA_UserData) {
            DA_UserData.remove(userData);
        }
        for (GameData gameData : DA_GameData) {
            DA_UserData.remove(gameData);
        }
        for (AuthData authData : DA_AuthData) {
            DA_UserData.remove(authData);
        }
    }

    public UserData getUser(String userName) throws DataAccessException {
        for (UserData userData : DA_UserData) {
            if (userData.getUsername().equals(userName)) {
                return userData; // Found the UserData with the specified username
            }
        }
        return null;
    }

    public void createUser(String userName, String password, String email) throws DataAccessException {
        UserData newUser = new UserData(userName, password, email);
        DA_UserData.add(newUser);
    }


}
