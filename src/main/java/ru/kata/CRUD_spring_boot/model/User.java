package ru.kata.CRUD_spring_boot.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    private int version;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;
    private int age;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public String getSecondName() {
        return secondName;
    }

    public User() {
    }

    public User(String firstName, String secondName, int age) {
        this.firstName = firstName;
        this.age = age;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{id=").append(id).append(", firstName=").append(firstName)
                .append(", secondName=").append(secondName)
                .append(", age=").append(age).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if ((o == null) || (o.getClass() != User.class)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return (firstName.equals(((User) o).getFirstName())
                && secondName.equals(((User) o).getSecondName())
                && age == ((User) o).getAge());
    }
}

