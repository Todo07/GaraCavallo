import java.util.Random;

public class Cavallo extends Thread {
    private int lunghezza;
    private String nome;
    private int lentezza;
    private final int PASSO = 5;
    private boolean azzoppato = false;
/**classe cavallo*/
    public Cavallo(String nome, int lentezza, int lunghezza) {
        this.nome = nome;
        this.lentezza = lentezza;
        this.lunghezza = lunghezza;
    }

    @Override
    public void run() {
        System.out.println("Cavallo " + nome + " comincia il suo galoppo!");
        Main.scriviNelFile("Cavallo " + nome + " comincia il suo galoppo!");
        lunghezza = lunghezza / PASSO;

        for (int i = 1; i <= lunghezza; i++) {
            try {
                sleep(lentezza);
            } catch (InterruptedException e) {
                if (azzoppato) {
                    System.out.println("Cavallo " + nome + " e' stato AZZOPPATO e si ferma al metro " + i * PASSO);
                    Main.scriviNelFile("Cavallo " + nome + " e' stato AZZOPPATO e si ferma al metro " + i * PASSO);
                    return;
                } else {
                    e.printStackTrace();
                }
            }

            System.out.println(nome + " cavalca - passo: " + i);
            Main.scriviNelFile(nome + " cavalca - passo: " + i);

            if (Main.getPrimo().equals("")) {
                Main.setPrimo(this.nome);
            }
        }

        System.out.println(nome + " ha terminato la gara!");
        Main.scriviNelFile(nome + " ha terminato la gara!");
    }

    public void azzoppa() {
        azzoppato = true;
        this.interrupt();
    }

    public String getNome() {
        return nome;
    }
}