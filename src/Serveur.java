
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        ServerSocket serveur = new ServerSocket(1234);

        System.out.println("Serveur en attente d'un client...");

        Socket socket = serveur.accept();

        System.out.println("Client connecte.");

        ObjectInputStream in = new ObjectInputStream(
                socket.getInputStream());

        ObjectOutputStream out = new ObjectOutputStream(
                socket.getOutputStream());

        while (true) {

            Object objet = in.readObject();

            if (objet instanceof String) {
                if (objet.equals("quit")) {
                    out.writeObject("Connexion terminee.");
                    out.flush();
                    break;
                }
            }

            if (objet instanceof Operation) {

                Operation operation = (Operation) objet;

                double a = operation.getOp1();
                double b = operation.getOp2();
                String operateur = operation.getOperateur();

                double resultat = 0;
                boolean valide = true;

                switch (operateur) {

                    case "+":
                        resultat = a + b;
                        break;

                    case "-":
                        resultat = a - b;
                        break;

                    case "*":
                        resultat = a * b;
                        break;

                    case "/":
                        if (b == 0) {
                            out.writeObject(
                                    "Erreur : division par zero");
                            out.flush();
                            valide = false;
                        } else {
                            resultat = a / b;
                        }
                        break;

                    default:
                        out.writeObject("Operateur invalide");
                        out.flush();
                        valide = false;
                }

                if (valide) {
                    System.out.println("Operation recue : "
                            + a + " " + operateur + " " + b);

                    out.writeObject(resultat);
                    out.flush();

                    System.out.println(
                            "Resultat envoye : " + resultat);
                }
            }
        }

        in.close();
        out.close();
        socket.close();
        serveur.close();

        System.out.println("Serveur arrete.");
    }
}

