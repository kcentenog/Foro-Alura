package com.foro.ForoAlura.respuesta;

public record ListadoRespuesta(Long id, String mensaje, String topico, String autor) {

    public  ListadoRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre());
    }
}
