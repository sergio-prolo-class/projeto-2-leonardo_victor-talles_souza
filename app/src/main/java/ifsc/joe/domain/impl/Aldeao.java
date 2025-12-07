package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.enums.Recurso;
import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static java.lang.Math.pow;

public class Aldeao extends Personagem implements ComMontaria, Coletador {

    public static final Set<Recurso> COLETAVEIS;
    private boolean montado;
    private int comidaColetada;
    private int ouroColetado;

    static {
        COLETAVEIS = Set.of(Recurso.COMIDA, Recurso.OURO);
    }

    public Aldeao(int posX, int posY) {
        super(TipoPersonagem.ALDEAO,
                Constantes.ALDEAO_ALCANCE_ATAQUE,
                posX,
                posY,
                Constantes.NOME_IMAGEM_ALDEAO,
                Constantes.NOME_IMAGEM_ALDEAO_MONTADO,
                Constantes.NOME_IMAGEM_ALDEAO_INVERTIDA,
                Constantes.NOME_IMAGEM_ALDEAO_MONTADO_INVERTIDA,
                Constantes.ALDEAO_VELOCIDADE,
                Constantes.ALDEAO_VIDA_INICIAL);
        this.icone = this.imagemNormal;
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
        if (this.getVida() <= 0){
            this.icone = Sangramento;
        }else if(this.montado){
            this.icone = olhandoParaEsquerda ? imagemVariante : imagemVarianteInvertida;
        }else{
            this.icone = olhandoParaEsquerda ? this.imagemNormal : this.imagemInvertida;
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
                ? Constantes.ALDEAO_VELOCIDADE_MONTADO
                : Constantes.ALDEAO_VELOCIDADE;
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

    @Override
    public void setAlcanceAtaque() {
        this.alcanceAtaque = Constantes.ALDEAO_ALCANCE_ATAQUE;
    }
}