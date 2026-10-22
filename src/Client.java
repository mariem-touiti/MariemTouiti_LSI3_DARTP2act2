import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 1234);

        ObjectOutputStream out = new ObjectOutputStream(
                socket.getOutputStream());

        ObjectInputStream in = new ObjectInputStream(
                socket.getInputStream());

        Scanner clavier = new Scanner(System.in);

        System.out.println("Client connecte au serveur.");

        while (true) {

            System.out.print(
                    "Donner une operation (ex: 55 * 25) ou quit : ");

            String texte = clavier.nextLine().trim();

            if (texte.equalsIgnoreCase("quit")) {
                out.writeObject("quit");
                out.flush();

                System.out.println(in.readObject());
                break;
            }

            String[] parties = texte.split("\\s+");

            if (parties.length != 3) {
                System.out.println(
                        "Operation invalide ! Exemple : 55 * 25");
                continue;
            }

            double a;
            double b;
            String operateur = parties[1];

            try {
                a = Double.parseDouble(parties[0]);
                b = Double.parseDouble(parties[2]);
            } catch (NumberFormatException e) {
                System.out.println("Les nombres sont invalides.");
                continue;
            }

            if (!operateur.equals("+")
                    && !operateur.equals("-")
                    && !operateur.equals("*")
                    && !operateur.equals("/")) {

                System.out.println("Operateur invalide.");
                continue;
            }

            Operation operation =
                    new Operation(a, operateur, b);

            out.writeObject(operation);
            out.flush();

            Object resultat = in.readObject();

            System.out.println("Resultat recu : " + resultat);
        }

        in.close();
        out.close();
        socket.close();
        clavier.close();
    }
}

