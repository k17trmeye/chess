package service;

import dataAccess.DataAccessException;
import dataAccess.UserData_INT;
import dataAccess.AuthData_INT;


public class LoginService {
    private final UserData_INT userData;
    private final AuthData_INT authData;

    public LoginService(UserData_INT userData, AuthData_INT authData) {
        this.userData = userData;
        this.authData = authData;
    }

    public boolean checkPassword(String username, String password) throws DataAccessException {
        String user_password = userData.getPassword(username);
        if (password.equals(user_password)) {
            return true;
        }
        return false;
    }

    public String getToken(String username) throws DataAccessException {
        return authData.getAuthToken(username);
    }

}
