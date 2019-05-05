package nl.han.dea.model.response;

public class LoginResponse {
    public String token;
    public String user;

    public LoginResponse(String user, String token){
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
