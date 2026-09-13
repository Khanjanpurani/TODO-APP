package org.todo.pojos;



public class Note {


    //id
    //title
    //content
    //emoji
    //createdAt
    private int id;
    private String title;
    private String content;
    private long createdAt;
    private String emoji;

    public Note(int id, String title, String content, long createdAt, String emoji) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.emoji = emoji;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
