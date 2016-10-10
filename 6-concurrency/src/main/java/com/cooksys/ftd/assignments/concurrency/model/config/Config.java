package com.cooksys.ftd.assignments.concurrency.model.config;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.annotation.*;
import java.nio.file.Path;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {

    /**
     * Loads a {@link Config} object from the given xml file path
     *
     * @param path the path at which an xml configuration can be found
     * @return the unmarshalled {@link Config} object
     */
    public static Config load(Path path) {
        throw new NotImplementedException();
    }

    /**
     * server configuration
     */
    private ServerConfig server;

    /**
     * client configuration
     */
    private ClientConfig client;

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

    public ClientConfig getClient() {
        return client;
    }

    public void setClient(ClientConfig client) {
        this.client = client;
    }
}
