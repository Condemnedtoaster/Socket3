package server1;

import java.io.*;
import java.lang.annotation.Target;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientConnection implements Runnable
{


    /**
     * Bog: Avanceret Java
     * Kapitel # - Netværk - Multitråds Server - ClientConnection.java
     * @author Sonny Sandberg
     */

        String name = "Guest";
        String addToContainer;
        int countValue;
        int index;
        String number;
        private Socket s;
        private static ArrayList<String> stringContainer = new ArrayList<>();

        public ClientConnection(Socket s) throws SocketException, IOException
        {
            this.s = s;
        }

        @Override
        public void run()
        {
            try
            {
                try
                {
                /*
                Vi har behov for at kommunikere med serveren. Vi opretter derfor
                en input og en output stream, og binder hver isæt til Socket'ens
                input og output stream.
                Sockets kører i full-duplex og der er dermed tovejs kommunikation
                til rådighed.
                */
                    InputStream input = s.getInputStream();
                    OutputStream output = s.getOutputStream();
                    Scanner in = new Scanner(input);

                   /*
                Til at læse input streamen med bruger vi her en scanner.
                Det kunne lige så godt have været en BufferedReader
                (hvis forbindelsen der modtages fra lukker efter sig,
                ellers stopper den aldrig med at læse)
                */


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
                    /*
                    Her starter scannerens arbejde. Hvis der ikke er nogle
                    linier, afventer den til der kommer en.
                    */
                    String stream = in.nextLine();
                    if (stream.equals("luk ned"))
                    {
                        done = true;
                    }
                    else if (stream.startsWith("NAME:")){
                        name = stream.substring(5);

                    }
                    else if (stream.startsWith("PUT:")){

                        addToContainer = new String(stream.getBytes()).replace("PUT:", "");
                        stringContainer.add(name+" added "+addToContainer);
                        out.println(name+" added: "+addToContainer);

                        for (String a: stringContainer){
                            System.out.println(a);

                        }


                    }
                    else if (stream.startsWith("COUNT:")){
                        for (String a : stringContainer) {
                            countValue = countValue + 1;

                        }
                        System.out.println(countValue);
                        out.println(countValue + " Have been added");
                        countValue = 0;

                    } else if (stream.startsWith("GET: ")) {
                        number = new String(stream.getBytes()).replace("GET: ", "");
                        index = Integer.valueOf(number);
                        out.println(stringContainer.get(index));


                    }
                    else
                    {
                        // Når vi skriver, sender vi en linie med PrintWriter
                        out.println("Error");
                    }
                }
            }
            finally
            {
                s.close();
            }
        }
        catch (Exception e)
    {
        e.printStackTrace();
    }
}
}
