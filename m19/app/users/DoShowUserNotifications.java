package m19.app.users;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import m19.app.exception.NoSuchUserException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
public class DoShowUserNotifications extends Command<LibraryManager> {


  private Input<Integer> _userId;

  /**
   * @param receiver
   */
  public DoShowUserNotifications(LibraryManager receiver) {
    super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      String a = _receiver.notifGet(_userId.value());
      _display.addLine(a);
      _display.display();
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(_userId.value());
    }
  }

}
