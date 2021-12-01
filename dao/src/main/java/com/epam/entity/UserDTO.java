package com.epam.entity;

public class UserDTO extends Entity {

    private String login;
    private String role;

    public UserDTO() {
    }


    public UserDTO(Long id, String login, String role) {
        super(id);
        this.login = login;
        this.role = role;
    }

    public UserDTO(String login, String role) {
        this.login = login;
        this.role = role;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (login != null ? !login.equals(userDTO.login) : userDTO.login != null) return false;
        return role != null ? role.equals(userDTO.role) : userDTO.role == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
