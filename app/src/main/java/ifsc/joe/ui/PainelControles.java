package ifsc.joe.ui;

import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;
import ifsc.joe.enums.Recurso;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Classe responsável por gerenciar os controles e interações da interface.
 * Conecta os componentes visuais com a lógica do jogo (Tela).
 */
public class PainelControles {
    private final Random sorteio;
    private Tela tela;
    private int count;
    private Class<? extends ComMontaria> classeMontariaAtual;
    private Class<? extends Coletador> classeColetadorAtual;
    private Class<? extends Personagem> classePersonagemAtual;
    private Class<? extends Guerreiro> classeGuerreiroAtual;
    private final JRadioButton[] filtro = new JRadioButton[4];

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
    private JButton coletarButton;
    private JTable recursos;
    private JTable mortes;

    public PainelControles() {
        this.sorteio = new Random();
        this.count = 0;
        this.classeMontariaAtual = ComMontaria.class;
        this.classePersonagemAtual = Personagem.class;
        this.classeGuerreiroAtual = Guerreiro.class;
        this.classeColetadorAtual = Coletador.class;
        iniciarGeradorRecursos();
        configurarListeners();
        inicializarArrayFiltro();
        configurarKeyBindings();
        table();
        desativarTeclas();
        setFocusable();
    }

    /**
     * Configura todos os listeners dos botões.
     */
    private void configurarListeners() {
        configurarBotoesSelecionar();
        configurarBotoesMovimento(Personagem.class);
        configurarBotoesCriacao();
        configurarBotaoAtaque(Guerreiro.class);
        configurarBotaoMontar(ComMontaria.class);
        configurarBotaoColetar(Coletador.class);
    }

    /**
     * Configura todos os listeners dos botões de movimento
     */
    private void configurarBotoesMovimento(Class<? extends Personagem> clazz) {
        removerTodosActionListeners(buttonCima);
        buttonCima.addActionListener(e -> getTela().movimentarPersonagem(Direcao.CIMA, clazz));

        removerTodosActionListeners(buttonBaixo);
        buttonBaixo.addActionListener(e -> getTela().movimentarPersonagem(Direcao.BAIXO, clazz));

        removerTodosActionListeners(buttonEsquerda);
        buttonEsquerda.addActionListener(e -> getTela().movimentarPersonagem(Direcao.ESQUERDA, clazz));

        removerTodosActionListeners(buttonDireita);
        buttonDireita.addActionListener(e -> getTela().movimentarPersonagem(Direcao.DIREITA, clazz));
    }

