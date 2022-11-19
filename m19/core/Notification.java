package m19.core;

import m19.app.exception.NoSuchUserException;

import java.io.Serializable;

public class Notification implements Serializable{
  private static final long serialVersionUID = 201901101348L;
  private String _notification;
  
  public Notification(String notification){
    _notification = notification;
  }
  public String printNotif() throws NoSuchUserException{
    return _notification;
  }
}
