import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo {
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private String status;

    public Emprestimo(Livro livro, Usuario usuario) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataRetirada = LocalDate.now();
        this.status = "Em andamento";
    }

    public void devolverLivro() {
        this.dataDevolucao = LocalDate.now();
        this.status = "Devolvido";
    }

    public Livro getLivro() {
        return livro;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String retiradaStr = dataRetirada.format(fmt);
        String devolucaoStr = (dataDevolucao != null) ? dataDevolucao.format(fmt) : "00/00/0000";

        return "Livro: " + livro.getTitulo() + " (Cód: " + livro.getCodigo() + ")" +
        "\nRetirada em: " + retiradaStr + "\nDevolvida em: " + devolucaoStr + "\nStatus: " + status;
    }
}
