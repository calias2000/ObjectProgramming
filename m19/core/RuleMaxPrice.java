package m19.core;

import java.io.Serializable;

public class RuleMaxPrice extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleMaxPrice(int id){
    super(id);
  }

  /**
  * checks if certain user behaviour can request certain work price
  */

  public boolean checkRule(Work work, User user){
    return !((!user.getUserBehaviour().equals("CUMPRIDOR")) && work.getPreco()>25);
  }
}
