package com.foro.ForoAlura.topicos;

public record ListadoTopico(Long id, String titulo, String mensaje, String autor, String curso) {

    public  ListadoTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}

