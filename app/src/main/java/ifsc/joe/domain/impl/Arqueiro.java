package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.enums.Recurso;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

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
        super(posX,
                posY,
                Constantes.NOME_IMAGEM_ARQUEIRO,
                Constantes.ARQUEIRO_VELOCIDADE,
                Constantes.ALDEAO_VIDA_INICIAL);
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
        // verificando se ta vivo
        if (this.getVida() < 0) return;
        // desenhando de fato a imagem no pai
        g.drawImage(this.icone, this.posX, this.posY, painel);
    }

    /**
     * Verifica se é possível coletar o item
     *
     */
    @Override
    public boolean coletar(Recurso recurso) {
        if (!COLETAVEIS.contains(recurso)) return false;
        if (recurso == Recurso.MADEIRA) this.madeiraColetada++;
        if (recurso == Recurso.COMIDA) this.comidaColetada++;
        return true;
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
    public void atacar(Personagem alvo) {
        // TODO implepentar logica de ataque
    }
}
