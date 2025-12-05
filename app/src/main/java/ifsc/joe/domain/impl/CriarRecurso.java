package ifsc.joe.domain.impl;

import ifsc.joe.enums.Recurso;
import ifsc.joe.ui.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class CriarRecurso {
    private Image icone;
    private final Recurso Tipo;
    private final int posX;
    private final int posY;

    public CriarRecurso(Recurso recurso, int posX, int posY) {
        this.Tipo = recurso;
        this.posX = posX;
        this.posY = posY;
        this.icone = ResourceManager.getImagens(recurso.toString().toLowerCase());
    }

    public void desenhar(Graphics g, JPanel painel) {
        g.drawImage(this.icone, this.posX, this.posY, painel);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Recurso getTipo() {
        return Tipo;
    }
}
