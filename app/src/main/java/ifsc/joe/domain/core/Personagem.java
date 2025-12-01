package ifsc.joe.domain.core;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {
    protected int posX, posY;
    protected Image icone;
    protected String nomeImagemBase;

    protected double velocidade;
    protected int vida;

    // Construtor
    public Personagem(int posX, int posY, String nomeImagemBase, double velocidade, int vida) {
        this.posX = posX;
        this.posY = posY;
        this.nomeImagemBase = nomeImagemBase;
        this.velocidade = velocidade;
        this.vida = vida;
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

    /**
     * Metodo auxiliar para carregar uma imagem do disco
     *
     * @param imagem Caminho da imagem
     * @return Retorna um objeto Image
     */
    protected Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./"+imagem+".png")
        )).getImage();
    }

    public final double getVelocidade() {
        return velocidade;
    }

    public final int getVida() {
        return vida;
    }
}
