package com.example.crudrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(length = 3000)
    private String description;
    @NotNull(message = "Author Username must be included")
    private String authorUsername;

    public Story(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
