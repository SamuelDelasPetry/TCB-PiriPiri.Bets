package BlackJack;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

/*C - Clubs - Pau
 * H - Hearts - Copas
 * S - Spades - Espada
 * D - Diamonds - Ouro
 */

public class BlackJack {
    private PiriPiriBets cassino;

    private class Carta {
        String naipe;
        String valor;

        public Carta(String valor, String naipe) {
            this.valor = valor;
            this.naipe = naipe;
        }

        public String toString() {
            return valor + '-' + naipe;
        }

        public String encontrarCaminhoImagem() {
            return "./cards/" + toString() + ".png";
        }

    }

    private Random r = new Random();

    ArrayList<Carta> baralho = new ArrayList<>();
    ArrayList<Carta> baralhoEmbaralhado = new ArrayList<>();

    ArrayList<Carta> maoJogador = new ArrayList<>();
    ArrayList<Carta> maoDealer = new ArrayList<>();

    Carta cartaEscondida;

    int valorAposta, somaJogador, somaDealer, qtdAsesJogador = 0, qtdAsesDealer = 0;

    String[] valores = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    String[] naipes = { "C", "H", "S", "D" };

    String aposta;

    boolean isBlackjack;

    // tela
    int cardWidth = 135, cardHeight = 190;

    JFrame frame = new JFrame("BlackJack");

    JTextField campoAposta = new JTextField(5);

    JLabel infoJogador = new JLabel("Soma Jogador: 0");
    JLabel infoDealer = new JLabel("Soma Dealer: 0");
    JLabel vencedor = new JLabel("");
    JLabel labelSaldo = new JLabel("");
    JLabel Aposta = new JLabel("Aposta: ");

    JButton confirmarAposta = new JButton("Confirmar Aposta");
    JButton bater = new JButton("Bater");
    JButton parar = new JButton("Parar");
    JButton jogarNovamente = new JButton("Jogar novamente");

