package m19.core;

import java.io.Serializable;
import java.io.IOException;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.app.exception.UserIsActiveException;
import m19.app.exception.WorkNotBorrowedByUserException;
import m19.app.exception.RuleFailedException;
/*import java.util.*;*/
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import m19.core.Parser;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;




/*     Bernardo Quinteiro 93692                    Diogo Lopes 93700     */



/**
 * Class that represents the library as a whole.
 */

public class Library implements Serializable{

  private Date date = new Date();
  private Map<Integer, User> _mapUsers = new HashMap<Integer, User>();
  private Map<Integer, Work> _mapWorks = new HashMap<Integer, Work>();
  private ArrayList<Rule> _listRules = new ArrayList<Rule>();
  private OnHoldRequest _onHold = new OnHoldRequest();
  private Integer _nextWorkId = 0;
  private Integer _nextUserId = 0;
  private String estado;
  private Rule _ruleTwoRequests = new RuleTwoRequests(1);
  private Rule _ruleSuspended = new RuleSuspended(2);
  private Rule _ruleNoneAvailable = new RuleNoneAvailable(3);
  private Rule _ruleTooManyRequests = new RuleTooManyRequests(4);
  private Rule _ruleReference = new RuleReference(5);
  private Rule _ruleMaxPrice = new RuleMaxPrice(6);


  /** Serial number for serialization. */

  private static final long serialVersionUID = 201901101348L;

  public Library(){
    _listRules.add(_ruleTwoRequests);
    _listRules.add(_ruleSuspended);
    _listRules.add(_ruleNoneAvailable);
    _listRules.add(_ruleTooManyRequests);
    _listRules.add(_ruleReference);
    _listRules.add(_ruleMaxPrice);
  }



  /**
   * Receives an id and returns the user with that id
   *   or throws an exception in case the user with that id does not exist.
   *
   * @param id
   *          input id to return user or throw exception
   * @throws NoSuchUserException
   */

  protected User existUser(int id) throws NoSuchUserException{
    if (_mapUsers.get(id) == null){
      throw new NoSuchUserException(id);
    }
    return _mapUsers.get(id);
  }



  /**
   * Receives an id and returns the work with that id
   *   or throws an exception in case the work with that id does not exist.
   *
   * @param id
   *          input id to return work or throw exception
   * @throws NoSuchWorkException
   */

  protected Work existWork(int id) throws NoSuchWorkException{
    if (_mapWorks.get(id) == null){
      throw new NoSuchWorkException(id);
    }
    return _mapWorks.get(id);
  }



  /**
   * Receives a user, sets it's id and adds to the map _mapUsers.
   *
   * @param user
   *          new user that will be added to _mapUsers
   */

  protected int addUser(User user){
    user.setId(_nextUserId);
    _mapUsers.put(_nextUserId, user);
    _nextUserId++;
    return user.getId();
  }



  /**
   * Returns a string with all the users in the map _mapUsers, sorted by
   * alphabetical order. This string will
   * be printed on the terminal using display().
   *
   */

  protected String showUsers(){
    List<User> list = new ArrayList<User>(_mapUsers.values());
    String stringUsers = "";
    Comparator<User> byName = Comparator.comparing(User::getNameLower);
    Collections.sort(list, byName);
    for (User user : list){
      stringUsers += user.printUser() + "\n";
    }
    return stringUsers;
  }



  /**
   * Receives an id and checks if that id exists in the map _mapUsers.
   * In case it doesn't exist, NoSuchUserException is thrown. On the other hand,
   * a string with all the info of the user found is returned and will be
   * printed on the screen using display().
   *
   * @param id
   *          input id to get the specific user
   * @throws NoSuchUserException
   */

  protected String showUser(int id) throws NoSuchUserException{
    User user = existUser(id);
    return user.printUser();
  }


  /**
   * Receives an id and checks if that id exists in the map _mapUsers.
   * In case it doesn't exist, NoSuchUserException is thrown. On the other hand,
   * the user notifications will be displayed on the screen.
   *
   * @param id
   *          input id to get notifications of user
   * @throws NoSuchUserException
   */

  protected String notifsGet(int id) throws NoSuchUserException{
    return existUser(id).getNotifs();
  }


  /**
   * Receives an id and checks if user exists. In case it does not the exception
   * NoSuchUserException is thrown. Then it checks if the user is active/suspended.
   * In case it is supsended a fine will be payed, on the other hand the exception
   * UserIsActiveException is thrown. Then it checks if the user has requests on delay.
   * If it doesn't have then he is again ACTIVE.
   *
   * @param id
   *          input id to pay fine or activate user
   * @throws NoSuchUserException
   * @throws UserIsActiveException
   */

  protected void payFine(int id) throws UserIsActiveException, NoSuchUserException{
    User user = existUser(id);
    if (!user.getIsActive()){
      user.doPayFine();
    }
    else{
      throw new UserIsActiveException(id);
    }
    if(!user.hasDelays(date.getDate())){
      user.activate();
    }
  }



  /**
   * Returns a string with all the works in the map _mapWorks. This string will
   * be printed on the terminal using display().
   *
   */

  protected String showWorks(){
    String worksList = "";
    for(int i = 0; i<_mapWorks.size(); i++){
      worksList += _mapWorks.get(i).printWork() + "\n";
    }
    return worksList;
  }



  /**
   * Receives an id and checks if that id exists in the map _mapWorks.
   * In case it doesn't exist, NoSuchWorkException is thrown. On the other hand,
   * a string with all the info of the work found is returned and will be
   * printed on the screen using display().
   *
   * @param id
   *          input id to get the specific work
   * @throws NoSuchWorkException
   */

  protected String showWork(int id) throws NoSuchWorkException{
    return existWork(id).printWork();
  }


