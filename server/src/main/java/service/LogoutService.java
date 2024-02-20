package service;

import dataAccess.*;

public class LogoutService {
    private final UserData_INT userData;
    private final AuthData_INT authData;
    public LogoutService(UserData_INT userData, AuthData_INT authData) {
        this.userData = userData;
        this.authData = authData;
    }
}
