import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Tela_Livro extends JPanel {

    private JTextField txtPesquisa;
    private JTextArea areaResultado;
    private Biblioteca biblioteca;

    public Tela_Livro(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(245, 246, 250));

        //Inicio da opcao de Pesquisa
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelPesquisa.setBackground(new Color(245, 246, 250));

        painelPesquisa.add(new JLabel("Pesquisar Título:"));
        txtPesquisa = new JTextField(25);
        painelPesquisa.add(txtPesquisa);

        JButton btnBuscar = new JButton("Buscar");
        painelPesquisa.add(btnBuscar);

        this.add(painelPesquisa, BorderLayout.NORTH);
        //Fim de pesquisa

        //Inicio do cuadro de livros listados
        areaResultado = new JTextArea();
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225), 1));

        this.add(scroll, BorderLayout.CENTER);
        //Fim do cuador

        //Inicio dos botoes de Listar e Remover
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelAcoes.setBackground(new Color(245, 246, 250));

        JButton btnLista = new JButton("Listar livros");
        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.setBackground(new Color(231, 76, 60));
        btnRemover.setForeground(Color.WHITE);

        painelAcoes.add(btnLista);
        painelAcoes.add(btnRemover);

        this.add(painelAcoes, BorderLayout.SOUTH);
        //Fim dos botoes

        btnLista.addActionListener(e -> listarLisvros());
        btnBuscar.addActionListener(e -> pesquisarLivro());
        btnRemover.addActionListener(e -> removerLivro());
    }

    private void listarLisvros() {
        areaResultado.setText("");
        if (biblioteca.getLivros().isEmpty()) {
            areaResultado.setText("Nenhum livro cadastrado no sistema.");
            return;
        }

        for (Livro livro : biblioteca.getLivros()) {
            String textoSimples = "Código: " + livro.getCodigo() + "| Titulo: " + livro.getTitulo() + "\n";
            areaResultado.append(textoSimples);
        }
    }

    private void pesquisarLivro() {
        areaResultado.setText("");
        String Busca = txtPesquisa.getText().trim().toLowerCase();

        if (Busca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o titulo do Livro.");
            return;
        }

        boolean achou = false;
        for (Livro livro : biblioteca.getLivros()) {
            if (livro.getTitulo().toLowerCase().contains(Busca)) {
                areaResultado.append(livro.toString() + "\n");
                achou = true;
            }
        }

        if (!achou) {
            areaResultado.setText("Livro " + txtPesquisa + " não foi encontrado!");
        }
    }

    private void removerLivro() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o Código do livro que deseja remover:");

        if (codigoStr == null || codigoStr.trim().isEmpty()) {
            return;
        }

        try {
            int codigoAlvo = Integer.parseInt(codigoStr.trim());
            Livro livroParaRemover = null;

            for (Livro livro : biblioteca.getLivros()) {
                if (livro.getCodigo() == codigoAlvo) {
                    livroParaRemover = livro;
                    break;
                }
            }

            if (livroParaRemover == null) {
                JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com o código " + codigoAlvo, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPasswordField campoSenha = new JPasswordField();
            Object[] mensagem = {
                    "Livro encontrado: '" + livroParaRemover.getTitulo() + "'\n\n" +
                            "Para confirmar a remoção, introduza a senha de segurança:", campoSenha
            };

            int opcao = JOptionPane.showConfirmDialog(this, mensagem, "Confirmação de Segurança", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (opcao == JOptionPane.OK_OPTION) {
                String senhaDigitada = new String(campoSenha.getPassword()).trim();

                if (senhaDigitada.equals("0987")) {
                    biblioteca.getLivros().remove(livroParaRemover);
                    JOptionPane.showMessageDialog(this, "Livro '" + livroParaRemover.getTitulo() + "' removido com sucesso!");
                    areaResultado.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta! Operação cancelada.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O código introduzido precisa ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
