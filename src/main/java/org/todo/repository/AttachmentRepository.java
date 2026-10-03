package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.pojos.Attachment;
import org.todo.pojos.Note;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    List<Attachment> findByNote(Note note);

    Optional<Attachment> findByIdAndNote(int id, Note note);
}
