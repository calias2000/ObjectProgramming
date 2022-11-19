package m19.core;


import java.io.Serializable;

public class Request implements Serializable{

  private static final long serialVersionUID = 201901101348L;
  private Work _work;
  private User _user;
  private int _deadline;

  public Request(Work work, int deadline){
    _work = work;
    _deadline = deadline;
  }

  public Request(User user, Work work){
    _user = user;
    _work = work;
  }

  public User getRequestUser(){
    return _user;
  }

  public Work getRequestWork(){
    return _work;
  }

  public int getDeadline(){
    return _deadline;
  }
}
