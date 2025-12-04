package ifsc.joe.ui;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.enums.Direcao;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    public Tela() {

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
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
     * Metodo que invoca o metodo da estatico da classe PersonagemFactory
     * e adiciona o personagem na coleção e Desenha-o neste JPanel
     *
     */
    public void criarPersonagem(TipoPersonagem tipo, int posX, int posY) {
        this.personagens.add(PersonagemFactory.criar(tipo, posX, posY));
        this.repaint();
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
//     * Altera o estado do aldeão de atacando para não atacando e vice-versa
//     */
    public void atacarPersonagem(Class<? extends Guerreiro> clazz) {
        this.personagens.stream()
                .filter(clazz::isInstance)
                .forEach(p -> {
                    this.personagens.stream()
                            .filter(other -> other != p)
                            .forEach(other -> {
                                ((Guerreiro) p).atacar(other);
                            });
                });

        // Depois que as coordenadas foram atualizadas é necessário repintar o JPanel
        // Percorrendo a lista de aldeões e pedindo para todos atacarem
        //this.aldeoes.forEach(Aldeao::alternarMontado);
        // Fazendo o JPanel ser redesenhado
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
}