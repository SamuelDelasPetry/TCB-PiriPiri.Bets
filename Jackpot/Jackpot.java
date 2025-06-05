package Jackpot;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import BlackJack.PiriPiriBets;

public class Jackpot {

    final static Random r = new Random();
    private PiriPiriBets cassino;

    private class Nums {
        int nums;

        public Nums() {
            nums = r.nextInt(5);

        }

        public String toString() {
            return nums + "";
        }

    }

    JFrame tela = new JFrame("Jackpot");
    JPanel num1 = new JPanel();
    JPanel num2 = new JPanel();
    JPanel num3 = new JPanel();
    JPanel painelComBorda = new JPanel();
    JPanel painelBotao = new JPanel();
    JPanel painelVitorias = new JPanel();
    JButton rodar = new JButton("Rodar");

    public Jackpot(PiriPiriBets piriPiriBets) {
        this.cassino = piriPiriBets;

        int num1X = cassino.getWidth()/2 - 325;
        int numY = cassino.getHeight()/2 - 150;

        int num2X = cassino.getWidth()/2 - 100;
        

        int num3X = cassino.getWidth()/2 + 125;
        

        ImageIcon icon = new ImageIcon("Jackpot\\SlotMachine.png");
        Border bordaNums = BorderFactory.createLineBorder(new Color(50, 50, 50), 5);
        Border bordaTela = BorderFactory.createLineBorder(new Color(25, 25, 25), 15);

        tela.setIconImage(icon.getImage());
        tela.setSize(cassino.getWidth(), cassino.getHeight());
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.getContentPane().setBackground(new Color(250 , 250 , 250));
        tela.setVisible(true);
        tela.setLayout(null);

        painelComBorda.setBorder(bordaTela);
        painelComBorda.setBackground(new Color(250 , 250 , 250));
        painelComBorda.setLayout(null);
        painelComBorda.setVisible(true);
        tela.setContentPane(painelComBorda);
        
        num1.setBounds(num1X , numY, 200 ,200);
        num1.setBackground(Color.pink);
        num1.setOpaque(true);
        num1.setFont(new Font("Arial", Font.BOLD, 72));
        num1.setBorder(bordaNums);
        tela.add(num1);

        num2.setBounds(num2X , numY , 200 ,200);
        num2.setBackground(Color.red);
        num2.setOpaque(true);
        num2.setFont(new Font("Arial", Font.BOLD, 72));
        num2.setBorder(bordaNums);
        tela.add(num2);

        num3.setBounds(num3X, numY , 200 ,200);
        num3.setBackground(Color.blue);
        num3.setOpaque(true);
        num3.setFont(new Font("Arial", Font.BOLD, 72));
        num3.setBorder(bordaNums);
        tela.add(num3);

        painelVitorias.setBounds(num1X, numY + 225, 400 , 150);
        painelVitorias.setBackground(Color.GREEN);
        painelVitorias.setOpaque(true);
        painelVitorias.setFont(new Font("Arial", Font.BOLD, 72));
        painelVitorias.setBorder(bordaNums);
        tela.add(painelVitorias);

        painelBotao.setBounds(num3X, numY + 225, num3X/2 - 45 , 75);
        painelBotao.setBackground(Color.GREEN);
        painelBotao.setOpaque(true);
        painelBotao.setFont(new Font("Arial", Font.BOLD, 72));
        painelBotao.setBorder(bordaNums);
        tela.add(painelBotao);

        
    }

}