/** Copyright [2019] [Andres Iturria Soler, Javier Linares Castrillon, Samuel Aragones Lozano] Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */package app.before;import DAO.Conexion;import javax.swing.*;import java.awt.*;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;/** * * @author Javier Linares Castrillon * @author Andrés Iturria Soler * *//** * Clase previa encargada de mostrar la página de inicio. */public class Begin extends JPanel{    private static JFrame main = new JFrame();    private JButton start = new JButton();    private JButton help = new JButton();    private ImageIcon ic = new ImageIcon("res/imagenes/help.png");    private ImageIcon flecha = new ImageIcon("res/imagenes/fAmarilla.png");    private Icon flechaAmarilla = new ImageIcon(flecha.getImage().getScaledInstance(512/10, 512/10, Image.SCALE_DEFAULT));    private PokedexFondo pf = new PokedexFondo();    private static JFrame f = new JFrame("->");    private static ImageIcon icon = new ImageIcon("res/imagenes/fotos/0.png");    private static Icon pokemons = new ImageIcon(icon.getImage().getScaledInstance(512/10, 512/10, Image.SCALE_DEFAULT));    private static JButton b = new JButton();    /**     * Inicializa el frame del principio.     */    public static void comenzar(){        paraComenzar();    }    private static void paraComenzar(){        b.setIcon(pokemons);        b.setOpaque(true);        b.setContentAreaFilled(false);        b.setBorderPainted(false);        b.setBorder(null);        f.add(b);        b.addActionListener(new ActionListener() {            @Override            public void actionPerformed(ActionEvent e) {                f.dispose();                init();            }        });        f.setSize(512/10*2,512/10*2);        f.setVisible(true);        f.setResizable(false);        f.setLocation(100,100);        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        f.setBackground(new Color(180, 15, 15));    }    private static void init(){        main.setTitle("Pokedex");        main.setBackground(new Color(180, 15, 15));        main.setVisible(true);        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        main.setLocation(200,40);        main.setResizable(false);        main.setSize(1600/4, 2846/4);        main.add(new Begin());    }    /**     * Construye un panel con sus componentes.     */    private Begin(){        setLayout(null);        initComponents();        Conexion.conectar();    }    private  void initComponents() {        setLayout(null);        start.setBounds(10, (2846 / 4) / 2 + 4, 60, 60);        start.setVisible(true);        start.setOpaque(true);        start.setContentAreaFilled(false);        start.setBorderPainted(false);        start.setBorder(null);        start.addActionListener(new ActionListener() {            @Override            public void actionPerformed(ActionEvent e) {                begin();            }        });        add(start);        help.setBounds(22, 32, 60, 60);        help.setIcon(new ImageIcon(ic.getImage().getScaledInstance(help.getWidth(), help.getHeight(), Image.SCALE_SMOOTH)));        help.setVisible(true);        help.setOpaque(true);        help.setContentAreaFilled(false);        help.setBorderPainted(false);        help.setBorder(null);        help.addActionListener(new ActionListener() {            @Override            public void actionPerformed(ActionEvent e) {                JOptionPane.showMessageDialog(help, "Pulsa en la flecha amarilla para abrir la Pokedex", "Ayuda", 1, flechaAmarilla);            }        });        add(help);        add(pf);    }    private final void begin(){        main.dispose();        Start.start();    }}