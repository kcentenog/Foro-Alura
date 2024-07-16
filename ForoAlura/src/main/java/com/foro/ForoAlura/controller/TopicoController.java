package com.foro.ForoAlura.controller;

import com.foro.ForoAlura.cursos.Curso;
import com.foro.ForoAlura.cursos.CursoRepositorio;
import com.foro.ForoAlura.topicos.*;
import com.foro.ForoAlura.usuarios.Usuario;
import com.foro.ForoAlura.usuarios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepositorio topicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CursoRepositorio cursoRepositorio;

    public TopicoController(TopicoRepositorio topicoRepository, UsuarioRepositorio usuarioRepository, CursoRepositorio cursoRepository) {
        this.topicoRepositorio = topicoRepository;
        this.usuarioRepositorio = usuarioRepository;
        this.cursoRepositorio = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<RespuestaTopico> registrar(@RequestBody @Valid RegistroTopico datosRegistro, UriComponentsBuilder uri) {
        Usuario autor = usuarioRepositorio.getReferenceById(datosRegistro.autorId());
        Curso curso = (Curso) cursoRepositorio.getReferenceById(datosRegistro.cursoId());
        Topico topico = (Topico) topicoRepositorio.save(new Topico(datosRegistro, autor, curso));
        RespuestaTopico datosRespuesta = new RespuestaTopico(topico);
        URI url = uri.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTopico>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok((Page<ListadoTopico>) topicoRepositorio.findAll().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoID> retornaDatos(@PathVariable Long id) {
        Topico topico = (Topico) topicoRepositorio.getReferenceById(id);
        return ResponseEntity.ok(new RespuestaTopicoID(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RespuestaTopico> actualizar(@RequestBody @Valid ActualizarTopico datosActualizar) {
        Usuario autor = usuarioRepositorio.getReferenceById(datosActualizar.autorId());
        Curso curso = (Curso) cursoRepositorio.getReferenceById(datosActualizar.cursoId());
        Topico topico = (Topico) topicoRepositorio.getReferenceById(datosActualizar.id());
        topico.actualizarDatos(datosActualizar, autor, curso);
        return ResponseEntity.ok( new RespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Topico topico = (Topico) topicoRepositorio.getReferenceById(id);
        //topicoRepository.delete(topico);
        topico.cerrarTopico();
        return ResponseEntity.noContent().build();
    }
}
