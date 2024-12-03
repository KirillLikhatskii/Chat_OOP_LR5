import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        Socket socket = null;
        PrintStream ps;
        BufferedReader dis;
        Boolean isChatting = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите свой никнейм для переписки: \r\n");
        String nickname = scanner.nextLine();
        try {
            socket = new Socket( "DESKTOP-SUR2M6O" , 8030);
            ps = new PrintStream(socket.getOutputStream());
            ps.println("/CMD "+nickname);
            ps.flush();
            dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg1 = dis.readLine();
            System.out.println(msg1);
            while (isChatting)
            {
                socket = new Socket( "DESKTOP-SUR2M6O" , 8030);
                ps = new PrintStream(socket.getOutputStream());
                dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mymsg = scanner.nextLine();
                ps.println(nickname +"> "+mymsg+"\r\n");
                ps.flush();
                String msg = dis.readLine();
                System.out.println(msg);
                if (mymsg.contains("/exit"))
                {
                    isChatting = false;
                    String emsg = dis.readLine();
                    System.out.println(emsg);
                }
            }
        }catch (IOException e){
            System.out.println("Ошибка : " + e);
        }

    }
}