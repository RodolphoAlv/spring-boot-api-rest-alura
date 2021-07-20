package br.com.alura.forum.controller;

import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizacaoTopicoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public Page<TopicoDto> lista(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {

        if(nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(pageable);
            return TopicoDto.toModelPage(topicos);
        }
        Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, pageable);
        return TopicoDto.toModelPage(topicos);
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
        return topico.map(present -> ResponseEntity.status(HttpStatus.OK).body(new DetalhesTopicoDto(present)))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
