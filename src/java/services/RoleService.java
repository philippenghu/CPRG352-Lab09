/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dataaccess.RoleDB;
import models.Role;
/**
 *
 * @author Hu Peng
 */
public class RoleService {

    public String getRoleName(int roleID) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role= roleDB.getRole(roleID);
        String roleName=role.getRoleName();
        return roleName;
    }

}
