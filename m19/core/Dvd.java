package m19.core;

import java.io.Serializable;

public class Dvd extends Work implements Serializable{

  private String _director;
  private String _igac;
  private static final long serialVersionUID = 201901101348L;


  public Dvd(String titulo, String director, int preco, Category categoria, String igac, int numberOfCopies){
    super(titulo, numberOfCopies, categoria,  preco, "DVD");
    _director = director;
    _igac = igac;
  }


  /**
  * Returns a string with all the information of a dvd.
  */

  public String printWork(){
    return getId() + " - " + getCurrentCopies() + " de  " + getNumberOfCopies() + " - " + getPrintType() + " - " + getTitulo() + " - " + getPreco() + " - " + getCategory().getPrintCategory() + " - " + _director + " - " + _igac;
  }


  /**
  * Getters and setters for the class Dvd.
  */

  public String getCreator(){
    return _director;
  }

  public String getDirector(){
    return _director;
  }

  public String getIgac(){
    return _igac;
  }

  public String  getPrintType(){
    return "DVD";
  }
}
