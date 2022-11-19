package m19.core;

import java.io.Serializable;

public class RuleTwoRequests extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleTwoRequests(int id){
    super(id);
  }

  /**
  * checks if the work was already requested by the user
  */

  public boolean checkRule(Work work, User user){
    return !user.findRequest(work);
  }
}
