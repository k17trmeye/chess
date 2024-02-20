package service;

import dataAccess.DataAccessException;
import dataAccess.*;

public class ClearService {
    private final UserData_INT userData;
    private final AuthData_INT authData;
    private final GameData_INT gameData;

    public ClearService(UserData_INT userData, AuthData_INT authData, GameData_INT gameData) {
        this.userData = userData;
        this.authData = authData;
        this.gameData = gameData;
    }

    public void clear() throws DataAccessException{
        userData.clearUserData();
        authData.clearAuthData();
        gameData.clearGameData();
    }
}
