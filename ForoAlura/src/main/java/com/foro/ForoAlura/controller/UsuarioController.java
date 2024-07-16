package com.foro.ForoAlura.controller;


import com.foro.ForoAlura.usuarios.*;
import jakarta.transaction.Transactional;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.stream.Stream;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioController(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }


    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrar(@RequestBody DatosRegistrarUsuario datosRegistro, UriComponentsBuilder uri) {
        Usuario usuario = usuarioRepositorio.save(new Usuario(datosRegistro));
        DatosRespuestaUsuario datosRespuesta = new DatosRespuestaUsuario(usuario);
        URI url = uri.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Stream<DatosListadoUsuario>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(usuarioRepositorio.findAll().stream().map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuarioId> retornaDatos(@PathVariable Long id) {
        Usuario usuario = usuarioRepositorio.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaUsuarioId(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizar(@RequestBody ActualizarUsuario datosActualizar) {
        Usuario usuario = usuarioRepositorio.getReferenceById(datosActualizar.id());
        usuario.actualizarDatos(datosActualizar);
        return ResponseEntity.ok( new DatosRespuestaUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = usuarioRepositorio.getReferenceById(id);
        usuarioRepositorio.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
