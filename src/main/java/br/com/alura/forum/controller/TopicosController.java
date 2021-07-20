package br.com.alura.forum.controller;

import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizacaoTopicoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {

        if(nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.toModelList(topicos);
        }
//        List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
//        return TopicoDto.toModelList(topicos);

        List<Topico> topicos = topicoRepository.carregarPorNomeDoCurso(nomeCurso);
        return TopicoDto.toModelList(topicos);
    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(
            @Valid @RequestBody TopicoForm topicoForm,
            UriComponentsBuilder uriBuilder
    ) {
        Topico topico = topicoForm.toModel(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> buscar(@PathVariable Long id) {

        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new DetalhesTopicoDto(topico.get()));

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizacaoTopicoForm form
    ) {
        Topico topico = form.atualizar(topicoRepository, id);

        return ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
