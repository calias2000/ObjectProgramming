package m19.core;

import java.io.Serializable;

public class Date implements Serializable{

  private static final long serialVersionUID = 201901101348L;
  private int _date;


public Date(){
  _date = 0;
}

  /**
  * Advances the date a certain number of days.
  */

  protected void advanceDate(int days){
    _date += days;
  }


  /**
  * Returns the date.
  */

  protected int getDate(){
    return _date;
  }

}
