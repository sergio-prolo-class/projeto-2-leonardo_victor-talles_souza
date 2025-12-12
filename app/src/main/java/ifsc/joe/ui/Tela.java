package ifsc.joe.ui;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.CriarRecurso;
import ifsc.joe.enums.Direcao;
import ifsc.joe.enums.Recurso;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.pow;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    private final Set<CriarRecurso> recursos;
    private final Map<Recurso, Integer> estoqueRecursos;
    private final Map<TipoPersonagem, Integer> MortesPorTipo;
    private final Timer gameLoop;

    public Tela() {

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.recursos = new HashSet<>();
        this.estoqueRecursos = new HashMap<>();
        this.MortesPorTipo = new HashMap<>();

        for (Recurso r : Recurso.values()) {
            this.estoqueRecursos.put(r, 0);
        }
        for (TipoPersonagem p : TipoPersonagem.values()) {
            this.MortesPorTipo.put(p, 0);
        }
        gameLoop = new Timer(1, e -> update());
        gameLoop.start();
    }

    /**
     * Method que invocado sempre que o JPanel precisa ser resenhado.
     * @param g Graphics componente de java.awt
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //TODO preciso ser melhorado
        // percorrendo a lista de aldeões e pedindo para cada um se desenhar na tela
        this.personagens.forEach(personagem -> personagem.desenhar(g, this));
        this.recursos.forEach(recurso -> recurso.desenhar(g, this));

        // liberando o contexto gráfico
        g.dispose();
    }

    /**
     * Metodo que invoca o metodo da estatico da classe PersonagemFactory
     * e adiciona o personagem na coleção e Desenha-o neste JPanel
     *
     */
    public void criarPersonagem(TipoPersonagem tipo, int posX, int posY) {
        this.personagens.add(PersonagemFactory.criar(tipo, posX, posY));
        this.repaint();
    }

    /**
     * Desenha-o neste JPanel, e adiciona o recurso na coleção
     *
     * @param recurso tipo que será criado
     * @param posX posição do eixo X que será criado
     * @param posY posição do eixo Y que será criado
     */
    public void criarRecurso(Recurso recurso, int posX, int posY) {
        this.recursos.add(new CriarRecurso(recurso, posX, posY));
        this.repaint();
    }

    /**
     * Coleta os recursos próximos ao personagem
     *
     * @param clazz personagem que implementa coletador que irá coletar
     */
    public void coletarRecurso(Class<? extends Coletador> clazz) {
        Set<CriarRecurso> recursosParaRemover = new HashSet<>();

        this.personagens.stream()
            .filter(clazz::isInstance)
            .forEach(personagem -> {
                this.recursos.iterator().forEachRemaining(r -> {
                    if (distancia(r, personagem)) {
                        Coletador coletador = clazz.cast(personagem);
                        Recurso recursoColetado = coletador.coletar(r);

                        if (recursoColetado != null) {
                            recursosParaRemover.add(r);
                            this.estoqueRecursos.put(recursoColetado, this.estoqueRecursos.get(recursoColetado) + 1);
                        }
                    }
                });
            });
        this.recursos.removeAll(recursosParaRemover);
        this.repaint();
    }

    /**
     * Verifica a distância entre os recursos e o personagem
     *
     * @param recurso que será usado para calcular a distância de um personagem
     * @param personagem que será usado para calcular a distância de um recurso
     * @return boolean
     */
    public boolean distancia(CriarRecurso recurso, Personagem personagem){
        return pow((recurso.getPosX() - personagem.getX()), 2) + pow((recurso.getPosY() - personagem.getY()), 2) <= 1500;
    }

    /**
     * Atualiza as coordenadas X ou Y de todos os personagens
     *
     * @param direcao direcao para movimentar
     */
    public void movimentarPersonagem(Direcao direcao, Class<? extends Personagem> clazz) {

        this.personagens.stream()
            .filter(clazz::isInstance)
            .forEach(p -> p.mover(direcao, this.getWidth(), this.getHeight()));
        // Depois que as coordenadas foram atualizadas é necessário repintar o JPanel
        this.repaint();
    }

//    /**
//     * O metodo atacarPersonagem faz com que todos os personagens do tipo Guerreiro ataquem todos os outros personagens
//     * presentes na tela. Se algum personagem tiver sua vida reduzida a zero ou menos durante o ataque, ele é marcado para remoção
//     * e contabilizado no mapa MortesPorTipo.
//     */


    public void atacarPersonagem(Class<? extends Guerreiro> clazz) {

        this.personagens.stream()
            .filter(clazz::isInstance)
            .filter(Guerreiro.class::isInstance)
            .map(Guerreiro.class::cast)
            .forEach(g -> {
                Personagem p = (Personagem) g;

                // INVERTE O LADO AO ATACAR
                p.inverter();
                p.setAlcanceAtaque();
                this.repaint();

                // ATACA
                this.personagens.stream()
                    .filter(other -> other != p)
                    .forEach(q -> {
                        g.atacar(q);
                        p.tempoAtaque = System.currentTimeMillis();
                        this.repaint();
                        if (q.getVida() <= 0) {
                            this.repaint();
                            TipoPersonagem tipo = q.getTipo();
                            this.MortesPorTipo.put(tipo, this.MortesPorTipo.get(tipo) + 1);
                        }
                    });
            });
        this.repaint();
    }

    private void update() {
        Set<Personagem> removerAgora = new HashSet<>();

        for (Personagem p : personagens) {

            //gestos de ataque volta após ataque
            if (p.getAlcanceAtaque() > 0 && System.currentTimeMillis() - p.tempoAtaque > 200) {
                p.inverter();
                p.zerarAlcanceAtaque();
            }

            //efeito esquiva dura 150ms
            if (p.getEsquivando() && System.currentTimeMillis() - p.tempoEsquiva > 150) {
                p.alterarEsquivando();
            }

            //personagem morto some após 500ms
            if (p.getVida() <= 0 && System.currentTimeMillis() - p.tempoMorte > 900) {
                removerAgora.add(p);
            }
        }

        // remove com segurança
        personagens.removeAll(removerAgora);
        this.repaint();
    }

    /**
     * Altera o estado do personage de montado para não montado e vice-versa
     *
     */
    public void montarComMontaria(Class<? extends ComMontaria> clazz) {
        this.personagens.stream()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .forEach(ComMontaria::alternarMontado);
        // Fazendo o JPanel ser redesenhado
        this.repaint();
    }

    /**
     * Retorna o map contendo a quantidade de recursos coletados
     *
     * @return Map<Recurso, Integer>
     */
    public Map<Recurso, Integer> getEstoqueRecursos() {
        return estoqueRecursos;
    }
    public Map<TipoPersonagem, Integer> getMortesPorTipo() {
        return MortesPorTipo;
    }
}