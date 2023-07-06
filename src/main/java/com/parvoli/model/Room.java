package com.parvoli.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private char groupLetter;

    @Column
    private int capacity;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Baby> babies;

    @Transient
    private int occupancy;

    @Transient
    private int freePlaces;

    public Room() {
    }

    public Room(char groupLetter, int capacity, Course course, Set<Baby> babies) {
        this.groupLetter = groupLetter;
        this.capacity = capacity;
        this.course = course;
        this.babies = babies;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public char getGroupLetter() {
        return groupLetter;
    }

    public int getCapacity() {
        return capacity;
    }

    public Course getCourse() {
        return course;
    }

    public Set<Baby> getBabies() {
        return babies;
    }

    public int getOccupancy() {
        return getBabies().size();
    }

    public int getFreePlaces() {
        return capacity - occupancy;
    }

    // Setters
    public void setGroupLetter(char groupLetter) {
        this.groupLetter = groupLetter;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
