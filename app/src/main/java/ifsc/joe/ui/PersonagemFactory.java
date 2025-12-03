package ifsc.joe.ui;

import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.TipoPersonagem;

/**
 * Classe responsável por criar os personagens
 */
public class PersonagemFactory {
    public static Personagem criar(TipoPersonagem tipoPersonagem, int posX, int posY) {
        return switch (tipoPersonagem) {
            case CAVALEIRO -> new Cavaleiro(posX, posY);
            case ARQUEIRO -> new Arqueiro(posX, posY);
            case ALDEAO -> new Aldeao(posX, posY);
        };
    }
}
