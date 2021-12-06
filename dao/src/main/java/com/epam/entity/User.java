package com.epam.entity;

import java.util.Arrays;

public class User extends Entity {

    private  String login;
    private  char[] password;
    private  String role;
    private  String salt;

    public User(){}

    public User(long id){
        super(id);
    }
    public User(String login){
        this.login = login;
    }
    public User(long id,String login,char[] password){
        super(id);
        this.login = login;
        this.password = password;
    }
    public User(String login,char[] password,String role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(long id,String login,char[] password,String role){
       super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, char[] password, String role, String salt) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !Arrays.equals(password, user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return salt != null ? salt.equals(user.salt) : user.salt == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? Arrays.hashCode(password) : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + Arrays.toString(password) + '\'' +
                ", role='" + role + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
