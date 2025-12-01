package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;

import javax.swing.*;
import java.awt.*;

public class Cavaleiro extends Personagem implements ComMontaria, Guerreiro {

    private boolean montado;
    private int ataque;

    // Construtor
    public Cavaleiro(int posX, int posY) {
        super(posX,
                posY,
                Constantes.NOME_IMAGEM_CAVALEIRO,
                Constantes.CAVALEIRO_VELOCIDADE,
                Constantes.CAVALEIRO_VIDA_INICIAL);
        this.icone = this.carregarImagem(this.nomeImagemBase);
        this.ataque = Constantes.CAVALEIRO_ATAQUE;
        this.montado = true;
    }

    /**
     * Desenhando o Cavaleiro, nas coordenadas X e Y, com a imagem 'icone'
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
     * Metodo para alternar montaria do
     */
    @Override
    public void alternarMontado() {
        this.montado = !this.montado;
        this.velocidade = this.montado
                ? Constantes.CAVALEIRO_VELOCIDADE
                : Constantes.CAVALEIRO_VELOCIDADE_NAO_MONTADO;
    }

    /**
     * Metodo de ataque
     *
     * @param alvo
     */
    @Override
    public void atacar(Personagem alvo) {
        // TODO implepentar logica de ataque
    }
}
