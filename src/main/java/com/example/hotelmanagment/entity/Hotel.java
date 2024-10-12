package com.example.hotelmanagment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> rooms;

    public void addRoom(Room room) {
        if (rooms == null)
            rooms = new ArrayList<>();

        rooms.add(room);
    }

    public void removeRoom(Room room) {
        if (rooms != null && !rooms.isEmpty())
            rooms.remove(room);
    }

}
