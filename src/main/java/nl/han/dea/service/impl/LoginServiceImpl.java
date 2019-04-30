package nl.han.dea.service.impl;

import nl.han.dea.model.Login;
import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.LoginService;

import javax.inject.Named;

@Named("LoginServiceImpl")
public class LoginServiceImpl implements LoginService{
    @Override
    public LoginResponse getLogin(LoginRequest loginRequest){
        try{
            Login login = new Login(loginRequest.getUser(), loginRequest.getPassword(), "1234-1234");
            return new LoginResponse(login.getUser(), login.getToken());
        }catch(Exception e){
            return null;
        }

    }
}
