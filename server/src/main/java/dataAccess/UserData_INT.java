package dataAccess;

import model.*;

import java.util.List;

public interface UserData_INT {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // UserData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<UserData> getUserData() throws DataAccessException;
    void clearUserData() throws DataAccessException;
    String checkUserName(String userName) throws DataAccessException;
    void createUser(String userName, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;


}
