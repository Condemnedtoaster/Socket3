package server1;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class server {




    /**
     * Bog: Avanceret Java
     * Kapitel # - Netværk - Sockets - Server.java
     * @author Sonny Sandberg
     */
    public static class Server
    {
        public static void main(String[] args)
        {
            try
            {
                // ServerSocket oprettes og port 8001 angives som den der skal lyttes på
                ServerSocket ss = new ServerSocket(8001);
                System.out.println("Server kører...");

                while(true)
                {
                    // Så længe der ikke er oprettet en forbindelse, venter serveren her
                    // Så snart der anmodes om en forbindelse accepteres den med accept()
                    Socket s = ss.accept();
                    System.out.println("Klient forbundet.");

                /*
                Vi har behov for at kommunikere med klienten. Vi opretter derfor
                en input og en output stream, og binder hver især til Socket'ens
                input og output stream.
                Sockets kører i full-duplex og der er dermed tovejs kommunikation
                til rådighed.
                */
                    InputStream input = s.getInputStream();
                    OutputStream output = s.getOutputStream();

                /*
                Til at læse input streamen med bruger vi her en scanner.
                Det kunne lige så godt have været en BufferedReader
                (hvis forbindelsen der modtages fra lukker efter sig,
                ellers stopper den aldrig med at læse)
                */
                    Scanner in = new Scanner(input);

                    // Når vi skriver til output streamen bruger vi her en PrintWriter.
                    PrintWriter out = new PrintWriter(output, true);

                /*
                Lad os sige velkommen til den der har forbundet til serveren,
                så den ved der er hul igennem.
                */
                    out.println("Velkommen");

                /*
                Vi ønsker at have kontrol over hvornår forbindelsen skal lukkes
                fra denne side.
                Vi ønsker kun at lukke forbindelse, når brugeren skriver "luk ned"
                */
                    boolean done = false;
                    while (!done && in.hasNextLine())
                    {
                    /*Her starter scannerens arbejde. Hvis der ikke er nogle
                    linier, afventer den til der kommer en.
                    */
                        String stream = in.nextLine();
                        if (stream.equals("luk ned"))
                        {
                            done = true;
                        }
                        else
                        {
                            // Når vi skriver, sender vi en linie med PrintWriter
                            out.println(stream);
                        }
                    }

                    s.close();
                    System.out.println("Forbindelsen til klienten blev lukket.");
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }


}
