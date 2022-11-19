package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import java.io.IOException;
import pt.tecnico.po.ui.DialogException;
import java.io.FileNotFoundException;
import m19.app.exception.FileOpenFailedException;
/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<LibraryManager> {

  private Input<String> _file;

  /**
   * @param receiver
   */
  public DoOpen(LibraryManager receiver) {
    super(Label.OPEN, receiver);
    _file = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.load(_file.value());
    } catch (FileNotFoundException fnfe) {
      throw new FileOpenFailedException(_file.value());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

}
