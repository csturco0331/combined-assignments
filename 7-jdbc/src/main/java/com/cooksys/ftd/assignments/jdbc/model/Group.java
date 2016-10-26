package com.cooksys.ftd.assignments.jdbc.model;

import com.cooksys.ftd.assignments.jdbc.predef.Entity;

import java.util.Set;

public class Group implements Entity<Integer> {
    private Integer id;
    private String name;
    private City city;
    private Interest interest;
    private Set<Person> members;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }
}
