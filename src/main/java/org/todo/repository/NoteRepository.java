package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.pojos.Note;


// Spring Data JPA scans for interfaces extending JpaRepository at startup.
// It finds this interface and generates a full implementation automatically in memory.
// That implementation is registered as a Spring bean and linked to the NOTE table.
// JpaRepository<Note, Integer> means — manage Note entity whose primary key is Integer.
// We get save(), findById(), findAll(), deleteById(), existsById() etc for free.
public interface NoteRepository extends JpaRepository<Note,Integer> {
}
