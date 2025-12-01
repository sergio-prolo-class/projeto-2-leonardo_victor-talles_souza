package ifsc.joe.ui;

import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.util.Random;

/**
 * Classe responsável por gerenciar os controles e interações da interface.
 * Conecta os componentes visuais com a lógica do jogo (Tela).
 */
public class PainelControles {

    private final Random sorteio;
    private Tela tela;

    // Componentes da interface (gerados pelo Form Designer)
    private JPanel painelPrincipal;
    private JPanel painelTela;
    private JPanel painelLateral;
    private JButton bCriaAldeao;
    private JButton bCriaArqueiro;
    private JButton bCriaCavaleiro;
    private JRadioButton todosRadioButton;
    private JRadioButton aldeaoRadioButton;
    private JRadioButton arqueiroRadioButton;
    private JRadioButton cavaleiroRadioButton;
    private JButton atacarButton;
    private JButton buttonCima;
    private JButton buttonEsquerda;
    private JButton buttonBaixo;
    private JButton buttonDireita;
    private JLabel logo;

    public PainelControles() {
        this.sorteio = new Random();
        configurarListeners();
    }

    /**
     * Configura todos os listeners dos botões.
     */
    private void configurarListeners() {
        configurarBotoesMovimentar();
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
    }

    /**
     * Configura todos os listeners dos botões de movimento
     */
    private void configurarBotoesMovimento() {
        buttonCima.addActionListener(e -> getTela().movimentarPersonagem(Direcao.CIMA));
        buttonBaixo.addActionListener(e -> getTela().movimentarPersonagem(Direcao.BAIXO));
        buttonEsquerda.addActionListener(e -> getTela().movimentarPersonagem(Direcao.ESQUERDA));
        buttonDireita.addActionListener(e -> getTela().movimentarPersonagem(Direcao.DIREITA));
    }

    /**
     * Configura todos os listeners dos botões de criação
     */
    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> criarPersonagem(Aldeao.class));

        bCriaArqueiro.addActionListener(e -> criarPersonagem(Arqueiro.class));

        bCriaCavaleiro.addActionListener(e -> criarPersonagem(Cavaleiro.class));
    }

    /**
     * Configura todos os listeners dos botões de seleção
     */
    private void configurarBotoesMovimentar() {
        todosRadioButton.addActionListener(e -> configurarBotoesMovimento());

        // TODO selecionar
        aldeaoRadioButton.addActionListener( e -> mostrarMensagemNaoImplementado("selecionar aldeão"));
        arqueiroRadioButton.addActionListener(e -> mostrarMensagemNaoImplementado("selecionar arqueiro"));
        cavaleiroRadioButton.addActionListener(e -> mostrarMensagemNaoImplementado("selecionar cavaleiro"));
    }

    /**
     * Configura o listener do botão de ataque
     */
    private void configurarBotaoAtaque() {
//        atacarButton.addActionListener(e -> getTela().atacarAldeoes());
    }

    /**
     * Cria um personagem nas coordenadas X e Y.
     */
    private void criarPersonagem(Class<? extends Personagem> clazz) {
        int[] pos = sortearPosicaoAleatoria();
        try {
            Constructor<? extends Personagem> personagem = clazz.getConstructor(int.class, int.class);
            Personagem p = personagem.newInstance(pos[0], pos[1]);
            getTela().criarPersonagem(p);
        } catch (Exception e) {
            // não sei se pode colocar
            // TODO melhorar o tratamento
            System.err.println("Erro ao criar personagem: " + e.getMessage());
        }
    }

    /**
     * Metodo para fazer o sorteio da posição inicial
     *
     * @return
     */
    private int[] sortearPosicaoAleatoria() {
        int[] xy = new int[2];
        xy[0] = sorteio.nextInt(painelTela.getWidth() - Constantes.PADDING);
        xy[1] = sorteio.nextInt(painelTela.getHeight() - Constantes.PADDING);
        return xy;
    }


    /**
     * Exibe mensagem informando que a funcionalidade ainda não foi implementada.
     */
    private void mostrarMensagemNaoImplementado(String funcionalidade) {
        JOptionPane.showMessageDialog(
                painelPrincipal,
                "Preciso ser implementado",
                funcionalidade,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Obtém a referência da Tela com cast seguro.
     */
    private Tela getTela() {
        if (tela == null) {
            tela = (Tela) painelTela;
        }
        return tela;
    }

    /**
     * Retorna o painel principal para ser adicionado ao JFrame.
     */
    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    /**
     * Método chamado pelo Form Designer para criar componentes customizados.
     * Este método é invocado antes do construtor.
     */
    private void createUIComponents() {
        this.painelTela = new Tela();
    }
}