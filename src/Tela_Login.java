import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Tela_Login extends JPanel {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private Tela_Principal telaPrincipal;

    private static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public Tela_Login(Tela_Principal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;

        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(new EmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(245, 246, 250));

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBackground(new Color(245, 246, 250));

        painelFormulario.add(new JLabel("Usuário"));
        txtUsuario = new JTextField();
        painelFormulario.add(txtUsuario);

        painelFormulario.add(new JLabel("Senha"));
        txtSenha = new JPasswordField();
        painelFormulario.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Criar Conta");

        painelFormulario.add(btnEntrar);
        painelFormulario.add(btnCadastrar);

        JPanel containerCentral = new JPanel(new BorderLayout());
        containerCentral.setBackground(new Color(245, 246, 250));
        containerCentral.setPreferredSize(new Dimension(300, 150));
        containerCentral.add(painelFormulario, BorderLayout.NORTH);

        this.add(containerCentral, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> efetuarLogin());
        btnCadastrar.addActionListener(e -> criarConta());
    }

    private void efetuarLogin() {
        String userDigitado = txtUsuario.getText();
        String senhaDigitado = new String(txtSenha.getPassword());

        if (userDigitado.equals("adm") && senhaDigitado.equals("5412")) {
            JOptionPane.showMessageDialog(this, "Login efetuado como Administrador!");
            telaPrincipal.atualizarUsuarioLogado("Administrador");
            limparCampos();
            return;
        }

        for (Usuario u : listaUsuarios) {
            if (u.getLogin().equals(userDigitado) && u.verificarSenha(senhaDigitado)) {
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");

                telaPrincipal.conectarUsuario(u);

                limparCampos();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Usuário ou seha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void criarConta() {
        String userDigitado = txtUsuario.getText().trim();
        String senhaDigitada = new String(txtSenha.getPassword()).trim();

        if (userDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos para cadastrar!");
            return;
        }

        if (userDigitado.equalsIgnoreCase("adm")) {
            JOptionPane.showMessageDialog(this, "Nome reservado!", "Probido!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listaUsuarios.add(new Usuario(userDigitado, senhaDigitada));
        JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        txtUsuario.setText("");
        txtSenha.setText("");
    }
}
