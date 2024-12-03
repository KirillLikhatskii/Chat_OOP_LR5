import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8030);
        ArrayList<String> nicknames = new ArrayList<String>();
        String msg_for_all = "";
        ArrayList<String> msg_for = new ArrayList<String>(1000);
        ArrayList<Socket> s = new ArrayList<Socket>();
        while (true)
        {
            Boolean invited = false;
            try {
                if (nicknames.isEmpty()){
                    s.add(server.accept());
                }
                for (int i=0; i< nicknames.size(); i++)
                    s.set(i, server.accept());
                for (int i=0; i<s.size(); i++)
                {
                    BufferedReader dis = new BufferedReader(new InputStreamReader(s.get(i).getInputStream()));
                    String msg = dis.readLine();
                    if(msg.contains("/CMD")){
                        if (!nicknames.contains(msg.split(" ", 2)[1]))
                        {
                            nicknames.add(msg.split(" ", 2)[1]);
                            msg_for_all += "К чату присоединился: " + msg.split(" ", 2)[1] + "\r\n";
                            msg_for.add("");
                        }
                    }else if (msg.contains("/invite"))
                    {
                        s.add(server.accept());
                        invited = true;
                    }else if (msg.contains("/exit"))
                    {
                        for (int j=0;j<nicknames.size(); j++)
                        {
                            if (msg.contains(nicknames.get(j)))
                            {
                                msg_for_all += "Чат покинул: " + nicknames.get(j) + "\r\n";
                                nicknames.remove(i);
                                s.get(i).close();
                                s.remove(i);
                                break;
                            }
                        }
                    }else
                    {
                        if(!msg.isEmpty())
                        {
                            for (int j=0; j<nicknames.size(); j++)
                            {
                                if (j!=i)
                                    msg_for.set(j, msg_for.get(j)+msg);
                            }
                        }
                    }
                }
                for (int i=0; i<s.size(); i++)
                {
                    PrintStream ps = new PrintStream(s.get(i).getOutputStream());
                    if (!msg_for_all.isEmpty())
                        ps.println(msg_for_all);
                    if (!msg_for.get(i).isEmpty())
                        ps.println(msg_for.get(i));
                    ps.flush();
                    msg_for.set(i, "");
                    if (i == s.size()-1 && invited)
                        break;
                    s.get(i).close();
                }
                msg_for_all = "";
            } catch (IOException e) {
                System. out.println( " ошибка : " + e);
            }
        }
    }
}