package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;


public class GameServer {

    private final static Logger logger = Logger.getLogger(GameServer.class.getName());

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private int numActual;
    private int numMin;
    private int numMax;

    public void start(int port){
        try {

            serverSocket = new ServerSocket(port);
            //program stops here until a request is received
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            playGame();

            stop();

        }catch (IOException e){
            logger.severe("IOException occurred");
        }
    }

    public void stop(){
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            logger.severe("IOException occurred");
        }
    }

    public void getNumbers(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter MIN number for range:");
        numMin = scanner.nextInt();

        System.out.println("Enter MAX number for range:");
        numMax = scanner.nextInt();

        System.out.println("Enter ACTUAL number:");
        numActual = scanner.nextInt();

        while (numActual <= numMin && numActual >= numMax){
            System.out.println("Enter ACTUAL number:");
            numActual = scanner.nextInt();
        }

    }

    private void playGame(){

        try {

            out.println("Guess number between " + numMin + " and " + numMax);

            int input, count=0;
            boolean guessed = false;
            while (!guessed) {
                input = Integer.parseInt(in.readLine());
                System.out.println("The player guesses: " + count);
                if (input < numActual) {
                    out.println("higher");
                } else if (input > numActual) {
                    out.println("lower");
                } else if (input == numActual) {
                    out.println("You guessed it!\n It only took you " + count + " tries!");
                    guessed = true;
                }
                count++;
            }
            System.out.println("The player guessed it in " + count + " turns.");
        }catch (IOException e){
            logger.severe("Input error");
        }
    }

    public static void main(String[] args){
        GameServer gameServer = new GameServer();
        gameServer.getNumbers();
        gameServer.start(6666);
        gameServer.playGame();
        try {
            Thread.sleep(1000);
            System.out.println("Exiting.");
            Thread.sleep(1000);
            System.out.println("Exiting..");
            Thread.sleep(1000);
            System.out.println("Exiting...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameServer.stop();
    }

}