    JPanel painelBotoes = new JPanel();
    JPanel painel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {

                Image cartaEscondidaImage = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if (!parar.isEnabled()) {
                    cartaEscondidaImage = new ImageIcon(getClass().getResource(cartaEscondida.encontrarCaminhoImagem()))
                            .getImage();
                }

                g.drawImage(cartaEscondidaImage, 15, 25, cardWidth, cardHeight, null);

                for (int i = 0; i < maoDealer.size(); i++) {
                    Image carta = new ImageIcon(getClass().getResource(maoDealer.get(i).encontrarCaminhoImagem()))
                            .getImage();
                    g.drawImage(carta, cardWidth + 20 + (cardWidth + 5) * i, 25, cardWidth, cardHeight, null);
                }

                for (int i = 0; i < maoJogador.size(); i++) {
                    Image carta = new ImageIcon(getClass().getResource(maoJogador.get(i).encontrarCaminhoImagem()))
                            .getImage();
                    g.drawImage(carta, 15 + (cardWidth + 5) * i, 315, cardWidth, cardHeight, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BlackJack(PiriPiriBets piriPiriBets) {
        jogarNovamente.setVisible(false);
        parar.setVisible(false);
        bater.setVisible(false);

        this.cassino = piriPiriBets;
        labelSaldo.setText("Saldo: " + cassino.getSaldo());

        ImageIcon icone = new ImageIcon("BlackJack\\blackjackIcon.jpg");
        Border borda = BorderFactory.createLineBorder(new Color(82, 54, 41), 10);

        frame.setIconImage(icone.getImage());
        frame.setTitle("BlackJack");
        frame.setVisible(true);
        frame.setSize(cassino.getWidth(), cassino.getHeight());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(35, 85, 50));
        painel.setBorder(borda);

        frame.add(painel);

        painelBotoes.add(labelSaldo);
        painelBotoes.add(Aposta);
        painelBotoes.add(campoAposta);
        painelBotoes.add(confirmarAposta);

        bater.setEnabled(false);
        parar.setEnabled(false);
        jogarNovamente.setEnabled(false);
        infoDealer.setText("");
        infoJogador.setText("");

        bater.setFocusable(false);
        painelBotoes.add(bater);

        parar.setFocusable(false);
        painelBotoes.add(parar);

        jogarNovamente.setFocusable(false);
        painelBotoes.add(jogarNovamente);

        frame.add(painelBotoes, BorderLayout.SOUTH);

        infoDealer.setFont(new Font("Arial", Font.BOLD, 18));
        infoDealer.setForeground(Color.WHITE);

        infoJogador.setFont(new Font("Arial", Font.BOLD, 18));
        infoJogador.setForeground(Color.WHITE);

        vencedor.setFont(new Font("Arial", Font.BOLD, 22));
        vencedor.setForeground(Color.PINK);

        painel.setLayout(null);

        infoDealer.setBounds(20, 220, 300, 25);
        infoJogador.setBounds(20, 280, 300, 25);
        vencedor.setBounds(300, 250, 300, 25);

        painel.add(infoDealer);
        painel.add(infoJogador);
        painel.add(vencedor);

        confirmarAposta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aposta = campoAposta.getText();

                try {
                    valorAposta = Integer.parseInt(aposta);
                    if (valorAposta <= cassino.getSaldo() && valorAposta > 0) {
                        cassino.setSaldo(cassino.getSaldo() - valorAposta);
                        labelSaldo.setText("Saldo: " + cassino.getSaldo());
                        campoAposta.setEditable(false);
                        confirmarAposta.setEnabled(false);
                        bater.setVisible(true);
                        parar.setVisible(true);
                        jogarNovamente.setVisible(true);
                        painel.repaint();
                        confirmarAposta.setVisible(false);
                        iniciarJogo();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Digite um numero válido.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, digite um número válido para a aposta.");
                }

            }
        });

        bater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maoJogador.add(baralho.get(baralho.size() - 1));
                somaJogador += adicionarSoma(baralho.get(baralho.size() - 1), qtdAsesJogador);
                if (isAce(baralho.remove(baralho.size() - 1))) {
                    qtdAsesJogador++;
                }

                somaJogador = reduzirAsesJogador(somaJogador);

                javax.swing.Timer timer = new javax.swing.Timer(80, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        atualizarInfos();
                        painel.repaint();
                    }
                });
                timer.setRepeats(false);
                timer.start();

                if (somaJogador >= 21) {
                    bater.setEnabled(false);
                    parar.doClick();
                }

            }
        });

        parar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bater.setEnabled(false);
                parar.setEnabled(false);

                if (somaJogador < 21) {
                    while (somaDealer < 17 && somaDealer < somaJogador) {
                        somaDealer += adicionarSoma(baralho.get(baralho.size() - 1), qtdAsesDealer);
                        qtdAsesDealer += isAce(baralho.get(baralho.size() - 1)) ? 1 : 0;
                        somaDealer = reduzirAsesDealer(somaDealer);
                        maoDealer.add(baralho.remove(baralho.size() - 1));
                    }
                }

                javax.swing.Timer timer = new javax.swing.Timer(80, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        atualizarInfos();
                        descobrirGanhador();
                        atualizarSaldo();
                        painel.repaint();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        jogarNovamente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetarJogo();
            }
        });
    }

    public void iniciarJogo() {
        bater.setEnabled(true);
        parar.setEnabled(true);

        fazerBaralho();
        embaralhar();
        entregarCartas();

        if (somaJogador == 21) {
            bater.setEnabled(false);
            jogarNovamente.setEnabled(false);
            isBlackjack = true;
            javax.swing.Timer timer = new javax.swing.Timer(550, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    parar.doClick();
                    jogarNovamente.setEnabled(true);
                    bater.setEnabled(true);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

        atualizarInfos();

        System.out.println("Dealer: " + maoDealer + " - " + somaDealer);
        System.out.println("Jogador: " + maoJogador + " - " + somaJogador);
    }

    private void fazerBaralho() {
        for (int i = 0; i < valores.length; i++) {
            for (int j = 0; j < naipes.length; j++) {
                Carta carta = new Carta(valores[i], naipes[j]);
                baralho.add(carta);
            }
        }
        System.out.println("Baralho:");
        System.out.println(baralho);
    }

    // embaralhamento Fisher-Yates
    private void embaralhar() {
        for (int i = baralho.size() - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);

            Carta temp = baralho.get(i);
            baralho.set(i, baralho.get(j));
            baralho.set(j, temp);
        }

        System.out.println("Baralho embaralhado:");
        System.out.println(baralho);
    }

    private void entregarCartas() {
        cartaEscondida = baralho.remove(baralho.size() - 1);
        qtdAsesDealer += isAce(cartaEscondida) ? 1 : 0;
        qtdAsesDealer += isAce(baralho.get(baralho.size() - 1)) ? 1 : 0;
        maoDealer.add(baralho.remove(baralho.size() - 1));
        somaDealer += adicionarSoma(cartaEscondida, qtdAsesDealer);
        somaDealer += adicionarSoma(maoDealer.get(0), qtdAsesDealer);

        somaDealer = reduzirAsesDealer(somaDealer);

        for (int i = 0; i < 2; i++) {
            qtdAsesJogador += isAce(baralho.get(baralho.size() - 1)) ? 1 : 0;
            maoJogador.add(baralho.remove(baralho.size() - 1));
            somaJogador += adicionarSoma(maoJogador.get(i), qtdAsesJogador);
        }
    }

    public boolean isAce(Carta carta) {
        return carta.valor.equals("A");
    }

    public int adicionarSoma(Carta carta, int qtdAses) {
        if ("AQJK".contains(carta.valor)) {
            if (carta.valor.contains("A")) {
                qtdAses++;
                return 11;
            } else
                return 10;
        } else {
            return Integer.parseInt(carta.valor);
        }
    }

    public int reduzirAsesJogador(int soma) {
        while (soma > 21 && qtdAsesJogador > 0) {
            soma -= 10;
            qtdAsesJogador--;
        }
        return soma;
    }

    public int reduzirAsesDealer(int soma) {
        while (soma > 21 && qtdAsesDealer > 0) {
            soma -= 10;
            qtdAsesDealer--;
        }
        return soma;
    }

    public void atualizarInfos() {
        infoDealer.setText("Dealer: " + (parar.isEnabled() ? "?" : somaDealer));
        infoJogador.setText("Jogador: " + somaJogador);
    }

    public void descobrirGanhador() {
        if (somaJogador > 21 || somaDealer > somaJogador && somaDealer <= 21) {
            vencedor.setText("Dealer Venceu!");
        } else if (somaDealer > 21 || somaJogador > somaDealer && somaJogador <= 21) {
            vencedor.setText("Jogador Venceu!");
        } else
            vencedor.setText("Empate!");

        jogarNovamente.setEnabled(true);

    }

    public void atualizarSaldo() {
        if (vencedor.getText().equals("Jogador Venceu!") && !isBlackjack) {
            cassino.setSaldo(cassino.getSaldo() + valorAposta * 2);
        } else if (isBlackjack) {
            cassino.setSaldo(cassino.getSaldo() + valorAposta * 2.5);
        } else if (vencedor.getText().equals("Empate!")) {
            cassino.setSaldo(cassino.getSaldo() + valorAposta);
        }
    }

    public void resetarJogo() {
        baralho.clear();
        maoJogador.clear();
        maoDealer.clear();

        cartaEscondida = null;
        valorAposta = 0;
        somaJogador = 0;
        somaDealer = 0;
        qtdAsesJogador = 0;
        qtdAsesDealer = 0;
        isBlackjack = false;
        aposta = "";

        infoDealer.setText("");
        infoJogador.setText("");
        vencedor.setText("");

        campoAposta.setText("");
        campoAposta.setEnabled(true);
        campoAposta.setEditable(true);
        confirmarAposta.setEnabled(true);
        confirmarAposta.setVisible(true);

        bater.setEnabled(false);
        bater.setVisible(false);
        parar.setEnabled(false);
        parar.setVisible(false);
        jogarNovamente.setEnabled(false);
        jogarNovamente.setVisible(false);

        Aposta.setVisible(true);

        labelSaldo.setText("Saldo: " + cassino.getSaldo());

        painel.repaint();
    }

}
