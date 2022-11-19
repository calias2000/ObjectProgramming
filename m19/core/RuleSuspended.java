package m19.core;

import java.io.Serializable;

public class RuleSuspended extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleSuspended(int id){
    super(id);
  }

  /**
  * checks if the user is suspended or not
  */

  public boolean checkRule(Work work, User user){
    return user.getIsActive();
  }
}
