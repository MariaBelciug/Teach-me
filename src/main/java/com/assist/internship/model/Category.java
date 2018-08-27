package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category", schema = "public")
public class Category implements ResponseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    int id;

    @Column(name="name")
    String name;
}
