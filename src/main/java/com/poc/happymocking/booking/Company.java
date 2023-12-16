package com.poc.happymocking.booking;

import java.util.ArrayList;
import java.util.List;

public class Company {
    String name;
    List<Company> children;

    public Company(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }
    void populateChildren(int depth) {
        if (depth > 5) {
            return; // base case to stop recursion
        }
        Company child = new Company("Child of " + this.name);
        this.children.add(child);
        child.populateChildren(depth+1);
    }
}