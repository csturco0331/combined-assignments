package com.cooksys.ftd.assignments.jdbc.model;

import com.cooksys.ftd.assignments.jdbc.predef.Entity;

public class City implements Entity<Integer> {
    private Integer id;
    private String name;
    private State state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
