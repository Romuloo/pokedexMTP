
/**
 Copyright [2019] [Andres Iturria Soler, Javier Linares Castrillon, Samuel Aragones Lozano]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
package domain.localizacion;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Javier Linares Castrillon
 */

/**
 * Clase encargada de general un panel customizado con una imagen de fondo.
 */
public class Imagen extends JPanel {

    /**
     * Constructor de la clase Imagen.
     */
    public Imagen() {
        this.setSize((1500/3)*2, (1061/3)*2);

    }

    /**
     * Pinta la imagen seleccionada en el panel.
     * @param grafico
     */
    public void paint(Graphics grafico) {
        ImageIcon Img = new ImageIcon("res/imagenes/kanto.png");
        grafico.drawImage(Img.getImage(), 0, 0, (1500/3)*2, (1061/3)*2, null);
        setOpaque(false);
        super.paint(grafico);

    }
}

