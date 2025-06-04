package BlackJack;

public class PiriPiriBets {

    private double saldo = 1000;

    private int width = 800;
    private int height = 600;

    public PiriPiriBets() {

    }

    public void jogarBlackJack() {
        BlackJack jogo = new BlackJack(this);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}