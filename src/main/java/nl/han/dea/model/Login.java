package nl.han.dea.model;

public class Login {
    private String user;
    private String password;
    private String token;

    public Login(String user, String password, String token) {
        this.user = user;
        this.password = password;
        this.token = token;
    }

    public Login() {
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
