/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abc
 */
import java.rmi.*;
import java.rmi.RMISecurityManager;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class ChatServer extends UnicastRemoteObject implements IChatServer {

    ArrayList list;
    public ChatServer() throws RemoteException {
        super();
        list = new ArrayList();
    }
    
    public void addClient(IChatClient client,String msg) throws RemoteException {
        if(!list.contains(client)) {
            for(int i=0;i < list.size();i++) {
                sendMessage((IChatClient)list.get(i),msg);
            }
            list.add(client);
        }
    }
    
    public void removeClient(IChatClient client,String msg) throws RemoteException {
        if(!list.contains(client)) {
            return;
        }
        else {
            for(int i=0;i < list.size();i++) {
                sendMessage((IChatClient)list.get(i),msg);
            }
            list.remove(client);
        }
    }
    
    public void sendMessage(IChatClient client,String msg) throws RemoteException {
        if(!list.contains(client)) {
            return;
        }
        else {
            for(int i=0;i < list.size();i++) {
                String user = client.getName();
                if(user == null || user == "") {
                    user = "Anonymous";
                }
                ((IChatClient)list.get(i)).sendMessage(user+": "+msg);
            }
        }
    }

    public static void main(String[] args) {
        try {
            IChatServer server = new ChatServer();
            LocateRegistry.createRegistry(6600);
            Naming.rebind("rmi://127.0.0.1:6600/IChatServer", server);
            System.out.println("Server Start !");
        }
        
        catch(Exception e) {
            System.out.println("IChatServer: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
