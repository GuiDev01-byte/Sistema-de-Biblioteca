import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Alterado para JPanel para rodar dentro da sua Tela_Principal
public class Tela_Biblioteca extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAno;
    private JTextField txtDescricao;
    private int qtdLivros = 0;

    private JTextArea areaLivros;
    private Biblioteca biblioteca;

    public Tela_Biblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setBackground(new Color(245, 246, 250));

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridLayout(6, 2, 10, 10));
        painelFormulario.setBackground(new Color(245, 246, 250));

        painelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        painelFormulario.add(txtCodigo);

        painelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelFormulario.add(txtTitulo);

        painelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        painelFormulario.add(txtAutor);

        painelFormulario.add(new JLabel("Ano:"));
        txtAno = new JTextField();
        painelFormulario.add(txtAno);

        painelFormulario.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelFormulario.add(txtDescricao);

        JButton btnCadastrar = new JButton("Cadastrar Livro");
        JButton btnListar = new JButton("Listar Livros");

        painelFormulario.add(btnCadastrar);
        painelFormulario.add(btnListar);

        areaLivros = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaLivros);

        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setBackground(new Color(245, 246, 250));
        painelEsquerdo.setPreferredSize(new Dimension(300, 400));
        painelEsquerdo.add(painelFormulario, BorderLayout.NORTH);

        this.add(painelEsquerdo, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrarLivro());
        btnListar.addActionListener(e -> listarLivros());
    }

    private void cadastrarLivro() {
        int codigo = Integer.parseInt(txtCodigo.getText());
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int ano = Integer.parseInt(txtAno.getText());
        String descricao = txtDescricao.getText();

        Livro livro = new Livro(codigo, titulo, autor, ano, descricao);
        biblioteca.adicionarLivro(livro);

        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        qtdLivros = qtdLivros +1;
        limparCampos();
    }

    private void listarLivros() {
        areaLivros.setText("");
        for (Livro livro : biblioteca.getLivros()) {
            areaLivros.append(livro + "\n");
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAno.setText("");
        txtDescricao.setText("");
    }
}