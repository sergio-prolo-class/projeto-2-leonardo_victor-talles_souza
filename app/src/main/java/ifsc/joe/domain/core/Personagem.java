package ifsc.joe.domain.core;

import ifsc.joe.enums.Direcao;
import ifsc.joe.ui.ResourceManager;

import javax.swing.*;
import java.awt.*;

public abstract class Personagem {
    protected int posX, posY;
    protected Image icone;
    protected String nomeImagemInicial;
    protected String nomeImagemVariante;

    protected Image imagemNormal;
    protected Image imagemVariante;

    protected double velocidade;
    protected int vida;

    // Construtor
    public Personagem(int posX, int posY, String nomeImagemInicial, String nomeImagemVariante, double velocidade, int vida) {
            this.posX = posX;
            this.posY = posY;
            this.nomeImagemInicial = nomeImagemInicial;
            this.nomeImagemVariante = nomeImagemVariante;
            this.velocidade = velocidade;
            this.vida = vida;

        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = ResourceManager.getImagens(nomeImagemVariante);
    }

    public Personagem(int posX, int posY, String nomeImagemInicial, double velocidade, int vida) {
        this.posX = posX;
        this.posY = posY;
        this.nomeImagemInicial = nomeImagemInicial;
        this.velocidade = velocidade;
        this.vida = vida;

        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = null;
    }

    /**
     * Desenhando o personagem, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o personagem
     */
    public abstract void desenhar(Graphics g, JPanel painel);

    /**
     * Atualiza as coordenadas X e Y do personagem
     *
     * @param direcao indica a direcao a ir.
     */
    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        switch (direcao) {
            case CIMA -> this.posY -= (int) (velocidade * 10);
            case BAIXO -> this.posY += (int) (velocidade * 10);
            case ESQUERDA -> this.posX -= (int) (velocidade * 10);
            case DIREITA -> this.posX += (int) (velocidade * 10);
        }

        //Não deixa a imagem ser desenhada fora dos limites do JPanel pai
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }


    public final double getVelocidade() {
        return velocidade;
    }

    public final int getVida() {
        return vida;
    }
}
