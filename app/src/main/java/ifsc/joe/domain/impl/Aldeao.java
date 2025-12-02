package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.enums.Recurso;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class Aldeao extends Personagem implements ComMontaria, Coletador {

    public static final Set<Recurso> COLETAVEIS;
    private boolean montado;
    private int comidaColetada;
    private int ouroColetado;

    static {
        COLETAVEIS = Set.of(Recurso.COMIDA, Recurso.OURO);
    }

    public Aldeao(int posX, int posY) {
        super(posX, posY,
                Constantes.NOME_IMAGEM_ALDEAO,
                Constantes.ALDEAO_VELOCIDADE,
                Constantes.ALDEAO_VIDA_INICIAL);
        this.icone = this.carregarImagem(this.nomeImagemBase);
    }

    /**
     * Desenhando o Aldeão, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o Aldeão
     */
    @Override
    public void desenhar(Graphics g, JPanel painel) {
        // verificando se ta vivo
        if (this.getVida() < 0) return;
        String nomeAtual = this.montado ? "aldeao-montado" : "aldeao3";
        this.icone = this.carregarImagem(nomeAtual);

        this.nomeImagemBase = nomeAtual;
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
                ? Constantes.ALDEAO_VELOCIDADE_MONTADO
                : Constantes.ALDEAO_VELOCIDADE;
    }

    /**
     * Verifica se é possível coletar o item
     *
     * @param recurso
     * @return
     */
    @Override
    public boolean coletar(Recurso recurso) {
        if (!COLETAVEIS.contains(recurso)) return false;
        if (recurso == Recurso.OURO) this.ouroColetado++;
        if (recurso == Recurso.COMIDA) this.comidaColetada++;
        return true;
    }
}