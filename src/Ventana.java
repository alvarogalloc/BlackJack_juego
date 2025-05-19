import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ventana extends JFrame {

    public JPanel panel;

    public Ventana() {
        this.setTitle("BlackJack");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 600);
        this.setLocationRelativeTo(null);
        componentes();
    }

    private BufferedImage fondo;

    private void componentes(){
        paneles();
        cartasVolteadas();
        fichas();
        textos();
    }

    private void paneles(){
        try {
            fondo = ImageIO.read(new File("src\\blackjack-table.jpg"));
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + e);
            fondo = null;
        }

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setOpaque(false);
        this.getContentPane().add(panel);
        panel.setLayout(null);
    }

    private void cartasVolteadas(){
        JLabel cartaMonton1 = new JLabel();
        JLabel cartaMonton2 = new JLabel();
        JLabel cartaMonton3 = new JLabel();

        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/cartas/red_back.png"));
        Image cartaOriginal = iconoOriginal.getImage();
        Image cartaEscalada = cartaOriginal.getScaledInstance(90, 130, Image.SCALE_SMOOTH);

        ImageIcon iconoEscalado = new ImageIcon(cartaEscalada);

        cartaMonton1.setIcon(iconoEscalado);
        cartaMonton2.setIcon(iconoEscalado);
        cartaMonton3.setIcon(iconoEscalado);
        cartaMonton1.setBounds(20, 20, 90, 130);
        cartaMonton2.setBounds(27, 27, 90, 130);
        cartaMonton3.setBounds(34, 34, 90, 130);

        panel.add(cartaMonton1);
        panel.add(cartaMonton2);
        panel.add(cartaMonton3);

    }

    private void fichas(){
        JButton ficha100 = new JButton();
        JButton ficha50 = new JButton();
        JButton ficha10 = new JButton();

        ficha100.setBounds(30,300,75,75);
        ficha50.setBounds(30,375,75,75);
        ficha10.setBounds(30,450,75,75);

        ficha100.setOpaque(false);
        ficha100.setContentAreaFilled(false);
        ficha100.setBorderPainted(false);

        ficha50.setOpaque(false);
        ficha50.setContentAreaFilled(false);
        ficha50.setBorderPainted(false);

        ficha10.setOpaque(false);
        ficha10.setContentAreaFilled(false);
        ficha10.setBorderPainted(false);

        ImageIcon icon100 = new ImageIcon(getClass().getResource("/100.png"));
        ImageIcon icon50 = new ImageIcon(getClass().getResource("/50.png"));
        ImageIcon icon10 = new ImageIcon(getClass().getResource("/10.png"));

        ficha100.setIcon(new ImageIcon(icon100.getImage().getScaledInstance(ficha100.getWidth(),ficha100.getHeight(),Image.SCALE_SMOOTH)));
        ficha50.setIcon(new ImageIcon(icon50.getImage().getScaledInstance(ficha50.getWidth(),ficha50.getHeight(),Image.SCALE_SMOOTH)));
        ficha10.setIcon(new ImageIcon(icon10.getImage().getScaledInstance(ficha10.getWidth(),ficha10.getHeight(),Image.SCALE_SMOOTH)));

        panel.add(ficha100);
        panel.add(ficha50);
        panel.add(ficha10);
    }

    private void textos(){
        JLabel apuestaTexto = new JLabel("Apuesta: $");
        JLabel dineroTexto = new JLabel("Dinero: $");
        Font fuentePersonalizada = new Font("Times New Roman",Font.BOLD, 30);
        Color fondo = new Color(67, 130, 255, 190);

        apuestaTexto.setBounds(20, 230, 200, 50);
        dineroTexto.setBounds(940, 10, 300, 50);

        apuestaTexto.setFont(fuentePersonalizada);
        dineroTexto.setFont(fuentePersonalizada);
        apuestaTexto.setForeground(Color.black);
        dineroTexto.setForeground(Color.black);
        apuestaTexto.setBackground(fondo);
        apuestaTexto.setOpaque(true);
        dineroTexto.setBackground(fondo);
        dineroTexto.setOpaque(true);


        panel.add(apuestaTexto);
        panel.add(dineroTexto);
    }
    
}