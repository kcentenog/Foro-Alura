package com.foro.ForoAlura.usuarios;

public record DatosRespuestaUsuario(String nombre, String email) {

    public DatosRespuestaUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getEmail());
    }
}
