package nl.han.dea.resource;

import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginResourceTest {
    @InjectMocks
    private LoginResource loginResource;

    @Mock
    private LoginService loginService;


    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    @Before
    public void setUp(){
        loginRequest = new LoginRequest("meron", "meron");
        loginResponse = new LoginResponse("meron", "1234-1234-1234");
    }

    @Test
    public void shouldReturnLoginResponseWithStatus200(){
        when(loginService.getLogin(loginRequest)).thenReturn(loginResponse);
        Response response = loginResource.getLogin(loginRequest);

        System.out.println(loginRequest.user);
        System.out.println(loginResponse.token);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnLoginResponseWithStatus401(){
        when(loginService.getLogin(loginRequest)).thenReturn(loginResponse);
        Response response = loginResource.getLogin(null);
        assertEquals(401, response.getStatus());
    }
}
