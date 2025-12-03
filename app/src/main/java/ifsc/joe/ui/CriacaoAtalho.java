package ifsc.joe.ui;

import ifsc.joe.enums.TipoPersonagem;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CriacaoAtalho extends AbstractAction {
    private final Tela tela;
    private final TipoPersonagem tipoPersonagem;
    private final PainelControles painel;

    public CriacaoAtalho(TipoPersonagem tipoPersonagem, Tela tela, PainelControles painel) {
        this.tipoPersonagem = tipoPersonagem;
        this.tela = tela;
        this.painel = painel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] pos = painel.posicaoCriacao();
        tela.criarPersonagem(tipoPersonagem, pos[0], pos[1]);
    }
}
