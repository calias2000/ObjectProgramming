package m19.core;

import java.io.Serializable;

import java.util.Iterator;
import java.util.ArrayList;


  /**
  * class related to the works that have no more copies and users
  *   want to be notified when one is available
  */

public class OnHoldRequest implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  private ArrayList<Request> _listOnHold = new ArrayList<Request>();

  public void addOnHoldRequest(Request request){
    _listOnHold.add(request);
  }

  public void checkRequestHold(Work work){
    Iterator<Request> iter = _listOnHold.iterator();
    while(iter.hasNext()){
      Request request = iter.next();
      if(request.getRequestWork() == work){
        request.getRequestUser().addNotif("ENTREGA: " + work.printWork());
        iter.remove();
      }
    }
  }
}
