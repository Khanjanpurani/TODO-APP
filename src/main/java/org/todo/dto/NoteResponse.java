package org.todo.dto;

import java.time.LocalDateTime;

public class NoteResponse {

    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String emoji;
    private boolean pinned;

    public NoteResponse() {
    }

    public NoteResponse(int id, String title, String content,
                        LocalDateTime createdAt, String emoji,boolean pinned) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.emoji = emoji;
        this.pinned = pinned;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}