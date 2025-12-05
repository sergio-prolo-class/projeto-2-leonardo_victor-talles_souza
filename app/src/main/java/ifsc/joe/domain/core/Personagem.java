package ifsc.joe.domain.core;

import ifsc.joe.consts.Constantes;
import ifsc.joe.enums.Direcao;
import ifsc.joe.ui.ResourceManager;
import ifsc.joe.ui.Tela;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.pow;

public abstract class Personagem {
    protected int posX, posY;
    protected Image icone;
    protected String nomeImagemInicial;
    protected String nomeImagemInvertida;
    protected String nomeImagemVariante;
    protected String nomeImagemVarianteInvertida;

    protected Image imagemNormal;
    protected Image imagemVariante;
    protected Image imagemVarianteInvertida;
    protected Image imagemInvertida;

    protected double velocidade;
    protected int vida;
    protected boolean olhandoParaEsquerda;

    // Construtor
    public Personagem(int posX, int posY, String nomeImagemInicial, String nomeImagemVariante,String nomeImagemInvertida,String nomeImagemVarianteInvertida, double velocidade, int vida) {
            this.posX = posX;
            this.posY = posY;
            this.nomeImagemInicial = nomeImagemInicial;
            this.nomeImagemInvertida = nomeImagemInvertida;
            this.nomeImagemVariante = nomeImagemVariante;
            this.nomeImagemVarianteInvertida = nomeImagemVarianteInvertida;
            this.velocidade = velocidade;
            this.vida = vida;

            this.olhandoParaEsquerda = true;
        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = ResourceManager.getImagens(nomeImagemVariante);
        this.imagemInvertida = ResourceManager.getImagens(nomeImagemInvertida);
        this.imagemVarianteInvertida = ResourceManager.getImagens(nomeImagemVarianteInvertida);
    }

    public Personagem(int posX, int posY, String nomeImagemInicial,String nomeImagemInvertida, double velocidade, int vida) {
        this.posX = posX;
        this.posY = posY;
        this.nomeImagemInicial = nomeImagemInicial;
        this.velocidade = velocidade;
        this.vida = vida;

        this.imagemInvertida = ResourceManager.getImagens(nomeImagemInvertida);
        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = null;
        this.olhandoParaEsquerda = true;
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

    public final int getX() {
        return this.posX;
    }
    public final int getY() {
        return this.posY;
    }

    public final void sofreDano(int vida) {
        this.vida -= vida;
        //O print é teste
        System.out.println("Personagem em (" + this.posX + "," + this.posY + ") sofreu " + vida + " de dano. Vida restante: " + this.vida + "\n");
        this.morrer();
    }

    public final void morrer(){
        if(this.vida <= 0){
            //this.icone = ResourceManager.getImagens(Constantes.SANGUE);
        }
    }

//    public final void sangrar(){
//        this.icone = ResourceManager.getImagens(Constantes.SANGUE);
//    }

    public final void inverter(){
        this.olhandoParaEsquerda = !this.olhandoParaEsquerda;
    }

}
