package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Track;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Named("TracksServiceImpl")
public class TracksServiceImpl implements TracksService {

    @Inject
    //@Named("MysqlConnection")
    @Named("MySQLConnection")
    Data data;

    @Override
    public TracksResponse getTracks(String token, int forPlaylist) {
        return new TracksResponse(data.getTracks(token, forPlaylist));
    }
}
