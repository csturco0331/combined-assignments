package com.cooksys.ftd.assignments.concurrency.model.config;

import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClientInstanceConfig {

    /**
     * The delay between requests, in milliseconds. If negative, requests follow each other instantly.
     *
     * Defaults to {@code -1}
     */
    @XmlAttribute
    private int delay = -1;

    /**
     * The requests to send, in order, represented as {@link Request} values.
     */
    @XmlElement(name = "request")
    private List<Request> requests;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
