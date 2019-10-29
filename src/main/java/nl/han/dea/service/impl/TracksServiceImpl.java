package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;

import javax.inject.Inject;
import javax.inject.Named;

@Named("TracksServiceImpl")
public class TracksServiceImpl implements TracksService {

    @Inject
    @Named("JPAMySQLConnection")
    //@Named("MSSQLConnection")
    Data data;

    @Override
    public TracksResponse getTracks(String token, int forPlaylist) {
        return new TracksResponse(data.getTracks(token, forPlaylist));
    }
}
