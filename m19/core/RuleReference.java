package m19.core;

import java.io.Serializable;

public class RuleReference extends Rule implements Serializable{

  private static final long serialVersionUID = 201901101348L;

  public RuleReference(int id){
    super(id);
  }

  /**
  * checks if the work is from the category Reference
  */

  public boolean checkRule(Work work, User user){
    return !work.getCategory().equals("REFERENCE");
  }
}
