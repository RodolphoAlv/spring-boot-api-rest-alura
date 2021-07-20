package br.com.alura.forum.config.validacao;

public class ErroForm {
    private String campo;
    private String erro;

    public ErroForm campo(String campo) {
        this.campo = campo;
        return this;
    }

    public ErroForm erro(String erro) {
        this.erro = erro;
        return this;
    }

    public static ErroForm build() {
        return new ErroForm();
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
