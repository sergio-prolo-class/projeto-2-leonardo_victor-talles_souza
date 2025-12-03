package ifsc.joe.ui;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MovimentoAtalho extends AbstractAction {
    private final Direcao direcao;
    private final Tela tela;
    private final PainelControles painel;

    public MovimentoAtalho(Direcao direcao, Tela tela, PainelControles painel) {
        this.direcao = direcao;
        this.tela = tela;
        this.painel = painel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tela.movimentarPersonagem(direcao, painel.getClassePersonagemAtual());
    }
}