package com.cooksys.ftd.assignments.concurrency.model.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServerConfig {

    /**
     * Indicates if this server is disabled or not. Defaults to {@code false}.
     */
    @XmlAttribute
    private boolean disabled = false;

    /**
     * The port this server should listen on. Defaults to {@code 8080}.
     */
    private int port = 8080;

    /**
     * The maximum number of clients this server should handle concurrently.
     * If the value is negative, there is no limit to the number of clients handled concurrently.
     * Defaults to {@code -1}.
     */
    @XmlElement(name = "max-clients")
    private int maxClients;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }
}

