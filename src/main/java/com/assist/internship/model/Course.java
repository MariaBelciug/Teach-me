package com.assist.internship.model;

import com.assist.internship.helpers.ResponseObject;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course", schema = "public")
public class Course implements ResponseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "small_description")
    String smallDescription;

    @Column(name = "long_description")
    String longDescription;

    //divided by ';'
    @Column(name = "tags")
    String tags;

    //divided by ';'
    @Column(name = "images")
    String images;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", referencedColumnName="id")
    Category category;
}
