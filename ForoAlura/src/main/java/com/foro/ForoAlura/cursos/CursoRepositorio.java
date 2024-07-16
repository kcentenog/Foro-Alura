package com.foro.ForoAlura.cursos;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface CursoRepositorio extends JpaRepository {
    Page<Curso> findAllByActivoTrue(Pageable paginacion);
}
