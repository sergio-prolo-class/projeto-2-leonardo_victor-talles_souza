package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static java.lang.Math.pow;

public class Cavaleiro extends Personagem implements ComMontaria, Guerreiro {

    private boolean montado;
    private int ataque;

    // Construtor
    public Cavaleiro(int posX, int posY) {
        super(TipoPersonagem.CAVALEIRO
                ,posX,
                posY,
                Constantes.NOME_IMAGEM_CAVALEIRO_MONTADO,
                Constantes.NOME_IMAGEM_CAVALEIRO,
                Constantes.NOME_IMAGEM_CAVALEIRO_MONTADO_INVERTIDA,
                Constantes.NOME_IMAGEM_CAVALEIRO_INVERTIDA,
                Constantes.CAVALEIRO_VELOCIDADE,
                Constantes.CAVALEIRO_VIDA_INICIAL);
        this.icone = this.imagemNormal;
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
        if (this.getVida() <= 0){
            this.icone = Sangramento;
        }else if(this.montado){
            this.icone = this.olhandoParaEsquerda ? this.imagemNormal : this.imagemInvertida;
        }else{
            this.icone = this.olhandoParaEsquerda ? this.imagemVariante : this.imagemVarianteInvertida;
        }
        g.drawImage(this.icone, this.posX, this.posY, painel);
        // desenhando de fato a imagem no pai
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
     */
    @Override
    public void atacar(Personagem p) {
        if(pow((p.getX() - this.posX),2) + pow((p.getY() - this.posY),2) <= 705) {
            p.sofreDano(this.ataque);
        }
    }
}
