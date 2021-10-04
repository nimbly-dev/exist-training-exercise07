package com.exist.altheo.model;

public class Role {
    int roleId;
    int roleName;

    public Role(){
        
    }

    public Role(int roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getRoleName() {
        return roleName;
    }
    public void setRoleName(int roleName) {
        this.roleName = roleName;
    }

    
}
