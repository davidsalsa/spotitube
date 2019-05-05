package nl.han.dea.service;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.impl.LoginServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private Data data;

    private LoginRequest loginRequest;
    private LoginResponse expected;
    private Login login;

    @Before
    public void setUp(){
        String password = "test";
        String token = "token";
        String user = "test";
        login = new Login(user, password, token);
        loginRequest = new LoginRequest(user, password);
        expected = new LoginResponse(user, token);
    }

    @Test
    public void shouldReturnLoginResponse(){
        when(data.getLogin(loginRequest.getUser(), loginRequest.getPassword())).thenReturn(login);
        LoginResponse actual = loginService.getLogin(loginRequest);

        assertEquals(expected.getUser(), actual.getUser()); //checks on name
        assertEquals(expected.getToken(),actual.getToken()); //checks on token
        assertEquals(expected.getClass(),actual.getClass()); //checks on class
    }

}
