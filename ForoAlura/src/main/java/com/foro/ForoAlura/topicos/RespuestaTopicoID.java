package com.foro.ForoAlura.topicos;

import com.foro.ForoAlura.cursos.RespuestaCurso;
import com.foro.ForoAlura.usuarios.DatosRespuestaUsuario;

public record RespuestaTopicoID(Long id, String titulo, String mensaje, String fechaCreacion, String estado, DatosRespuestaUsuario autor,
                                RespuestaCurso curso) {

    public RespuestaTopicoID(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion().toString(),
                topico.getEstado().toString(), new DatosRespuestaUsuario(topico.getAutor()),
                new RespuestaCurso(topico.getCurso()));
    }
}
