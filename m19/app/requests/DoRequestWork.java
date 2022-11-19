package m19.app.requests;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import m19.app.exception.RuleFailedException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

  private Input<Integer> _userId;
  private Input<Integer> _workId;
  private Input<String> _choice;

  /**
   * @param receiver
   */
  public DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    Form _form1 = new Form();
    _form.parse();
    try{
      int a = _receiver.workRequest(_userId.value(), _workId.value());
      if (a == 3){
        _choice = _form1.addStringInput(Message.requestReturnNotificationPreference());
        _form1.parse();
        if (_choice.value().equals("s")){
          _receiver.putOnHold(_userId.value(), _workId.value());
        }
      }
      else if(a == 0){
        _display.addLine(Message.workReturnDay(_workId.value(), _receiver.getMessageDeadline(_userId.value(), _workId.value())));
        _display.display();
      }
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(_workId.value());
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(_userId.value());
    }

  }

}
