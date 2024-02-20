package dataAccess;

import model.*;

import java.util.List;
public interface AuthData_INT {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // AuthData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<AuthData> getAuthData() throws DataAccessException;
    void clearAuthData() throws DataAccessException;
    String createAuth (String username) throws DataAccessException;
    String getAuthToken (String username) throws DataAccessException;
    String getUsername (String authToken) throws DataAccessException;
    void deleteAuth (String authToken) throws DataAccessException;
}
