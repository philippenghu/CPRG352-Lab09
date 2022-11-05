package servelets;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        displayAll(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String email = "";
        boolean active = false;
        String firstName = "";
        String lastName = "";
        String password = "";
        int roleId;
        String roleName;
        String editMessage;
        UserService userService = new UserService();
        User user;
        List<User> users;
        User editUser;
        RoleService roleService = new RoleService();
        try {
            if (action != null) {
                switch (action) {
                    case "save":
                        users = userService.getAll();
                        email = request.getParameter("email");
                        active = request.getParameter("active") != null;
                        firstName = request.getParameter("firstName");
                        lastName = request.getParameter("lastName");
                        password = request.getParameter("password");
                        roleId = Integer.parseInt(request.getParameter("roleId"));
                        roleName = roleService.getRoleName(roleId);
                        Role role = new Role(roleId);
                        String message;
                        if (email.equals(null) || email.equals("")
                                || firstName.equals(null) || firstName.equals("")
                                || lastName.equals(null) || lastName.equals("")
                                || password.equals(null) || password.equals("")) {
                            message = "Please correctly enter all the information.";
                            request.setAttribute("message", message);
                        } else if (!users.isEmpty()) {
                            boolean duplicated = false;
                            for (int i = 0; i < users.size(); i++) {
                                if (email.equals(users.get(i).getEmail())) {
                                    message = "Email duplicated, fail to add user.";
                                    request.setAttribute("message", message);
                                    duplicated = true;
                                }
                            }
                            if (duplicated == false) {
                                userService.insert(email, active, firstName, lastName, password, roleId);
                                message = "User " + email + " successfully added";
                                request.setAttribute("message", message);
                            }
                        } else if (users.isEmpty()) {
                            userService.insert(email, active, firstName, lastName, password, roleId);
                            message = "User " + email + " successfully added";
                            request.setAttribute("message", message);
                        }
                        break;
                    case "showEdit":
                        email = (String) request.getParameter("editUser");
                        editUser = userService.getUser(email);
                        request.setAttribute("editUser", editUser);
                        session.setAttribute("editUser", editUser);
                        break;
                    case "delete":
                        email = request.getParameter("deleteUser");
                        userService.delete(email);
                        editMessage = "User " + email + "successfully deleted";
                        request.setAttribute("editMessage", editMessage);
                        break;
                    case "reset":
                        user = new User(email, active, firstName, lastName, password);                      
                        role = new Role(2);
                        user.setRole(role);
                        request.setAttribute("editUser", user);
                        break;
                    case "update":
                        user = (User) session.getAttribute("editUser");
                        email = (String) user.getEmail();
                        active = request.getParameter("activeEdit") != null;
                        firstName = request.getParameter("firstNameEdit");
                        lastName = request.getParameter("lastNameEdit");
                        password = request.getParameter("passwordEdit");
                        roleId = Integer.parseInt(request.getParameter("editRole"));
                        roleName = roleService.getRoleName(roleId);
                        if (firstName == null || firstName.equals("")
                                || lastName == null || lastName.equals("")
                                || password == null || password.equals("")) {
                            editMessage = "Please correctly enter all the information.";
                            request.setAttribute("editMessage", editMessage);
                        } else {
                            User editedUser = new User(email, active, firstName,
                                    lastName, password);
                            Role editedRole = new Role(roleId);
                            editedUser.setRole(editedRole);
                            userService.updateUser(email, active, firstName,
                                    lastName, password, roleId);
                            editMessage = "User " + email + "successfully updated";
                            session.setAttribute("editUser", editedUser);
                            request.setAttribute("editUser", editedUser);
                            request.setAttribute("editMessage", editMessage);
                            break;
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            String message = "Sorry, something went wrong.";
            request.setAttribute("message", message);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayAll(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    private void displayAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> users;
        try {
            users = userService.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            String message = "Sorry, displaying error.";
            request.setAttribute("message", message);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
