package com.foro.ForoAlura.cursos;

public record RespuestaCurso(String nombre, String categoria) {

    public RespuestaCurso(Curso curso) {
        this(curso.getNombre(), curso.getCategoria());
    }
}

