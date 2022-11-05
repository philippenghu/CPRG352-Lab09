<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="CSS/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User management<h1>
        <h2>Add User</h2>
        <table>
            <form action="user" method="post">
                <tr>
                    <td>
                        <input type="email" name="email" placeholder="Email" value="${addUser.email}"><br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" name="active" value="${addUser.active}"> Active <br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="text" name="firstName" value="${addUser.firstName}" placeholder="First Name"><br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="text" name="lastName" value="${addUser.lastName}" placeholder="Last Name" ><br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="text" name="password" value="${addUser.password}" placeholder="Password" ><br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <select name="roleId">
                            <option value="1">system admin</option>
                            <option value="2">regular user</option>
                            <option value="3">company admin</option>
                        </select>
                    </td>    
                </tr>
                <br>
                <tr>
                    <td>
                        <input type="hidden" name="action" value="save">
                        <input type="submit" value="Add">  
                    </td>    
                </tr>
            </form>
        </table>
        <p>
            ${message}
        <p>
        <h2>Manage User</h2>
       <table>
            <form action="user" method="post">
                <tr>
                    <th>Active</th>
                    <th>First Name</th>
                    <th>Last Name</th> 
                    <th>Role</th> 
                    <th>Password</th> 
                    <th>Edit</th>
                    <th>Delete</th>    
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>
                            <c:out value = "${user.active}"/>
                        </td>
                        <td>
                            <c:out value = "${user.firstName}"/>
                        </td>
                        <td>
                            <c:out value = "${user.lastName}"/>
                        </td>
                        <td>
                            <c:out value = "${user.role.roleName}"/>
                        </td>
                        <td>
                            <c:out value = "${user.password}"/>
                        </td>
                        <td>
                            <form  method="post"> 
                                <input type="hidden" name="editUser" value="${user.email}">
                                <input type="submit" value="Edit">
                                <input type="hidden" name="action" value="showEdit">
                            </form>
                        </td>
                        <td>
                            <form action="?action=delete" method="post">
                                <input type="hidden" name="deleteUser" value="${user.email}">
                                <input type="submit" name="action" value="Delete">
                            </form>
                        </td>
                    </tr> 
                </c:forEach>
        </table>
        <h2>Edit User</h2>
        <p>${editMessage}<p>
        <table>
            <form action="?action=update" method="post">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${editUser.active}">
                                <input type="checkbox" name="activeEdit" value="1" checked>Active
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="activeEdit" value="1">Active
                            </c:otherwise>
                        </c:choose>
                    </td>  
                </tr>
                <br>
                <tr>
                    <td>
                        <input type="text" name="firstNameEdit" placeholder="First Name" value="${editUser.firstName}"><br>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="text" name="lastNameEdit" placeholder="Last Name" value="${editUser.lastName}"><br>
                    </td>    
                </tr>
               
                <tr>
                    <td>
                        <input type="text" name="passwordEdit" placeholder="Password" value="${editUser.password}"><br>
                    </td>    
                </tr>
                 <tr>
                    <td>
                        <c:if test="${editUser.role.roleId==1}">
                            <select name="editRole">
                                <option value="1" selected>system admin</option>
                                <option value="2">regular user</option>       
                                <option value="3">company admin</option>
                            </select> 
                        </c:if>
                        <c:if test="${editUser.role.roleId==2}">
                            <select name="editRole">
                                <option value="1">system admin</option>
                                <option value="2" selected>regular user</option>       
                                <option value="3">company admin</option>
                            </select>
                        </c:if>
                        <c:if test="${editUser.role.roleId==3}">
                            <select name="editRole">
                                <option value="1">system admin</option>
                                <option value="2">regular user</option>       
                                <option value="3" selected>company admin</option>
                            </select>
                        </c:if>
                    </td>    
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Update">
                    </td>    
                </tr>
                <input type="hidden" name="saveChange" value="${user.email}">
            </form>
            <form action="?action=reset"  method="post">
                <tr>
                    <td>
                        <input type="submit" value="Cancel">
                    </td>    
                </tr>
                <input type="hidden" name="action" value="reset">
            </form>
        </table>
    </form>
</body>
</html>
