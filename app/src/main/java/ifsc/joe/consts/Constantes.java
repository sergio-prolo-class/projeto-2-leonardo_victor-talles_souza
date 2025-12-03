package ifsc.joe.consts;

import javax.swing.*;
import java.awt.event.KeyEvent;

public final class Constantes {

    // Constates da criação das imagens
    public static final String NOME_IMAGEM_ALDEAO = "aldeao";
    public static final String NOME_IMAGEM_CAVALEIRO = "cavaleiro";
    public static final String NOME_IMAGEM_ARQUEIRO = "arqueiro";

    // Constantes do Aldeão
    public static final int ALDEAO_VIDA_INICIAL = 25;
    public static final int ALDEAO_ATAQUE = 1;
    public static final double ALDEAO_VELOCIDADE = 0.8;
    public static final double ALDEAO_VELOCIDADE_MONTADO = 1.8;

    // Constates do Cavaleiro
    public static final int CAVALEIRO_VIDA_INICIAL = 50;
    public static final int CAVALEIRO_ATAQUE = 3;
    public static final double CAVALEIRO_VELOCIDADE = 2;
    public static final double CAVALEIRO_VELOCIDADE_NAO_MONTADO = 1;

    // Constantes do Arqueiro
    public static final int ARQUEIRO_VIDA_INICIAL = 35;
    public static final int ARQUEIRO_ATAQUE = 2;
    public static final double ARQUEIRO_VELOCIDADE = 1;
    public static final int ARQUEIRO_FLECHAS_INICIAL = 5;
    public static final int ARQUEIRO_FLECHAS_PRODUCAO = 10;

    public static final int PADDING = 50;
    public static final String TITULO = "Java of Empires";


    public static final KeyStroke ky1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, 0);
    public static final KeyStroke ky2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, 0);
    public static final KeyStroke ky3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, 0);
    public static final KeyStroke kyM = KeyStroke.getKeyStroke(KeyEvent.VK_M, 0);
    public static final KeyStroke kySpace  = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
    public static final KeyStroke kyTab  = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);



}
