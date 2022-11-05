package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author Hu Peng
 */
public class UserService {

    public UserService() {
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllUsers();
        return users;
    }

    // return the User based on their email
    public User getUser(String Email) throws Exception {
        UserDB userDB = new UserDB();
        return userDB.getUser(Email);
    }

    public void updateUser(String email, boolean active, String firstname, String lastname, String password, int roleID) throws Exception {
        UserDB userDB = new UserDB();
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleID);
        User user = userDB.getUser(email);
        user.setActive(active);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPassword(password);
        user.setRole(role);
        userDB.update(user);
    }

    public void insert(String email, boolean active, String firstname, String lastname, String password, int roleID) throws Exception {
        UserDB userDB = new UserDB();
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleID);
        User user = new User(email, active, firstname, lastname, password);
        user.setRole(role);
        userDB.insert(user);
    }

    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUser(email);
        userDB.delete(user);
    }

}
