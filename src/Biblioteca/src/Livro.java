public class Livro {
    private int codigo;
    private String titulo;
    private String descricao;
    private String autor;
    private int ano;

    public Livro(int codigo, String titulo, String autor, int ano, String descricao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.ano = ano;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " | " + autor + " | " + ano + " -> " + descricao;
    }
}
