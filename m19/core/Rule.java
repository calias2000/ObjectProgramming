package m19.core;

import java.io.Serializable;

public abstract class Rule implements Serializable{
  private int _id;
  private static final long serialVersionUID = 201901101348L;

  public Rule(int id){
    _id = id;
  }

  public int getRuleId(){
    return _id;
  }

  public abstract boolean checkRule(Work work, User user);
}
