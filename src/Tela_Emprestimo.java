import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Tela_Emprestimo extends JPanel {
    private Tela_Principal telaPrincipal;
    private Biblioteca biblioteca;
    private JTextArea areaHistorico;

    public Tela_Emprestimo(Tela_Principal telaPrincipal, Biblioteca biblioteca) {
        this.telaPrincipal = telaPrincipal;
        this.biblioteca = biblioteca;

        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(245, 246, 250));

        // --- VALIDAÇÃO DE ACESSO ---
        if (telaPrincipal.getUsuarioLogado() == null && !telaPrincipal.isAdmin()) {
            JLabel lblErro = new JLabel("Acesso Negado. Faça login para acessar os Empréstimos.", JLabel.CENTER);
            lblErro.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblErro.setForeground(Color.RED);
            this.add(lblErro, BorderLayout.CENTER);
            return;
        }

        // Título da Tela
        JLabel lblTitulo = new JLabel("Painel de Empréstimos e Devoluções");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        this.add(lblTitulo, BorderLayout.NORTH);

        // Centro: Área de exibição de dados
        areaHistorico = new JTextArea();
        areaHistorico.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaHistorico.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaHistorico);
        this.add(scroll, BorderLayout.CENTER);

        // Rodapé: Botões de Ações Diferenciadas
        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 10, 10));
        painelBotoes.setBackground(new Color(245, 246, 250));

        if (telaPrincipal.isAdmin()) {
            // --- VISÃO DO ADMIN ---
            JButton btnRelatorioAdmin = new JButton("Ver Histórico Global de Usuários");
            painelBotoes.setLayout(new FlowLayout(FlowLayout.LEFT));
            painelBotoes.add(btnRelatorioAdmin);
            btnRelatorioAdmin.addActionListener(e -> carregarRelatorioAdmin());

            areaHistorico.setText("Modo Administrador conectado. Clique no botão abaixo para auditar os usuários.");
        } else {
            // --- VISÃO DO USUÁRIO COMUM ---
            JButton btnVerLivros = new JButton("Ver Livros Disponíveis");
            JButton btnPegar = new JButton("Pegar Empréstimo");
            JButton btnMeusEmprestimos = new JButton("Ver Meus Empréstimos");
            JButton btnDevolver = new JButton("Devolver um Livro"); // Botão criado

            // CORREÇÃO: Adicionando todos os botões fisicamente na tela
            painelBotoes.add(btnVerLivros);
            painelBotoes.add(btnPegar);
            painelBotoes.add(btnMeusEmprestimos);
            painelBotoes.add(btnDevolver); // Adicionado o botão aqui para ele aparecer!

            btnVerLivros.addActionListener(e -> listarLivrosDisponiveis());
            btnPegar.addActionListener(e -> realizarEmprestimo());
            btnMeusEmprestimos.addActionListener(e -> listarMeusEmprestimos());
            btnDevolver.addActionListener(e -> devolverLivro());

            listarMeusEmprestimos();
        }

        this.add(painelBotoes, BorderLayout.SOUTH);
    }

    private void listarLivrosDisponiveis() {
        areaHistorico.setText("=== LIVROS DISPONÍVEIS NA BIBLIOTECA ===\n\n");
        if (biblioteca.getLivros().isEmpty()) {
            areaHistorico.append("Nenhum livro cadastrado no sistema.");
            return;
        }
        for (Livro l : biblioteca.getLivros()) {
            areaHistorico.append("Código: " + l.getCodigo() + " | Título: " + l.getTitulo() + " | Autor: " + l.getAutor() + "\n");
        }
    }

    private void realizarEmprestimo() {
        String codStr = JOptionPane.showInputDialog(this, "Digite o Código do livro que deseja:");
        if (codStr == null || codStr.trim().isEmpty()) return;

        try {
            int cod = Integer.parseInt(codStr.trim());
            Livro livroAlvo = null;

            for (Livro l : biblioteca.getLivros()) {
                if (l.getCodigo() == cod) {
                    livroAlvo = l;
                    break;
                }
            }

            if (livroAlvo != null) {
                // REGRA DE OURO DA BIBLIOTECA: Verificar se alguém no planeta está com esse livro ativo
                for (Usuario umUsuarioQualquer : telaPrincipal.getTodosUsuarios()) {
                    for (Emprestimo emp : umUsuarioQualquer.getMeusEmprestimos()) {
                        if (emp.getLivro().getCodigo() == cod && emp.getStatus().equals("Em andamento")) {
                            JOptionPane.showMessageDialog(this, "Este livro já está emprestado para o usuário '" + umUsuarioQualquer.getLogin() + "' e não está disponível no momento!", "Livro Indisponível", JOptionPane.WARNING_MESSAGE);
                            return; // Encerra o método aqui e impede o empréstimo!
                        }
                    }
                }

                // Se passou pelo teste acima, o livro está livre!
                Usuario atual = telaPrincipal.getUsuarioLogado();
                Emprestimo novoEmp = new Emprestimo(livroAlvo, atual);
                atual.getMeusEmprestimos().add(novoEmp);

                JOptionPane.showMessageDialog(this, "Empréstimo realizado! Retire o livro '" + livroAlvo.getTitulo() + "'.");
                listarMeusEmprestimos();
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Código inválido.");
        }
    }

    private void listarMeusEmprestimos() {
        Usuario atual = telaPrincipal.getUsuarioLogado();
        areaHistorico.setText("=== MEUS EMPRÉSTIMOS (" + atual.getLogin() + ") ===\n\n");

        if (atual.getMeusEmprestimos().isEmpty()) {
            areaHistorico.append("Você ainda não realizou nenhum empréstimo.");
            return;
        }

        for (Emprestimo emp : atual.getMeusEmprestimos()) {
            areaHistorico.append(emp.toString() + "\n");
        }
    }

    private void devolverLivro() {
        String codStr = JOptionPane.showInputDialog(this, "Digite o Código do livro que vai devolver:");
        if (codStr == null || codStr.trim().isEmpty()) return;

        try {
            int cod = Integer.parseInt(codStr.trim());
            Usuario atual = telaPrincipal.getUsuarioLogado();
            boolean devolveu = false;

            for (Emprestimo emp : atual.getMeusEmprestimos()) {
                if (emp.getLivro().getCodigo() == cod && emp.getStatus().equals("Em andamento")) {
                    emp.devolverLivro();
                    JOptionPane.showMessageDialog(this, "Livro '" + emp.getLivro().getTitulo() + "' devolvido com sucesso!");
                    devolveu = true;
                    break;
                }
            }

            if (devolveu) {
                listarMeusEmprestimos();
            } else {
                JOptionPane.showMessageDialog(this, "Você não possui nenhum empréstimo em aberto com este código.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Código inválido.");
        }
    }

    private void carregarRelatorioAdmin() {
        areaHistorico.setText("======= RELATÓRIO AUDITORIA DE USUÁRIOS (ADMIN) =======\n\n");

        if (telaPrincipal.getTodosUsuarios().isEmpty()) {
            areaHistorico.append("Nenhum usuário realizou movimentações no sistema ainda.");
            return;
        }

        for (Usuario u : telaPrincipal.getTodosUsuarios()) {
            areaHistorico.append("USUÁRIO: " + u.getLogin().toUpperCase() + "\n");
            areaHistorico.append("Histórico de Livros:\n");

            if (u.getMeusEmprestimos().isEmpty()) {
                areaHistorico.append("  [Nenhum empréstimo realizado]\n");
            } else {
                for (Emprestimo emp : u.getMeusEmprestimos()) {
                    String dadosFormatados = emp.toString().replace("\n", "\n  ");
                    areaHistorico.append("  " + dadosFormatados + "\n");
                }
            }
            areaHistorico.append("====================================================\n\n");
        }
    }
}
