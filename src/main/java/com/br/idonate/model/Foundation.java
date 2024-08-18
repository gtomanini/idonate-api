package com.br.idonate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "foundations")
@Getter
@Setter
public class Foundation extends BaseModel{

    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String contact;
    private Float latitude;
    private Float longitude;



    @ManyToMany
    @JoinTable(name = "foundations_items",
    joinColumns = @JoinColumn(name = "foundation_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

}
