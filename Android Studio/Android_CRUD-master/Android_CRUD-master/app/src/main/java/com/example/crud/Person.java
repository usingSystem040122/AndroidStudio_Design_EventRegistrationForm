package com.example.crud;

public class Person {
    private long id;
    private String name;
    private String email;

   public Person(long id,String name,String email)
    {
        this.id = id;
        this.setName(name);
        this.setEmail(email);
    }
    public Person(String name, String email) {
        this.id = -1;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return name + " (" + email + ")";
    }
}
