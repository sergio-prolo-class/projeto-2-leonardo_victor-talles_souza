package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.enums.Recurso;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static java.lang.Math.pow;

public class Arqueiro extends Personagem implements Coletador, Guerreiro {

    public static final Set<Recurso> COLETAVEIS;
    private int flechas;
    private int madeiraColetada;
    private int comidaColetada;
    private int ataque;

    static {
        COLETAVEIS = Set.of(Recurso.COMIDA, Recurso.MADEIRA);
    }

    public Arqueiro(int posX, int posY) {
        super(TipoPersonagem.ARQUEIRO,
                0,
                posX,
                posY,
                Constantes.NOME_IMAGEM_ARQUEIRO,
                Constantes.NOME_IMAGEM_ARQUEIRO_INVERTIDA,
                Constantes.ARQUEIRO_VELOCIDADE,
                Constantes.ARQUEIRO_VIDA_INICIAL);
        this.icone = this.imagemNormal;
        this.flechas = Constantes.ARQUEIRO_FLECHAS_INICIAL;
        this.ataque = Constantes.ARQUEIRO_ATAQUE;
    }

    /**
     * Desenhando o arqueiro, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o Aldeão
     */
    @Override
    public void desenhar(Graphics g, JPanel painel) {
        drawBarra(g);

        desenharAlcanceAtaque(g);

        // verificando se ta vivo
        if (this.getVida() <= 0){
            this.icone = Sangramento;
        }else{this.icone = olhandoParaEsquerda ? this.imagemNormal : this.imagemInvertida;}
        g.drawImage(this.icone, this.posX, this.posY, painel);
        // desenhando de fato a imagem no pai
    }

    /**
     * Verifica se é possível coletar o item
     *
     */
    @Override
    public Recurso coletar(CriarRecurso p) {
        if (!COLETAVEIS.contains(p.getTipo())) return null;
        return p.getTipo();
    }

    // TODO ver como vai ser implementado esses métodos
//    public String produzirFlechas() {
//        if (this.madeiraColetada == 0) return "Arqueiro sem madeira para produção!";
//        this.madeiraColetada--;
//        return this.recarregarFlechas(Constantes.ARQUEIRO_FLECHAS_PRODUCAO);
//    }
//
//    public String recarregarFlechas(int quant) {
//        if (quant <= 0) return "Não é possível recarregar flechas negativas!";
//        this.flechas +=  quant;
//        return String.format("Arqueiro agora com %d flechas%n", quant);
//    }

    /**
     * Metodo de ataque
     *
     */

    @Override
    public void atacar(Personagem p) {
        if(Math.sqrt(pow((p.getX() - this.posX),2) + pow((p.getY() - this.posY),2)) <= this.alcanceAtaque) {
            p.sofreDano(this.ataque);
            //reduzindo flechas
        }
    }
    public void setAlcanceAtaque() {
        this.alcanceAtaque = Constantes.ARQUEIRO_ALCANCE_ATAQUE;
    }
}
