package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Player {

    private final static Logger logger = Logger.getLogger(Player.class.getName());

    private Socket playerSocket;
    private PrintWriter out;
    private BufferedReader in;

    private String hostIP;
    private int hostPort;

    public void connect(){
        try {
            playerSocket = new Socket(hostIP, hostPort);
            out = new PrintWriter(playerSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        }catch (IOException e){
            logger.severe("IOException occurred");
        }
    }

    public void stopConnection(){
        try {
            in.close();
            out.close();
            playerSocket.close();
        }catch (IOException e){
            logger.severe("IOException occurred");
        }
    }

    private void getAdress(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter host IP:");
        hostIP = scanner.nextLine();

        System.out.println("Enter host port:");
        hostPort = scanner.nextInt();
    }

    private String sendValueAndReturnAnswer(String value) throws IOException {
        out.println(value);
        return in.readLine();
    }

    public void play(){
        Scanner scanner = new Scanner(System.in);

        try {

            while (true) {
                String serverOutput;
                String playerInput;
                System.out.println(in.readLine());

                System.out.println("Guess a number:");
                playerInput = scanner.nextLine();
                serverOutput = sendValueAndReturnAnswer(playerInput);
                System.out.println(serverOutput);
                if (!serverOutput.equals("higher") && !serverOutput.equals("lower")) {
                    break;
                }
            }
        }catch (IOException e){
            logger.severe("IOException occurred");
        }
    }

    public static void main(String[] args){
        Player player = new Player();
        player.getAdress();
        player.connect();
        player.play();
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
        player.stopConnection();
    }

}
