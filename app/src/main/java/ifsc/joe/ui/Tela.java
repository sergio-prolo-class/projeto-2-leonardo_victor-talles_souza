package ifsc.joe.ui;

import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    private final Set<Aldeao> aldeoes;

    public Tela() {

        //TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.aldeoes = new HashSet<>();
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

        // liberando o contexto gráfico
        g.dispose();
    }

    /**
     * Desenha-o neste JPanel
     * e adiciona o mesmo na lista de aldeoes
     *
     * @param personagem
     */
    public void criarPersonagem(Personagem personagem) {
        personagem.desenhar(super.getGraphics(), this);
        this.personagens.add(personagem);
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

    /**
     * Altera o estado do aldeão de atacando para não atacando e vice-versa
     */
//    public void atacarAldeoes() {
//
//        //TODO preciso ser melhorado
//
//        // Percorrendo a lista de aldeões e pedindo para todos atacarem
//        this.aldeoes.forEach(Aldeao::atacar);
//
//        // Fazendo o JPanel ser redesenhado
//        this.repaint();
//    }
}