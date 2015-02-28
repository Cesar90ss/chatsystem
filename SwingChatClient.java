/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abc
 */
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import javax.swing.*;

public class SwingChatClient extends UnicastRemoteObject implements IChatClient,ActionListener {
    IChatServer server = null;
    boolean connected = false;
    String title = "ChatClient";
    JFrame f;
    String username = "";
    JTextArea area;
    JTextField inName;
    JButton connect;
    JButton disconnect;
    JButton send;
    JTextField contentIn;
    
    public SwingChatClient() throws RemoteException {
        super();
    }
    
    public void init() {
        f = new JFrame(title);
        f.setBounds(250,250,500,500);
        f.setVisible(true);
        JPanel pNorth,pSouth,pText;
        pNorth = new JPanel();
        pSouth = new JPanel();
        pText = new JPanel();
        inName = new JTextField(6);
        contentIn = new JTextField(22);
        area = new JTextArea(15,35);
        connect = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        send = new JButton("Send");
        pNorth.add(new JLabel("Enter your name: "));
        pNorth.add(inName);
        pNorth.add(connect);
        pNorth.add(disconnect);
        pSouth.add(new JLabel("Enter Message: "));
        pSouth.add(contentIn);
        pSouth.add(send);
        pText.add(new JScrollPane(area));
        connect.addActionListener(this);
        disconnect.addActionListener(this);
        send.addActionListener(this);
        contentIn.addActionListener(this);
        f.add(pNorth,BorderLayout.NORTH);
        f.add(pSouth,BorderLayout.SOUTH);
        f.add(pText,BorderLayout.CENTER);
        f.validate();
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    
    public static void main(String args[])throws RemoteException {
        try {
            SwingChatClient client = new SwingChatClient();
            client.init();
        }
        catch(Exception e) {
            System.err.println("swingchatclient" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void connect() {
        try {
            server = (IChatServer)Naming.lookup("rmi://127.0.0.1:6600/IChatServer");
            System.out.println("Client Start!");
            connected = true;
            setStatus();
            username = inName.getText().trim();
            server.addClient(this,username+" Entered!");
        }
        catch(Exception e) {
            area.append("Connection fault!");
            connected=false;
            setStatus();
            server=null;
            System.out.println(e);
        }
    }
    
    public void disconnect() {
        try {
            if(server == null)
                return;
            server.removeClient(this,username+" Has Quited!");
            server = null;
        }
        catch(Exception e) {
            area.append("Connection Error!"+"/n");
            int length = area.getText().length();
            area.setCaretPosition(length);
        }
        finally {
            connected = false;
            setStatus();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connect) {
            connect();
        }
        else if (e.getSource() == disconnect) {
            disconnect();
        }
        else if(e.getSource() == contentIn) {
            if (server == null)
                return;
            try {
                server.sendMessage(this, contentIn.getText());
                contentIn.setText("");
            }
            catch(Exception ee) {
                area.append("Connection Error!"+"\n");
                int length = area.getText().length();
                area.setCaretPosition(length);
            }
        }
        else {
            if (server == null)
                return;
            try {
                server.sendMessage(this,contentIn.getText());
                contentIn.setText("");
            }
            catch(Exception ex) {
                area.append("Connection Error"+"\n");
                int length = area.getText().length();
            }
        }
    }
    
    public void setStatus() {
        if(connected)
            f.setTitle(title+"connected");
        else
            f.setTitle(title+"disconnected");
    }
    
    public String getName() throws RemoteException {
        return username;
    }
    
    public void sendMessage(String msg) throws RemoteException {
        area.append(msg+"\n");
        int length = area.getText().length();
        area.setCaretPosition(length);
    }
}

