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

public interface IChatClient extends Remote{
    public String getName() throws RemoteException;
    public void sendMessage(String msg) throws RemoteException;
}
