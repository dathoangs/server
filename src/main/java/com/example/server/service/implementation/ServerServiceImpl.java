package com.example.server.service.implementation;

import com.example.server.enumeration.Status;
import com.example.server.model.Server;
import com.example.server.repo.ServerRepo;
import com.example.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server update(Long id, Server serverUpdate) {
        Optional<Server> serverData =  serverRepo.findById(id);

        if (serverData.isPresent()){
            Server server = serverData.get();
            if (serverUpdate.getType() != null)
                server.setType(serverUpdate.getType());
            if (serverUpdate.getType() != null)
                server.setMemory(serverUpdate.getMemory());
            if (serverUpdate.getType() != null)
                server.setName(serverUpdate.getName());
            if (serverUpdate.getType() != null)
                server.setStatus(serverUpdate.getStatus());
            return serverRepo.save(server);
        } else {
            return serverRepo.findById(id).get();
        }
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fectching data");
        return serverRepo.findAll(of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

    @Override
    public Boolean report() throws FileNotFoundException, JRException {
        String path = "e:\\report";
        List<Server> serverList = serverRepo.findAll();
        File file = ResourceUtils.getFile("classpath:article.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(serverList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "My self");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(print, path+"\\article.pdf");
        return true;
    }
}
