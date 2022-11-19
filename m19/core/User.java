package m19.core;

import java.io.Serializable;
import java.util.ArrayList;
import m19.app.exception.NoSuchUserException;

public class User implements Serializable{

  private String _name;
  private String _email;
  private int _id;
  private Library _library;
  private Behaviour _behaviour;
  private boolean _isActive;
  private int _multa;
  private ArrayList<Notification> _listNotifs = new ArrayList<Notification>();
  private ArrayList<Request> _listRequests = new ArrayList<Request>();
  private ArrayList<Integer> _listStreak = new ArrayList<Integer>();
  private static final long serialVersionUID = 201901101348L;


  public User(String name, String email){
    _name = name;
    _email = email;
    _behaviour = Behaviour.NORMAL;
    _isActive = true;
  }


  /**
   * Getters and setters for the class user.
   */

  public void setId(int i){
    _id = i;
  }

  public int getId(){
    return _id;
  }

  public boolean getIsActive(){
    return _isActive;
  }


  public String getUserBehaviour(){
    return _behaviour.getBehaviour();
  }


  public Behaviour getBehaviour(){
    return _behaviour;
  }

  public String getName(){
    return _name;
  }

  public String getEmail(){
    return _email;
  }

  public void doPayFine(){
    _multa = 0;
  }

  public void addFine(int fine){
    _multa += fine;
  }

  public int getFine(){
    return _multa;
  }


  public String printUser(){
    String estado = "";
    if (_isActive){
      estado = "ACTIVO";
    }
    else{
      estado = "SUSPENSO - EUR " + _multa;
    }
    return _id + " - " + _name + " - " + _email + " - " + _behaviour.getBehaviour() + " - " + estado;
  }

  public String getNameLower(){
    return _name.toLowerCase();
  }



  /**
  * funtions that store the last work returns of the user and then check
  *   according to these the behaviour of the user
  */

  public void addStreak(int value){
    if(_listStreak.size() >= 5){
      _listStreak.remove(0);
    }
    _listStreak.add(value);

  }

  public void verifyStreak(){
    int countGood = 0;
    for(int pos: _listStreak){
      if(pos == 1){
        countGood++;
      }
    }
    if(countGood == 5){
      _behaviour= Behaviour.CUMPRIDOR;
    }
    else if(_listStreak.size() >= 3){
      int a = _listStreak.get(_listStreak.size()-1) + _listStreak.get(_listStreak.size()-2) + _listStreak.get(_listStreak.size()-3);
      if(a == 0){
        _behaviour = Behaviour.FALTOSO;
      }
      else if(a == 3){
        _behaviour = Behaviour.NORMAL;
      }
    }
  }



  /**
  * functions that deal with the user notifications
  */

  public void addNotif(String text){
    Notification notif = new Notification(text);
    _listNotifs.add(notif);
   }

  public String getNotifs() throws NoSuchUserException{
    String notifString = "";
    for(Notification notif: _listNotifs){
      notifString += notif.printNotif() + "\n";
    }
    _listNotifs.clear();
    return notifString;
  }



  /**
  * functions that deal with the requests of the user
  */

  public void addRequest(Request request){
    _listRequests.add(request);
  }

  public int removeRequest(Work work){
    int a = 1;
    for(Request request: _listRequests){
      if (request.getRequestWork() == work){
        _listRequests.remove(request);
        a = 0;
        return a;
      }
    }
    return a;
  }

  public boolean findRequest(Work work){
    for(Request request: _listRequests){
      if (request.getRequestWork() == work){
        return true;
      }
    }
    return false;
  }

  public int getNumberOfRequests(){
    return _listRequests.size();
  }

  public ArrayList<Request> getListRequests(){
    return _listRequests;
  }

  public int getRequestWorkDeadline(Work work){
    for(Request request: _listRequests){
      if (request.getRequestWork() == work){
        return request.getDeadline();
      }
    }
    return -1;
  }

  public boolean hasDelays(int date){
    for(Request request: _listRequests){
      if(date > request.getDeadline()){
        return true;
      }
    }
    return false;
  }



  /**
  * functions that suspend or activate the user
  */

  public void suspend(){
    _isActive = false;
  }

  public void activate(){
    _isActive = true;
  }
}
