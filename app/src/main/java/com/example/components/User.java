package com.example.components;

public class User implements Comparable<User> {
    private String name;
    private Integer score;

    public User(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public int compareTo(User p) {
        return this.score.compareTo(p.score);
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }
}
