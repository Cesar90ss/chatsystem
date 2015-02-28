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

public interface IChatServer extends Remote {
    public void addClient(IChatClient client,String msg) throws RemoteException;
    public void removeClient(IChatClient client,String msg) throws RemoteException;
    public void sendMessage(IChatClient client,String msg) throws RemoteException;
}