import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tela_Principal extends JFrame {
    private JPanel painelCentro;
    private Biblioteca biblioteca;
    private JLabel lblUsuarioLogado;

    private Usuario usuarioLogado = null;
    private boolean isAdmin = false;
    private ArrayList<Usuario> todosUsuarios = new ArrayList<>();

    public Tela_Principal() {
        this.biblioteca = new Biblioteca();
        this.setTitle("Sistema de Biblioteca");
        this.setSize(800, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.criarInteface();
    }

    private void criarInteface() {
        this.setLayout(new BorderLayout());

        JPanel menuLateral = new JPanel();
        menuLateral.setBackground(new Color(44, 62, 80));
        menuLateral.setPreferredSize(new Dimension(220, 600));
        menuLateral.setLayout(new BorderLayout());

        JPanel painelTopoMenu = new JPanel();
        painelTopoMenu.setBackground(new Color(44, 62, 80));
        painelTopoMenu.setLayout(new BoxLayout(painelTopoMenu, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Biblioteca Online");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblUsuarioLogado = new JLabel("Usuário: Não conectado");
        lblUsuarioLogado.setForeground(new Color(189, 195, 199));
        lblUsuarioLogado.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblUsuarioLogado.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnBiblioteca = estilizarBotao("Acessar Biblioteca");
        JButton btnLivro = estilizarBotao("Livro");
        JButton btnEmprestimo = estilizarBotao("Emprestimos");

        painelTopoMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        painelTopoMenu.add(lblTitulo);
        painelTopoMenu.add(Box.createRigidArea(new Dimension(0, 5)));
        painelTopoMenu.add(lblUsuarioLogado);
        painelTopoMenu.add(Box.createRigidArea(new Dimension(0, 30)));
        painelTopoMenu.add(btnBiblioteca);
        painelTopoMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        painelTopoMenu.add(btnLivro);
        painelTopoMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        painelTopoMenu.add(btnEmprestimo);

        JPanel painelRodapeMenu = new JPanel();
        painelRodapeMenu.setBackground(new Color(44, 62, 80));
        painelRodapeMenu.setLayout(new BoxLayout(painelRodapeMenu, BoxLayout.Y_AXIS));
        painelRodapeMenu.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));

        JButton btnLogin = estilizarBotao("Login");
        JButton btnSair = estilizarBotao("Sair");

        Dimension tamanhoBotoesRodape = new Dimension(130, 35);
        btnLogin.setMaximumSize(tamanhoBotoesRodape);
        btnSair.setMaximumSize(tamanhoBotoesRodape);

        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSair.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelRodapeMenu.add(btnLogin);
        painelRodapeMenu.add(Box.createRigidArea(new Dimension(0, 8)));
        painelRodapeMenu.add(btnSair);

        menuLateral.add(painelTopoMenu, BorderLayout.NORTH);
        menuLateral.add(painelRodapeMenu, BorderLayout.SOUTH);

        painelCentro = new JPanel();
        painelCentro.setBackground(new Color(245, 246, 250));
        painelCentro.setLayout(new BorderLayout());

        mostrarTelaBoasVindas();

        btnBiblioteca.addActionListener(e -> trocarTela(abrirBiblioteca()));
        btnLivro.addActionListener(e -> trocarTela(telaLivro()));
        btnLogin.addActionListener(e -> trocarTela(telaLogin()));
        btnEmprestimo.addActionListener(e -> trocarTela(telaEmprestimo()));
        btnSair.addActionListener(e -> System.exit(0));

        this.add(menuLateral, BorderLayout.WEST);
        this.add(painelCentro, BorderLayout.CENTER);
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public boolean isAdmin() { return isAdmin; }
    public ArrayList<Usuario> getTodosUsuarios() { return todosUsuarios; }

    public void conectarUsuario(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.isAdmin = false;

        if (!todosUsuarios.contains(usuario)) {
            todosUsuarios.add(usuario);
        }

        lblUsuarioLogado.setText("Usuários: " + usuario.getLogin());
        lblUsuarioLogado.setForeground(new Color(46, 204, 113));
    }

    private void trocarTela(JPanel novaTela) {
        painelCentro.removeAll();
        painelCentro.add(novaTela, BorderLayout.CENTER);
        painelCentro.revalidate();
        painelCentro.repaint();
    }

    private void mostrarTelaBoasVindas() {
        JPanel painelBoasVindas = new JPanel(new GridBagLayout());
        painelBoasVindas.setBackground(new Color(245, 246, 250));

        JLabel lblBoasVindas = new JLabel("Bem-vindo ao Sistema!");
        lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBoasVindas.setForeground(new Color(53, 59, 72));

        painelBoasVindas.add(lblBoasVindas);
        painelCentro.add(painelBoasVindas, BorderLayout.CENTER);
    }

    private JButton estilizarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(52, 152, 219));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setMaximumSize(new Dimension(180, 40));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        return botao;
    }

    public void atualizarUsuarioLogado(String nomeUsuario) {
        if (nomeUsuario.contains("Administrador")) {
            this.isAdmin = true;
            this.usuarioLogado = null;
        }
        if (lblUsuarioLogado != null) {
            lblUsuarioLogado.setText("Usuário: " + nomeUsuario);
            lblUsuarioLogado.setForeground(new Color(46, 204, 113));
        }
    }

    private JPanel abrirBiblioteca() {
        return new Tela_Biblioteca(this.biblioteca);
    }

    private JPanel telaLivro() {
        return new Tela_Livro(this.biblioteca);
    }

    private JPanel telaLogin() {
        return new Tela_Login(this);
    }

    private JPanel telaEmprestimo() {
        return new Tela_Emprestimo(this, this.biblioteca);
    }
}