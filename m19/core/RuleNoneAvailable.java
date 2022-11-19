package m19.core;

import java.io.Serializable;

public class RuleNoneAvailable extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleNoneAvailable(int id){
    super(id);
  }

  /**
  * checks if the work has no more copies available
  */

  public boolean checkRule(Work work, User user){
    return work.getCurrentCopies() > 0;
  }
}
