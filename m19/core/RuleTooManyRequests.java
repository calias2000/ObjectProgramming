package m19.core;

import java.io.Serializable;

public class RuleTooManyRequests extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleTooManyRequests(int id){
    super(id);
  }

  /**
  * according to the user behaviour checks how many requests this can have
  */

  public boolean checkRule(Work work, User user){
    if(user.getBehaviour().equals(Behaviour.CUMPRIDOR)){
      return user.getNumberOfRequests() < 5;
    }
    else if(user.getBehaviour().equals(Behaviour.NORMAL)){
      return user.getNumberOfRequests() < 3;
    }
    else{
      return user.getNumberOfRequests() < 1;
    }
  }
}
