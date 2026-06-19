import java.util.ArrayList;

public class Usuario {
    private String login;
    private String senha;
    private ArrayList<Emprestimo> meusEmprestimos;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.meusEmprestimos = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public ArrayList<Emprestimo> getMeusEmprestimos() {
        return meusEmprestimos;
    }

}