  /**
   * Receives a user and a work and returns the deadline of the work based on
   * the user behaviour.
   *
   * @param user
   * @param work
   *          input user and work that will be used to return work deadline
   */

  protected int getRequestDeadline(User user, Work work){
    int workC = work.getNumberOfCopies();
    if (user.getBehaviour().equals(Behaviour.NORMAL)){
      if(workC > 5){
        return 15;
      }
      else if(workC > 1){
        return 8;
      }
      else{
        return 3;
      }
    }
    else if (user.getBehaviour().equals(Behaviour.CUMPRIDOR)){
      if(workC > 5){
        return 30;
      }
      else if(workC > 1){
        return 15;
      }
      else{
        return 8;
      }
    }
    else{
      return 2;

      }
  }


  /**
   * Receives a userId and a workId and goes through all the rules to check
   * if the user can request the work and if possible requests it adding it to
   * the user Request list. In case one of the rules fails an exception is thrown.
   * If the rule 3 is failed then the user is asked if wants to receive notification
   * when the work is available or not.
   *
   * @param userId
   * @param workId
   *          input userId and workId that will be used to make the request
   * @throws NoSuchUserException
   * @throws NoSuchWorkException
   * @throws RuleFailedException
   */

  protected int requestWork(int userId, int workId) throws NoSuchWorkException, NoSuchUserException, RuleFailedException{
    User user = existUser(userId);
    Work work = existWork(workId);
    int i = 1;
    for(Rule rule: _listRules){
      if(!rule.checkRule(work, user) && i!=3){
        throw new RuleFailedException(userId, workId, i);
      }
      else if(!rule.checkRule(work, user) && i==3){
        return i;
      }
      i++;
    }
    Request request = new Request(work, getRequestDeadline(user, work) + date.getDate());
    user.addRequest(request);
    work.removeCopy();
    return 0;
  }



  /**
   * Receives a userId and a workId and creates a Request. Then adds this
   * to the _listOnHold where are all the requests that were not able to be completed
   * and the users wanted to receive notification when available.
   *
   * @param userId
   * @param workId
   *          input userId and workId that will be added to _listOnHold
   */

  protected void putOnHoldRequest(int userId, int workId){
    User user = _mapUsers.get(userId);
    Work work = _mapWorks.get(workId);
    Request request = new Request(user, work);
    _onHold.addOnHoldRequest(request);
  }



  /**
   * Receives a userId and a workId and removes the user request returning the book to the library
   *
   * @param userId
   * @param workId
   *          User with id userId that returns the work with id workId
   * @throws WorkNotBorrowedByUserException
   * @throws NoSuchUserException
   * @throws NoSuchWorkException
   */

  protected int returnWork(int userId, int workId) throws WorkNotBorrowedByUserException, NoSuchWorkException, NoSuchUserException{
    Work work = existWork(workId);
    User user = existUser(userId);
    int a, deadline;
    deadline = user.getRequestWorkDeadline(work);
    a = user.removeRequest(work);
    if (a == 0){
      work.addCopy();
      _onHold.checkRequestHold(work);
      if (deadline <= date.getDate()){
        user.addStreak(0);
        user.verifyStreak();
        user.addFine(5 * (date.getDate()- deadline));
        return user.getFine();
      }
      else{
        user.addStreak(1);
        user.verifyStreak();
        return 0;
      }
    }
    throw new WorkNotBorrowedByUserException(workId, userId);
  }



  /**
   * Receives a dvd, sets it's id and adds to the map _mapWorks.
   *
   * @param dvd
   *          new dvd that will be added to _mapWorks
   */

  protected void addDvd(Dvd dvd){
    dvd.setId(_nextWorkId);
    _mapWorks.put(_nextWorkId, dvd);
    _nextWorkId++;
  }



  /**
   * Receives a book, sets it's id and adds to the map _mapWorks.
   *
   * @param book
   *          new book that will be added to _mapWorks
   */

  protected void addBook(Book book){
    book.setId(_nextWorkId);
    _mapWorks.put(_nextWorkId, book);
    _nextWorkId++;
  }



  /**
   * Receives a String and the works that have that String on any caracteristic.
   *
   * @param starter
   *          String that will be used on search
   */

  protected String searchString(String starter){
    String worksList = "";
    String lower = starter.toUpperCase();
    int i = 0;
    while(i<_mapWorks.size()){
      String lowerTitle = _mapWorks.get(i).getTitulo().toUpperCase();
      String lowerCreator = _mapWorks.get(i).getCreator().toUpperCase();
      if (lowerTitle.contains(lower) || lowerCreator.contains(lower)){
        worksList += _mapWorks.get(i).printWork() + "\n";
      }
      i++;
    }
    return worksList;
  }



  /**
   * Checks users requests and suspends users that have works on delay.
   */

  protected void dailyCheck(){
    List<User> list = new ArrayList<User>(_mapUsers.values());
    for(User user: list){
      if(user.hasDelays(getDate())){
        user.suspend();
      }
    }
  }



  /**
   * Increments the current date an inputed number of days and runs the dailyCheck
   * function to check delays on user requests.
   *
   * @param days
   *          number of days to increment
   */

  protected void addDays(int days){
    date.advanceDate(days);
    dailyCheck();
  }



  /**
   * Returns the current date that will be printed on the screen using display().
   *
   */

  protected int getDate(){
    return date.getDate();
  }



  /**
   * Read the text input file at the beginning of the program and populates the
   * instances of the various possible types (books, DVDs, users).
   *
   * @param filename
   *          name of the file to load
   * @throws BadEntrySpecificationException
   * @throws IOException
   */
  void importFile(String filename) throws BadEntrySpecificationException, IOException {
    Parser p = new Parser(this);
    p.parseFile(filename);
  }

}
