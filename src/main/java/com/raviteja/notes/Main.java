package com.raviteja.notes;

import com.raviteja.notes.model.Note;
import com.raviteja.notes.util.NoteUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== RaviTeja's Notes App v1 | Project 8 ===");
        System.out.println("Notes Folder: " + NoteUtil.getDir() + "\n");

        while (true) {
            System.out.println("--- NOTES MENU ---");
            System.out.println("1. Create New Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Read Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Search Notes");
            System.out.println("q. Quit");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            try {
                switch (c) {
                    case "1" -> createNote();
                    case "2" -> viewAll();
                    case "3" -> readNote();
                    case "4" -> deleteNote();
                    case "5" -> search();
                    case "q", "Q" -> { System.out.println("Project 8 Notes App Done! Bye!"); return; }
                    default -> System.out.println("Invalid!");
                }
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
            System.out.println();
        }
    }

    private static void createNote() throws Exception {
        System.out.print("Title: "); String title = sc.nextLine().trim();
        if (title.isEmpty()) { System.out.println("Title required!"); return; }
        System.out.println("Content (type END in new line to finish):");
        StringBuilder sb = new StringBuilder();
        while (true) { String line = sc.nextLine(); if (line.equals("END")) break; sb.append(line).append("\n"); }
        Note note = new Note(title, sb.toString().trim());
        NoteUtil.saveNote(note);
    }

    private static void viewAll() throws Exception {
        List<Note> notes = NoteUtil.getAllNotes();
        if (notes.isEmpty()) { System.out.println("No notes yet!"); return; }
        System.out.println("\nTotal Notes: " + notes.size());
        System.out.println("------------------------------------------------------------");
        notes.forEach(System.out::println);
        System.out.println("------------------------------------------------------------");
    }

    private static void readNote() throws Exception {
        System.out.print("Enter Title: "); String t = sc.nextLine().trim();
        System.out.println("\n" + NoteUtil.readNote(t));
    }

    private static void deleteNote() throws Exception {
        System.out.print("Enter Title to Delete: "); String t = sc.nextLine().trim();
        NoteUtil.deleteNote(t);
    }

    private static void search() throws Exception {
        System.out.print("Keyword: "); String k = sc.nextLine().trim();
        List<Note> res = NoteUtil.searchNotes(k);
        if (res.isEmpty()) System.out.println("No match for: " + k);
        else { System.out.println("Found " + res.size() + " notes:"); res.forEach(System.out::println); }
    }
}