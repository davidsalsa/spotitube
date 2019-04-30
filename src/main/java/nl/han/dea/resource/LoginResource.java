package nl.han.dea.resource;

import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.service.LoginService;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@JsonSerialize

@Path("/login")
public class LoginResource{

    @Inject
    @Named("LoginServiceImpl")
    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogin(LoginRequest loginRequest){
        try{
            LoginResponse loginResponse = loginService.getLogin(loginRequest);
            if(loginResponse.token.length() < 1){
                throw new SQLException("Gebruiker is niet gevonden");
            }
            return Response.status(201).entity(loginResponse).build();
        }catch(Exception e){
            return Response.status(401).build();
        }

    }
}