package m19.core;

import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.ImportFileException;
import m19.app.exception.UserRegistrationFailedException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
import m19.app.exception.UserIsActiveException;
import m19.app.exception.WorkNotBorrowedByUserException;
import m19.app.exception.RuleFailedException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.Serializable;

/**
 * The façade class.
 */

public class LibraryManager implements Serializable {

  private static final long serialVersionUID = 201901101348L;

  private Library _library = new Library();
  private String _file;



  /**
   * Functions related to users.
   */

  public int registerUser(String name, String email) throws UserRegistrationFailedException{
    int a;
    User user = new User(name, email);
    a = _library.addUser(user);
    return a;
  }

  public String getUsers(){
    return _library.showUsers();
  }

  public String getUser(int id) throws NoSuchUserException{
    try{
      return _library.showUser(id);
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(id);
    }
  }



  /*
   * Functions related to works.
   */

  public String getWorks(){
    return _library.showWorks();
  }

  public String getWork(int id) throws NoSuchWorkException{
    try{
      return _library.showWork(id);
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(id);
    }
  }

  public String getWorkString(String starter){
    return _library.searchString(starter);
  }

  public int workRequest(int userId, int workId) throws NoSuchWorkException, NoSuchUserException, RuleFailedException{
    try{
      return _library.requestWork(userId, workId);
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(workId);
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(userId);
    }
  }

  public int workReturn(int userId, int workId) throws WorkNotBorrowedByUserException, NoSuchWorkException, NoSuchUserException{
    try{
      return _library.returnWork(userId, workId);
    }catch(WorkNotBorrowedByUserException e){
      throw new WorkNotBorrowedByUserException(workId, userId);
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(userId);
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(workId);
    }
  }



  public void putOnHold(int userId, int workId){
    _library.putOnHoldRequest(userId, workId);
  }

  public String notifGet(int id) throws NoSuchUserException{
    try{
      return _library.notifsGet(id);
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(id);
    }
  }

  public int getMessageDeadline(int userId, int workId) throws NoSuchUserException, NoSuchWorkException{
    return _library.getRequestDeadline(_library.existUser(userId), _library.existWork(workId)) + _library.getDate();
  }

  public void finePay(int id) throws UserIsActiveException, NoSuchUserException{
    try{
      _library.payFine(id);
    }catch(UserIsActiveException e){
      throw new UserIsActiveException(id);
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(id);
    }
  }



  /**
   * Functions to show and advance the date.
   */

  public int showDate(){
    return _library.getDate();
  }

  public void advance(int days){
    if (days < 0){
      days = 0;
    }
    _library.addDays(days);
  }



  /**
   * Functions to save and load files.
   */

  public void save() throws MissingFileAssociationException, IOException {
     if (_file == null){
       throw new MissingFileAssociationException();
     }
     try(
       FileOutputStream fileoutput = new FileOutputStream(_file);
       ObjectOutputStream object = new ObjectOutputStream(fileoutput)){
         object.writeObject(_library);
     }
   }

  public void saveAs(String file) throws MissingFileAssociationException, IOException{
     _file = file;
     save();
   }

  public void load(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
     _file = file;
     try(
       FileInputStream fileinput = new FileInputStream(file);
       ObjectInputStream objectinput = new ObjectInputStream(new BufferedInputStream(fileinput))){
       fileinput.close();
       _library = (Library) objectinput.readObject();
       objectinput.close();
   }
   }

/**
 * Set the state of this application from a textual representation stored into a file.
 *
 * @param file the filename of the file with the textual represntation of the state of this application.
 * @throws ImportFileException if it happens some error during the parsing of the textual representation.
 */
  public void importFile(String file) throws ImportFileException {
    try {
      _library.importFile(file);
    } catch (IOException | BadEntrySpecificationException e) {
      throw new ImportFileException(e);
    }
  }
}
