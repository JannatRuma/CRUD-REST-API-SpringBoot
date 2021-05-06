package com.example.crudrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="STORY_TABLE")
public class Story {
    @Id
    @GeneratedValue
    private int id;
    @NotNull(message = "Title can not be null")
    private String title;
    @NotNull(message = "Description can not be null")
    private String description;
    private String slug;
    @NotNull(message = "Author Username must be included")
    private String authorUsername;
}
