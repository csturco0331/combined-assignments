package com.cooksys.ftd.assignments.socket.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
    @XmlElement
    private LocalConfig local;

    @XmlElement
    private RemoteConfig remote;

    @XmlElement(name = "student-file-path")
    private String studentFilePath;

    public LocalConfig getLocal() {
        return local;
    }

    public void setLocal(LocalConfig local) {
        this.local = local;
    }

    public RemoteConfig getRemote() {
        return remote;
    }

    public void setRemote(RemoteConfig remote) {
        this.remote = remote;
    }

    public String getStudentFilePath() {
        return studentFilePath;
    }

    public void setStudentFilePath(String studentFilePath) {
        this.studentFilePath = studentFilePath;
    }
}
