package com.example.server.service;

import com.example.server.model.Server;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface ServerService {

    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Long id, Server server);
    Server ping(String ipAddress) throws IOException;
    Boolean delete(Long id);
    Boolean report() throws FileNotFoundException, JRException;

}