    /**
     * Configura todos os listeners dos botões de criação
     */
    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> getTela().criarPersonagem(TipoPersonagem.ALDEAO, posicaoCriacao()[0], posicaoCriacao()[1]));

        bCriaArqueiro.addActionListener(e -> getTela().criarPersonagem(TipoPersonagem.ARQUEIRO, posicaoCriacao()[0], posicaoCriacao()[1]));

        bCriaCavaleiro.addActionListener(e -> getTela().criarPersonagem(TipoPersonagem.CAVALEIRO, posicaoCriacao()[0], posicaoCriacao()[1]));
    }

    /**
     * Configura todos os listeners dos botões de seleção
     */
    private void configurarBotoesSelecionar() {
        todosRadioButton.addActionListener(e -> passarClasse(Personagem.class));

        aldeaoRadioButton.addActionListener(e -> passarClasse(Aldeao.class));

        arqueiroRadioButton.addActionListener(e -> passarClasse(Arqueiro.class));

        cavaleiroRadioButton.addActionListener(e -> passarClasse(Cavaleiro.class));
    }

    /**
     *  Metodo que cria e gerencia a tabela de recursos coletados
     */
    public void table() {
        String[][] dados = {
                Constantes.COLUNA,
                {String.valueOf(getTela().getEstoqueRecursos().get(Recurso.OURO)),
                        String.valueOf(getTela().getEstoqueRecursos().get(Recurso.MADEIRA)),
                        String.valueOf(getTela().getEstoqueRecursos().get(Recurso.COMIDA))}
        };

        TableModel tableModel = new DefaultTableModel(dados, Constantes.COLUNA) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[][] dados2 = {
                Constantes.COLUNA_MORRTES,
                {String.valueOf(getTela().getMortesPorTipo().get(TipoPersonagem.ALDEAO)),
                        String.valueOf(getTela().getMortesPorTipo().get(TipoPersonagem.CAVALEIRO)),
                        String.valueOf(getTela().getMortesPorTipo().get(TipoPersonagem.ARQUEIRO))}
        };

        TableModel tableModel2 = new DefaultTableModel(dados2, Constantes.COLUNA_MORRTES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };



        recursos.setModel(tableModel);
        mortes.setModel(tableModel2);
        recursos.repaint();
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
     */
    private void passarClasse(Class<? extends Personagem> clazz) {
        this.classePersonagemAtual = clazz;

        configurarBotoesMovimento(clazz);

        configurarBotaoEspecifico(ComMontaria.class, clazz, this::configurarBotaoMontar, montarButton);

        configurarBotaoEspecifico(Coletador.class, clazz, this::configurarBotaoColetar, coletarButton);

        configurarBotaoEspecifico(Guerreiro.class, clazz, this::configurarBotaoAtaque, atacarButton);
    }

    /**
     * Configura um botão específico para executar uma ação, baseando-se na compatibilidade
     * entre a ‘interface’ esperada e a classe do personagem.
     *
     * @param <T> O tipo de interface (tela, resultado, ou objeto) que se espera receber ou manipular.
     * @param interfaceEsperada A classe ou ‘interface’ que define o tipo de retorno ou a ação esperada.
     * @param personagemClass A classe que estende Personagem e que será usada como base para a configuração.
     * @param configurador Uma função (Consumer) que aceita a classe do tipo <T> para realizar a lógica de configuração (ex: setar um Listener).
     * @param button O componente JButton a ser configurado (habilitado ou desabilitado).
     */
    private <T> void configurarBotaoEspecifico(Class<T> interfaceEsperada,
                                               Class<? extends Personagem> personagemClass,
                                               Consumer<Class<T>> configurador,
                                               JButton button) {
        if (interfaceEsperada.isAssignableFrom(personagemClass)) {
            configurador.accept((Class<T>) personagemClass);
        } else if (personagemClass.equals(Personagem.class)) {
            configurador.accept(interfaceEsperada);
        } else {
            button.setEnabled(false);
        }
    }

    /**
     * Configura o listener do botão de ataque
     */
    private void configurarBotaoAtaque(Class<? extends Guerreiro> clazz) {
        this.classeGuerreiroAtual = clazz; //talvez isso não faz nada
        removerTodosActionListeners(atacarButton);
        atacarButton.setEnabled(true);
        atacarButton.addActionListener(e -> {
            getTela().atacarPersonagem(clazz);
            table();
        });
    }

    /**
     * Configura o listener do botão de coletar
     *
     * @param clazz A classe que implementa Coletador e que será usada coletar os recursos.
     */
    private void configurarBotaoColetar(Class<? extends Coletador> clazz) {
        this.classeColetadorAtual = clazz;
        removerTodosActionListeners(coletarButton);
        coletarButton.setEnabled(true);
        coletarButton.addActionListener(e -> {
            getTela().coletarRecurso(clazz);
            table();
        });
    }

    /**
     * Configura o listener do botão de montar
     */
    private void configurarBotaoMontar(Class<? extends ComMontaria> clazz) {
        this.classeMontariaAtual = clazz;
        removerTodosActionListeners(montarButton);
        montarButton.setEnabled(true);
        montarButton.addActionListener(e -> getTela().montarComMontaria(clazz));
    }

    /**
     * Metodo para gerênciar o atalho do teclado
     */
    private void configurarKeyBindings() {
        InputMap inputMap = painelPrincipal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = painelPrincipal.getActionMap();

        inputMap.put(Constantes.KY_1, Constantes.CRIAR_ALDEAO);
        actionMap.put(Constantes.CRIAR_ALDEAO, new CriacaoAtalho(TipoPersonagem.ALDEAO, getTela(), this));

        inputMap.put(Constantes.KY_2, Constantes.CRIAR_ARQUEIRO);
        actionMap.put(Constantes.CRIAR_ARQUEIRO, new CriacaoAtalho(TipoPersonagem.ARQUEIRO, getTela(), this));

        inputMap.put(Constantes.KY_3, Constantes.CRIAR_CAVALEIRO);
        actionMap.put(Constantes.CRIAR_CAVALEIRO, new CriacaoAtalho(TipoPersonagem.CAVALEIRO, getTela(), this));

        inputMap.put(Constantes.KY_LEFT, Constantes.ESQUERDA);
        actionMap.put(Constantes.ESQUERDA, new MovimentoAtalho(Direcao.ESQUERDA, getTela(), this));

        inputMap.put(Constantes.KY_UP, Constantes.CIMA);
        actionMap.put(Constantes.CIMA, new MovimentoAtalho(Direcao.CIMA, getTela(), this));

        inputMap.put(Constantes.KY_RIGHT, Constantes.DIREITA);
        actionMap.put(Constantes.DIREITA, new MovimentoAtalho(Direcao.DIREITA, getTela(), this));

        inputMap.put(Constantes.KY_DOWN, Constantes.BAIXO);
        actionMap.put(Constantes.BAIXO, new MovimentoAtalho(Direcao.BAIXO, getTela(), this));

        inputMap.put(Constantes.KY_M, Constantes.MONTAR);
        actionMap.put(Constantes.MONTAR, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!classePersonagemAtual.equals(Arqueiro.class)) {
                    getTela().montarComMontaria(classeMontariaAtual);
                }
            }
        });

        inputMap.put(Constantes.KY_C, Constantes.COLETAR);
        actionMap.put(Constantes.COLETAR, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!classePersonagemAtual.equals(Cavaleiro.class)) {
                    getTela().coletarRecurso(classeColetadorAtual);
                    table();
                }
            }
        });

        inputMap.put(Constantes.KY_SPACE, Constantes.ATACAR);
        actionMap.put(Constantes.ATACAR, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!classePersonagemAtual.equals(Aldeao.class)) {
                    getTela().atacarPersonagem(classeGuerreiroAtual);
                    table();
                }
            }
        });

        inputMap.put(Constantes.KY_TAB, Constantes.ALTERNAR_FILTROS);
        actionMap.put(Constantes.ALTERNAR_FILTROS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trocarFiltro();
            }
        });
        painelPrincipal.setFocusable(true);
    }

    /**
     * Metodo que escolhe um recurso aleatório a cada tempo determinado
     */
    private void iniciarGeradorRecursos() {
        Timer timer = new Timer(Constantes.DELAY_CRIACAO_RECURSO, e -> {
            Recurso[] recursos = Recurso.values();
            Recurso recursoSorteado = recursos[sorteio.nextInt(recursos.length)];
            getTela().criarRecurso(recursoSorteado, posicaoCriacao()[0], posicaoCriacao()[1]);
        });

        timer.start();
    }

    /**
     * Metodo que faz a troca de filtro quando a tecla Tab é clicada
     */
    private void trocarFiltro() {
        count = (count + 1) % 4;
        filtro[count].doClick();
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
     * @return int[]
     */
    public int[] posicaoCriacao() {
        int[] xy = new int[2];
        xy[0] = sorteio.nextInt(painelTela.getWidth() - Constantes.PADDING);
        xy[1] = sorteio.nextInt(painelTela.getHeight() - Constantes.PADDING);
        return xy;
    }

    /**
     * metodo para remover o addActionListener do metodo configurarBotoesMovimento()
     */
    private void removerTodosActionListeners(AbstractButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    /**
     * Metodo usado para remover o foco do painel ao ser clicado com o mouse
     */
    private void setFocusable() {
        bCriaAldeao.setFocusable(false);
        bCriaArqueiro.setFocusable(false);
        bCriaCavaleiro.setFocusable(false);
        buttonCima.setFocusable(false);
        buttonBaixo.setFocusable(false);
        buttonEsquerda.setFocusable(false);
        buttonDireita.setFocusable(false);
        atacarButton.setFocusable(false);
        coletarButton.setFocusable(false);
        montarButton.setFocusable(false);
        todosRadioButton.setFocusable(false);
        aldeaoRadioButton.setFocusable(false);
        arqueiroRadioButton.setFocusable(false);
        cavaleiroRadioButton.setFocusable(false);
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
    public final Tela getTela() {
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
     * Retorna a classe atual selecionada
     */
    public Class<? extends Personagem> getClassePersonagemAtual() {
        return classePersonagemAtual;
    }

    /**
     * Método chamado pelo Form Designer para criar componentes customizados.
     * Este método é invocado antes do construtor.
     */
    private void createUIComponents() {
        this.painelTela = new Tela();
    }

}