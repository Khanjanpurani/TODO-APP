package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.pojos.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
