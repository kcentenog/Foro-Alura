package com.foro.ForoAlura.controller;

import com.foro.ForoAlura.respuesta.*;
import com.foro.ForoAlura.topicos.Estado;
import com.foro.ForoAlura.topicos.Topico;
import com.foro.ForoAlura.topicos.TopicoRepositorio;
import com.foro.ForoAlura.usuarios.Usuario;
import com.foro.ForoAlura.usuarios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaRepositorio respuestaRepositorio;
    private final TopicoRepositorio topicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public RespuestaController(RespuestaRepositorio respuestaRepository, TopicoRepositorio topicoRepository, UsuarioRepositorio usuarioRepository) {
        this.respuestaRepositorio = respuestaRepository;
        this.topicoRepositorio = topicoRepository;
        this.usuarioRepositorio = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<RetornoRespuesta> registrar(@RequestBody RegistroRespuesta datosRegistro, UriComponentsBuilder uri) {
        Topico topico = (Topico) topicoRepositorio.getReferenceById(datosRegistro.topicoId());
        if (topico.getEstado() == Estado.CERRADO) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Usuario autor = usuarioRepositorio.getReferenceById(datosRegistro.autorId());
        Respuesta respuesta = (Respuesta) respuestaRepositorio.save(new Respuesta(datosRegistro, topico, autor));
        topico.agregarRespuesta(respuesta);
        RetornoRespuesta datosRespuesta = new RetornoRespuesta(respuesta);
        URI url = uri.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoRespuesta>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok((Page<ListadoRespuesta>) respuestaRepositorio.findAll().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetornoRespuestaId> retornaDatos(@PathVariable Long id) {
        Respuesta respuesta = (Respuesta) respuestaRepositorio.getReferenceById(id);
        return ResponseEntity.ok(new RetornoRespuestaId(respuesta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RetornoRespuesta> actualizar(@RequestBody ActualizarRespuesta datosActualizar) {
        Respuesta respuesta = (Respuesta) respuestaRepositorio.getReferenceById(datosActualizar.id());

        Topico topico = (Topico) topicoRepositorio.getReferenceById(datosActualizar.topicoId());

        Usuario autor = usuarioRepositorio.getReferenceById(datosActualizar.autorId());
        respuesta.actualizarDatos(datosActualizar, topico, autor);
        return ResponseEntity.ok( new RetornoRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Object respuesta = respuestaRepositorio.getReferenceById(id);

        respuestaRepositorio.delete(respuesta);

        return ResponseEntity.noContent().build();
    }
}
