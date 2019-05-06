package nl.han.dea.service;

import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;

public interface LoginService {
    LoginResponse getLogin(LoginRequest loginRequest);
}
