package ifsc.joe.ui;

import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Classe responsável por gerenciar os controles e interações da interface.
 * Conecta os componentes visuais com a lógica do jogo (Tela).
 */
public class PainelControles  {
    private final Random sorteio;
    private Tela tela;
    private int count;
    private Class<? extends ComMontaria> classeMontariaAtual;
    private JRadioButton[] filtro = new JRadioButton[4];

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
    private JButton montarButton;

    public PainelControles() {
        this.sorteio = new Random();
        this.count = 0;
        this.classeMontariaAtual = ComMontaria.class;
        configurarListeners();
        inicializarArrayFiltro();
        configurarKeyBindings();
        desativarTeclas();
    }

    /**
     * Configura todos os listeners dos botões.
     */
    private void configurarListeners() {
        configurarBotoesSelecionar();
        configurarBotoesMovimento(Personagem.class);
        configurarBotoesCriacao();
        configurarBotaoAtaque();
        configurarBotaoMontar(ComMontaria.class);
    }

    /**
     * Configura todos os listeners dos botões de movimento
     */
    private void configurarBotoesMovimento( Class<? extends Personagem> clazz) {
        removerTodosActionListeners(buttonCima);
        buttonCima.addActionListener(e -> getTela().movimentarPersonagem(Direcao.CIMA, clazz));
        buttonCima.setFocusable(false);

        removerTodosActionListeners(buttonBaixo);
        buttonBaixo.addActionListener(e -> getTela().movimentarPersonagem(Direcao.BAIXO, clazz));
        buttonBaixo.setFocusable(false);

        removerTodosActionListeners(buttonEsquerda);
        buttonEsquerda.addActionListener(e -> getTela().movimentarPersonagem(Direcao.ESQUERDA, clazz));
        buttonEsquerda.setFocusable(false);

        removerTodosActionListeners(buttonDireita);
        buttonDireita.addActionListener(e -> getTela().movimentarPersonagem(Direcao.DIREITA, clazz));
        buttonDireita.setFocusable(false);
    }

    /**
     * Configura todos os listeners dos botões de criação
     */
    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> criarPersonagem(Aldeao.class));
        bCriaAldeao.setFocusable(false);

        bCriaArqueiro.addActionListener(e -> criarPersonagem(Arqueiro.class));
        bCriaArqueiro.setFocusable(false);

        bCriaCavaleiro.addActionListener(e -> criarPersonagem(Cavaleiro.class));
        bCriaCavaleiro.setFocusable(false);
    }

    /**
     * Configura todos os listeners dos botões de seleção
     */
    private void configurarBotoesSelecionar() {
        todosRadioButton.addActionListener(e -> passarClasse(Personagem.class));
        todosRadioButton.setFocusable(false);

        aldeaoRadioButton.addActionListener( e -> passarClasse(Aldeao.class));
        aldeaoRadioButton.setFocusable(false);

        arqueiroRadioButton.addActionListener(e -> passarClasse(Arqueiro.class));
        arqueiroRadioButton.setFocusable(false);

        cavaleiroRadioButton.addActionListener(e -> passarClasse(Cavaleiro.class));
        cavaleiroRadioButton.setFocusable(false);
    }

    /**
     * Metodo de inicialização do array com os botões de selecionar
     */
    private void inicializarArrayFiltro() {
        filtro[0] = todosRadioButton;
        filtro[1] = aldeaoRadioButton;
        filtro[2] = arqueiroRadioButton;
        filtro[3] = cavaleiroRadioButton;
    }

    /**
     * Metodo responsável em chamar o tipo de personagem específico para cada metodo
     *
     * @param clazz
     */
    private void passarClasse(Class<? extends Personagem> clazz) {

        configurarBotoesMovimento(clazz);

        if (ComMontaria.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Class<? extends ComMontaria> montariaClazz = (Class<? extends ComMontaria>) clazz;
            configurarBotaoMontar(montariaClazz);
        } else if (clazz.equals(Personagem.class)) {
            configurarBotaoMontar(ComMontaria.class);
        } else {
            configurarBotaoMontar(ComMontaria.class);
        }
    }

    /**
     * Configura o listener do botão de ataque
     */
    private void configurarBotaoAtaque() {
        atacarButton.setFocusable(false);
//        atacarButton.addActionListener(e -> getTela().atacarAldeoes());
    }

    /**
     * Configura o listener do botão de montar
     *
     * @param clazz
     */
    private void configurarBotaoMontar(Class<? extends ComMontaria> clazz) {
        this.classeMontariaAtual = clazz;
        removerTodosActionListeners(montarButton);
        montarButton.addActionListener(e -> getTela().montarComMontaria(clazz));
        montarButton.setFocusable(false);
    }

    /**
     * Cria um personagem nas coordenadas X e Y.
     */
    public void criarPersonagem(Class<? extends Personagem> clazz) {
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
     * Metodo para gerênciar o atalho do teclado
     */
    private void configurarKeyBindings() {
        InputMap inputMap = painelPrincipal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = painelPrincipal.getActionMap();


        inputMap.put(Constantes.ky1, "criarAldeao");
        actionMap.put("criarAldeao", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarPersonagem(Aldeao.class);
            }
        });

        inputMap.put(Constantes.ky2, "criarArqueiro");
        actionMap.put("criarArqueiro", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarPersonagem(Arqueiro.class);
            }
        });

        inputMap.put(Constantes.ky3, "criarCavaleiro");
        actionMap.put("criarCavaleiro", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarPersonagem(Cavaleiro.class);
            }
        });

        inputMap.put(Constantes.kyM, "montar");
        actionMap.put("montar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTela().montarComMontaria(classeMontariaAtual);
            }
        });

        inputMap.put(Constantes.kySpace, "atacar");
        actionMap.put("atacar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMensagemNaoImplementado("tab");
            }
        });

        inputMap.put(Constantes.kyTab, "alternarFiltros");
        actionMap.put("alternarFiltros", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trocarFiltro();
            }
        });

    }

    /**
     * Metodo que faz a troca de filtro quando a tecla Tab é clicada
     */
    private void trocarFiltro() {
        if (count == 4) count = 0;
        filtro[count].doClick();
        count++;
    }

    /**
     * Metodo que desativa a função nativa do tab
     */
    private void desativarTeclas() {
        painelPrincipal.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                Collections.emptySet());
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
     * metodo para remover o addActionListener do metodo configurarBotoesMovimento()
     *
     * @param button
     */
    private void removerTodosActionListeners(AbstractButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
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