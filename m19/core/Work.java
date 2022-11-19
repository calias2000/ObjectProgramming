package m19.core;

import java.io.Serializable;

public abstract class Work implements Serializable{

  private int _id;
  private int _price;
  private int _numberOfCopies;
  private int _currentCopies;
  private String _title;
  private Category _categoria;
  private String _tipo;
  private static final long serialVersionUID = 201901101348L;

  public Work(String titulo, int numberOfCopies, Category categoria, int preco, String type){
    _title = titulo;
    _numberOfCopies = numberOfCopies;
    _currentCopies = numberOfCopies;
    _categoria = categoria;
    _price = preco;
    _tipo = type;
  }


  /**
   * Abstract functions to print and get the creator of any type of work.
   */

  public abstract String getPrintType();
  public abstract String printWork();
  public abstract String getCreator();


  /**
   * Getters and setters for the class Work.
   */

  public int getCurrentCopies(){
    return _currentCopies;
  }

  public void removeCopy(){
    _currentCopies -= 1;
  }

  public void addCopy(){
    _currentCopies += 1;
  }

  public void setId(int id){
    _id = id;
  }

  public int getId(){
    return _id;
  }

  public Category getCategory(){
    return _categoria;
  }

  public String getWorkCategory(){
    return _categoria.getCategory();
  }

  public String getTitulo(){
    return _title;
  }

  public int getNumberOfCopies(){
    return _numberOfCopies;
  }

  public int getPreco(){
    return _price;
  }

  public String getType(){
    return _tipo;
  }
}
