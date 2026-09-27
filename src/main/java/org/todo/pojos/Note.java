package org.todo.pojos;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity//this tells spring hibernate to manage it as tabel schema and class name is table name
public class Note {


    @Id//Tells this is primary key in table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//For auto incrementing the id
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String emoji;
    private boolean pinned;

    //Hibernate requires no args constructor for themselves to create  the object
    public Note() {
    }

    public Note( String title, String content, String emoji) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.emoji = emoji;
        this.pinned = false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
