package nl.han.dea.model.response;

public class LoginResponse {
    public String token;
    public String user;

    public LoginResponse(){}
    public LoginResponse(String user, String token){
        this.token = token;
        this.user = user;
    }
}
