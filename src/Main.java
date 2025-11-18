import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;

public class Main {
    static String primo = "";
    static PrintWriter pw;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();


        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleziona il file dove salvare i risultati");

        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                pw = new PrintWriter(new FileWriter(chooser.getSelectedFile(), true));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("Nessun file selezionato. Programma terminato.");
            return;
        }


        System.out.print("Inserisci la lunghezza della gara: ");
        int lunghezza = Integer.parseInt(input.nextLine());
        scriviNelFile("Lunghezza gara: " + lunghezza + " metri");

        ArrayList<Cavallo> listaCavallo = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            System.out.print("Inserisci il nome del cavallo " + i + ": ");
            String nome = input.nextLine();
            System.out.print("Inserisci la lentezza del cavallo " + i + " (ms tra passi): ");
            int lentezza = Integer.parseInt(input.nextLine());
            Cavallo c = new Cavallo(nome, lentezza, lunghezza);
            listaCavallo.add(c);
        }

        for (Cavallo c : listaCavallo) {
            c.start();
        }

        Thread interruptManager = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000 + r.nextInt(3000));
                } catch (InterruptedException e) {
                    break;
                }
                if (!listaCavallo.isEmpty()) {
                    int index = r.nextInt(listaCavallo.size());
                    Cavallo scelto = listaCavallo.get(index);
                    if (scelto.isAlive()) {
                        scelto.azzoppa();
                    }
                }
            }
        });

        interruptManager.start();

        for (Cavallo c : listaCavallo) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        interruptManager.interrupt();

        System.out.println("\nVincitore: " + getPrimo());
        scriviNelFile("\nVincitore: " + getPrimo());
        System.out.println("Gara terminata!");
        scriviNelFile("Gara terminata!");

        pw.close();
    }

    public static String getPrimo() {
        return primo;
    }

    public static void setPrimo(String primo) {
        Main.primo = primo;
    }

    public static synchronized void scriviNelFile(String testo) {
        if (pw != null) {
            pw.println(testo);
            pw.flush();
        }
    }
}
