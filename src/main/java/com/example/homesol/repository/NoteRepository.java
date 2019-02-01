package com.example.homesol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.homesol.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}