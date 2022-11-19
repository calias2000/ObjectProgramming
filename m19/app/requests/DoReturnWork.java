package m19.app.requests;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import m19.app.exception.WorkNotBorrowedByUserException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
// FIXME import other core concepts
// FIXME import other ui concepts

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {


    private Input<Integer> _userId;
    private Input<Integer> _workId;
    private Input<String> _choice;

  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException{
    Form _form1 = new Form();
    _form.parse();
    try{
      int multa = _receiver.workReturn(_userId.value(), _workId.value());
      if(multa > 0){
        _display.addLine(Message.showFine(_userId.value(), multa));
        _display.display();
        _choice = _form1.addStringInput(Message.requestFinePaymentChoice());
        _form1.parse();
        if (_choice.value().equals("s")){
          _receiver.finePay(_userId.value());
        }
      }
    }catch(WorkNotBorrowedByUserException e){
      throw new WorkNotBorrowedByUserException(_workId.value(), _userId.value());
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(_userId.value());
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(_workId.value());
    }
  }

}
