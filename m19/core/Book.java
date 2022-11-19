package m19.core;

import java.io.Serializable;

public class Book extends Work implements Serializable{

  private String _author;
  private String _isbn;
  private static final long serialVersionUID = 201901101348L;


  public Book(String titulo, String author, int preco, Category categoria, String isbn, int numberOfCopies){
    super(titulo, numberOfCopies, categoria,  preco, "BOOK");
    _author = author;
    _isbn = isbn;

  }


  /**
  * Returns a String with all the information of a book.
  */

  public String printWork(){
    return getId() + " - " + getCurrentCopies() + " de  " + getNumberOfCopies() + " - " + getPrintType() + " - " + getTitulo() + " - " + getPreco() + " - " + getCategory().getPrintCategory() + " - " + _author + " - " + _isbn;
  }


  /**
  * Getters and setters of the class Book.
  */

  public String getCreator(){
    return _author;
  }

  public String getAuthor(){
    return _author;
  }

  public String getIsbn(){
    return _isbn;
  }

  public String  getPrintType(){
    return "Livro";
  }
}
