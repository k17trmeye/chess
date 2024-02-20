package service;

import dataAccess.DataAccessException;
import dataAccess.UserData_INT;
import dataAccess.AuthData_INT;

public class RegisterService {
    private final UserData_INT userData;
    private final AuthData_INT authData;

    public RegisterService(UserData_INT userData, AuthData_INT authData) {
        this.userData = userData;
        this.authData = authData;
    }

    public String getUser(String username) throws DataAccessException{
        return userData.checkUserName(username);
    }

    public void createUser(String username, String password, String email) throws DataAccessException{
        userData.createUser(username, password, email);
    }

    public String createAuth(String username) throws DataAccessException{
        return authData.createAuth(username);
    }
}
