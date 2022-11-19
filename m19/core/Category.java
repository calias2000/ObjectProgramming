package m19.core;

import java.io.Serializable;

public enum Category implements Serializable{

  REFERENCE("REFERENCE"),
  FICTION("FICTION"),
  SCITECH("SCITECH");

  private static final long serialVersionUID = 201901101348L;
  private String _category;

  Category(String category){
    _category = category;
  }

  public String getPrintCategory(){
    if ("REFERENCE".equals(_category)){
      return "Referência";
    }
    else if("FICTION".equals(_category)){
      return "Ficção";
    }
    else{
      return "Técnica e Científica";
    }
  }

  public String getCategory(){
    return _category;
  }
}
