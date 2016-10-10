package com.cooksys.ftd.assignments.concurrency.model.config;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClientConfig {

    /**
     * Indicates if this client is disabled or not. Defaults to {@code false}.
     */
    @XmlAttribute
    private boolean disabled = false;

    /**
     * The port this client should connect on. Defaults to {@code 8080}.
     */
    private int port = 8080;

    /**
     * The host this client should connect to. Defaults to {@code "localhost"}.
     */
    private String host = "localhost";

    /**
     * The maximum number of instances this client should have spawned at any given time.
     * If the value is negative, there is no limit to the number of instances spawned.
     * Defaults to {@code -1}.
     */
    @XmlElement(name = "max-instances")
    private int maxInstances = -1;

    /**
     * The strategy to use when spawning client instances.
     *
     * If {@code SpawnStrategy.NONE}, no instances should be spawned.
     *
     * If {@code SpawnStrategy.PARALLEL}, up to the maximum number of allowed instances
     * should be spawned in parallel. Once this group has all ended, the remaining instances
     * should be spawned in the same way, until all instances have ended.
     *
     * If {@code SpawnStrategy.SEQUENTIAL}, each instance should be spawned
     * in turn, waiting on the end of the previous instance before spawning the next.
     *
     * Defaults to {@code SpawnStrategy.NONE}.
     */
    @XmlElement(name = "spawn-strategy")
    private SpawnStrategy spawnStrategy = SpawnStrategy.NONE;

    /**
     * The list of client instances to spawn and their associated configurations.
     */
    @XmlElement(name = "instance")
    @XmlElementWrapper(name = "instances")
    private List<ClientInstanceConfig> instances;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxInstances() {
        return maxInstances;
    }

    public void setMaxInstances(int maxInstances) {
        this.maxInstances = maxInstances;
    }

    public SpawnStrategy getSpawnStrategy() {
        return spawnStrategy;
    }

    public void setSpawnStrategy(SpawnStrategy spawnStrategy) {
        this.spawnStrategy = spawnStrategy;
    }

    public List<ClientInstanceConfig> getInstances() {
        return instances;
    }

    public void setInstances(List<ClientInstanceConfig> instances) {
        this.instances = instances;
    }
}
