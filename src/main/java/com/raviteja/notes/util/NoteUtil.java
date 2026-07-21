package com.raviteja.notes.util;

import com.raviteja.notes.model.Note;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteUtil {
    private static final String NOTES_DIR = "E:/notes-data/";

    static {
        try { Files.createDirectories(Paths.get(NOTES_DIR)); } catch (Exception e) {}
    }

    public static String getDir() { return NOTES_DIR; }

    public static void saveNote(Note note) throws IOException {
        String safeTitle = note.getTitle().replaceAll("[^a-zA-Z0-9-_ ]", "").trim();
        Path path = Paths.get(NOTES_DIR + safeTitle + ".txt");
        note.setUpdatedAt(LocalDateTime.now());
        if (!Files.exists(path)) note.setCreatedAt(LocalDateTime.now());
        Files.writeString(path, note.toFileString());
        System.out.println("✅ Note saved: " + path);
    }

    public static List<Note> getAllNotes() throws IOException {
        List<Note> notes = new ArrayList<>();
        if (!Files.exists(Paths.get(NOTES_DIR))) return notes;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(NOTES_DIR), "*.txt")) {
            for (Path p : stream) {
                String txt = Files.readString(p);
                String title = p.getFileName().toString().replace(".txt", "");
                String content = txt.contains("--------------------------------\n") ? 
                    txt.substring(txt.indexOf("--------------------------------\n")+33) : txt;
                Note n = new Note(title, content.trim());
                notes.add(n);
            }
        }
        return notes;
    }

    public static String readNote(String title) throws IOException {
        Path path = Paths.get(NOTES_DIR + title + ".txt");
        if (!Files.exists(path)) throw new RuntimeException("Note not found: " + title);
        return Files.readString(path);
    }

    public static void deleteNote(String title) throws IOException {
        Path path = Paths.get(NOTES_DIR + title + ".txt");
        if (Files.deleteIfExists(path)) System.out.println("🗑️ Deleted: " + title);
        else throw new RuntimeException("Not found: " + title);
    }

    public static List<Note> searchNotes(String keyword) throws IOException {
        String kw = keyword.toLowerCase();
        return getAllNotes().stream()
            .filter(n -> n.getTitle().toLowerCase().contains(kw) || 
                         n.getContent().toLowerCase().contains(kw))
            .collect(Collectors.toList());
    }
}