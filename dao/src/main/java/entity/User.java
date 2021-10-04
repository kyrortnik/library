package entity;

public class User {

    private long id;
    private  String login;
    private  String password;
    private  String role;

    public User(){}

    public User(long id){
        this.id = id;
    }
    public User(long id,String login,String password){
        this.id = id;
        this.login = login;
        this.password = password;
    }
    public User(long id,String login,String password,String role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int hashCode(){
        int result = (int)id;
        result = result * 31 + (login != null ? login.hashCode() : 0);
        result = result * 31 + (password != null ? password.hashCode() : 0);
        result = result * 31 + (role != null ? role.hashCode() : 0);
        return result;

    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User that = (User) o;
        if (id != that.id) return false;
       if (login != null ? !login.equals(that.login) : that.login != null) return false;
       if (password != null ? !password.equals(that.password) : that.password != null) return false;
       return  role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
