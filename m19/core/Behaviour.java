package m19.core;

import java.io.Serializable;

public enum Behaviour implements Serializable{

  NORMAL("NORMAL"),
  CUMPRIDOR("CUMPRIDOR"),
  FALTOSO("FALTOSO");

  private static final long serialVersionUID = 201901101348L;
  private String _behaviour;

  Behaviour(String behaviour){
    _behaviour = behaviour;
  }

  public String getBehaviour(){
    return _behaviour;
  }
}
