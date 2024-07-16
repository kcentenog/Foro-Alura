package com.foro.ForoAlura.respuesta;

public record RetornoRespuesta(String mensaje, String topico, String autor) {

    public RetornoRespuesta(Respuesta respuesta) {
        this(respuesta.getMensaje(), respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre());
    }
}
