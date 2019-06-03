package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.LoginService;

import javax.inject.Inject;
import javax.inject.Named;

@Named("LoginServiceImpl")
public class LoginServiceImpl implements LoginService{

    @Inject
    @Named("MySQLConnection")
    //@Named("MSSQLConnection")
    Data data;

    @Override
    public LoginResponse getLogin(LoginRequest loginRequest){
        try{
            Login login = data.getLogin(loginRequest.getUser(), loginRequest.getPassword());
            return new LoginResponse(login.getUser(), login.getToken());
        }catch(Exception e){
            return null;
        }

    }
}
