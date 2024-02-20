package service;
import dataAccess.DataAccessException;
import dataAccess.*;

public class Service {
    private final UserData_INT userData;
    private final AuthData_INT authData;
    private final GameData_INT gameData;

    public Service (UserData_INT userData, AuthData_INT authData, GameData_INT gameData) {
        this.userData = userData;
        this.authData = authData;
        this.gameData = gameData;
    }

}
