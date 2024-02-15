package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserData_DAO {
    private final List<UserData> DB_UserData;

    public UserData_DAO() {
        this.DB_UserData = new ArrayList<>();
    }

    public List<UserData> getUserData() {
        return DB_UserData;
    }

    public void clear() throws DataAccessException {
        for (UserData userData : DB_UserData) {
            DB_UserData.remove(userData);
        }
    }

    public String checkUserName(String userName) throws DataAccessException {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(userName)) {
                return userData.getUsername();
            }
        }
        return null;
    }

    public void createUser(String userName, String password, String email) throws DataAccessException {
        // Create a new user
        UserData newUser = new UserData(userName, password, email);

        // Add the new user to the database
        DB_UserData.add(newUser);
    }

    public String getPassword(String username) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(username)) {
                return userData.getPassword();
            }
        }
        return null;
    }

}
