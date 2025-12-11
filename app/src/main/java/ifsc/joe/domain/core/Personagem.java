package ifsc.joe.domain.core;

import ifsc.joe.consts.Constantes;
import ifsc.joe.enums.Direcao;
import ifsc.joe.enums.Recurso;
import ifsc.joe.enums.TipoPersonagem;
import ifsc.joe.ui.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Personagem {
    private final TipoPersonagem tipo;
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
    protected int alcanceAtaque;
    protected double chanceDeEsquivar;
    protected boolean esquivando;
    public long tempoEsquiva;
    public long tempoMorte;
    public long tempoAtaque;

    protected final int vidaMaxima;


    protected Image Sangramento = ResourceManager.getImagens(Constantes.SANGUE);

    // Construtor
    public Personagem(TipoPersonagem Tipo, double chanceDeEsquivar, int posX, int posY, String nomeImagemInicial, String nomeImagemVariante,String nomeImagemInvertida,String nomeImagemVarianteInvertida, double velocidade, int vida) {
        this.tipo = Tipo;
        this.chanceDeEsquivar = chanceDeEsquivar;
        this.posX = posX;
        this.posY = posY;
        this.nomeImagemInicial = nomeImagemInicial;
        this.nomeImagemInvertida = nomeImagemInvertida;
        this.nomeImagemVariante = nomeImagemVariante;
        this.nomeImagemVarianteInvertida = nomeImagemVarianteInvertida;
        this.velocidade = velocidade;
        this.vida = vida;
        this.vidaMaxima = vida;

        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = ResourceManager.getImagens(nomeImagemVariante);
        this.imagemInvertida = ResourceManager.getImagens(nomeImagemInvertida);
        this.imagemVarianteInvertida = ResourceManager.getImagens(nomeImagemVarianteInvertida);

        this.olhandoParaEsquerda = true;
        this.alcanceAtaque = 0;
        this.tempoEsquiva = 0;
        this.tempoMorte = 0;
        this.tempoAtaque = 0;
    }

    public Personagem(TipoPersonagem Tipo, double chanceDeEsquivar, int posX, int posY, String nomeImagemInicial, String nomeImagemInvertida, double velocidade, int vida) {
        this.tipo = Tipo;
        this.chanceDeEsquivar = chanceDeEsquivar;
        this.posX = posX;
        this.posY = posY;
        this.nomeImagemInicial = nomeImagemInicial;
        this.velocidade = velocidade;
        this.vida = vida;
        this.vidaMaxima = vida;

        this.imagemInvertida = ResourceManager.getImagens(nomeImagemInvertida);
        this.imagemNormal = ResourceManager.getImagens(nomeImagemInicial);
        this.imagemVariante = null;

        this.olhandoParaEsquerda = true;
        this.alcanceAtaque = 0;
        this.tempoEsquiva = 0;
        this.tempoMorte = 0;
        this.tempoAtaque = 0;
    }

    /**
     * Desenhando o personagem, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o personagem
     */
    public abstract void desenhar(Graphics g, JPanel painel);

    // Define o alcance de ataque do personagem (diferente para cada tipo de personagem)
    public abstract void setAlcanceAtaque();

    public final void zerarAlcanceAtaque() {
        this.alcanceAtaque = 0;
    }

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

    public final int getVidaMaxima() {
        return vidaMaxima;
    }

    public final int getX() {
        return this.posX;
    }
    public final int getY() {
        return this.posY;
    }

    public final void sofreDano(int vida) {
        this.vida -= vida;
        if (this.vida <= 0)
            tempoMorte = System.currentTimeMillis();
        //O print é teste
        System.out.println("Personagem em (" + this.posX + "," + this.posY + ") sofreu " + vida + " de dano. Vida restante: " + this.vida + "\n");
    }

    public final void inverter(){
        this.olhandoParaEsquerda = !this.olhandoParaEsquerda;
    }

    public final TipoPersonagem getTipo() {
        return tipo;
    }

    public final void desenharAlcanceAtaque(Graphics g) {
        if (this.alcanceAtaque > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 255, 50)); // azul transparente
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(
                    this.posX + imagemNormal.getWidth(null) / 2 - alcanceAtaque,
                    this.posY + imagemNormal.getHeight(null) / 2 - alcanceAtaque,
                    alcanceAtaque * 2,
                    alcanceAtaque * 2
            );
        }
    }


    public final void drawBarra(Graphics g) {
        int x = this.getX();
        int y = this.getY();

        // caso quisesse add o personagem junto com a barra de vida (para ter um codigo mais legivel)
        //g.drawImage(this.icone , x, y, null);

        // DESENHAR BARRA DE VIDA
        int vidaMax = this.getVidaMaxima();
        int vida = this.getVida();

        int barWidth = 40;
        int barHeight = 5;
        int barX = x;
        int barY = y - 10; // acima da cabeça

        // barra verde proporcional
        int vidaLargura = (int) ((vida / (double) vidaMax) * barWidth);
        if((double) getVida() /getVidaMaxima() < 0.25){
            g.setColor(Color.RED);
        }else if((double) getVida() /getVidaMaxima() > 0.75){
            g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.YELLOW);
        }
        g.fillRect(barX, barY, vidaLargura, barHeight);

        // borda
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    public boolean esquivar() {
        Random gerador = new Random();
        if (gerador.nextDouble() < chanceDeEsquivar) {
            esquivando = true;
            tempoEsquiva = System.currentTimeMillis();
            return true;
        }
        return false;
    }


    public final void desenharEsquiva(Graphics g){
        int x = this.getX();
        int y = this.getY();

        String texto = "ESQUIVOU!";

        int textoX = x;
        int textoY = y - 20;  // 20 pixels acima da cabeça

        g.setColor(Color.BLACK);
        if(this.esquivando){
            g.drawString(texto, textoX, textoY);
        }
        g.drawString("", textoX, textoY);
    }

    public final void alterarEsquivando(){
        this.esquivando = !this.esquivando;
    }

    public final boolean getEsquivando(){
        return this.esquivando;
    }

    public final int getAlcanceAtaque() {
        return this.alcanceAtaque;
    }

}
