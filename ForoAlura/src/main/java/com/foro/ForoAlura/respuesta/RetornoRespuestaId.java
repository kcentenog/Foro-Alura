package com.foro.ForoAlura.respuesta;

import com.foro.ForoAlura.topicos.RespuestaTopico;
import com.foro.ForoAlura.usuarios.DatosRespuestaUsuario;

public record RetornoRespuestaId(String mensaje, RespuestaTopico topico, String fechaCreacion, DatosRespuestaUsuario autor, String solucion) {

    public RetornoRespuestaId(Respuesta respuesta) {
        this(respuesta.getMensaje(), new RespuestaTopico(respuesta.getTopico()), respuesta.getFechaCreacion().toString(),
                new DatosRespuestaUsuario(respuesta.getAutor()), respuesta.getSolucion().toString());
    }
}