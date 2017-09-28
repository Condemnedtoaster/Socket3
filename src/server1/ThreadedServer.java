package server1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ThreadedServer {


    /**
     * Bog: Avanceret Java
     * Kapitel # - Netværk - Multitråds Server - Server.java
     * @author Sonny Sandberg
     */
        public static void main(String[] args)
        {
            // ServerSocket oprettes og port 8001 angives som den der skal lyttes på
            ServerSocket ss;
            try
            {
                ss = new ServerSocket(8001);

                System.out.println("Server kører...");
                while(true)
                {
                    // Så længe der ikke er oprettet en forbindelse, venter serveren her
                    // Så snart der anmodes om en forbindelse accepteres den med accept()
                    Socket incoming = ss.accept();
                /*
                Den nye forbindelse sendes til vores ClientConnection object,
                hvor al logik for serveren bliver håndteret.
                Der oprettes en ny tråd, som modtager ClientConnection objektet
                og tråden startes.
                */
                    Runnable r = new ClientConnection(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }







                        } catch (IOException e) {
                e.printStackTrace();
            }
        }
                        }

