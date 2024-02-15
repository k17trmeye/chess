package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;

public abstract class UserData_MEM implements DataAccess{
    private final List<UserData> DB_UserData;

    public UserData_MEM() {
        this.DB_UserData = new ArrayList<>();
    }

    public List<UserData> getUserData() {
        return DB_UserData;
    }

    public void clearUserData() {
        for (UserData userData : DB_UserData) {
            DB_UserData.remove(userData);
        }
    }

    public String checkUserName(String userName) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(userName)) {
                return userData.getUsername();
            }
        }
        return null;
    }

    public void createUser(String userName, String password, String email) {
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
