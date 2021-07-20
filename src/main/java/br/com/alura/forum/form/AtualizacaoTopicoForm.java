package br.com.alura.forum.form;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AtualizacaoTopicoForm {
    @NotBlank
    @Size(min = 5)
    private String titulo;
    @NotBlank
    @Size(min = 10)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(TopicoRepository topicoRepository, Long id) {

        Topico topico = topicoRepository.findById(id).get();
        if(this.titulo != null) topico.setTitulo(this.titulo);
        if(this.mensagem != null) topico.setMensagem(this.mensagem);
        return topicoRepository.save(topico);
    }
}
