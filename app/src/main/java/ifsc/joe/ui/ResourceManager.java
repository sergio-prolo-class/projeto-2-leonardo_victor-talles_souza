package ifsc.joe.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por gerênciar o cache de recursos
 */
public class ResourceManager {
    private static final Map<String, Image> imagens;

    static {
        imagens = new HashMap<>();
    }

    public static Image getImagens(String nomeImagem) {
        if (!imagens.containsKey(nomeImagem)) {

            URL url = ResourceManager.class.getClassLoader().getResource("./" + nomeImagem + ".png");

            if (url == null) {
                return null;
            }

            ImageIcon image = new ImageIcon(url);
            imagens.put(nomeImagem, image.getImage());
        }
        return imagens.get(nomeImagem);
    }
}
