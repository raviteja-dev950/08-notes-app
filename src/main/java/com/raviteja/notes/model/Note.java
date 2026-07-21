package com.raviteja.notes.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String toFileString() {
        return "Title: " + title + "\n" +
               "Created: " + createdAt.format(FMT) + "\n" +
               "Updated: " + updatedAt.format(FMT) + "\n" +
               "--------------------------------\n" +
               content;
    }

    @Override
    public String toString() {
        return String.format("📌 %-20s | %s | %s chars", title, updatedAt.format(FMT), content.length());
    }
}