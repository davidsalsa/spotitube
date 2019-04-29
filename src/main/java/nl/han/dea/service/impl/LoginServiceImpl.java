package nl.han.dea.service.impl;


import nl.han.dea.model.Login;
import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.LoginService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginServiceImpl implements LoginService{

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse getLogin(LoginRequest loginRequest){
        try{
            Login login = new Login("Meron Brouwer", "MySuperSecretPassword12341", "1234-1234-1234");
            return new LoginResponse(login.getUser(), login.getToken());
        }catch(Exception e){
            return null;
        }

    }
}