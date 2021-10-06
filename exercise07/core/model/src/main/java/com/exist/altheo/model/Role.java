package com.exist.altheo.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
    int roleId;
    String roleName;

    List<Person> persons;

    public Role(){
       
    }
    public Role(String roleName){
        this.roleName = roleName;
    }

    public Role(String roleName, List<Person> persons) {
        this.roleName = roleName;
        this.persons = persons;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    
}
