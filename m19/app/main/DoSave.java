package m19.app.main;

import m19.core.LibraryManager;
import java.io.IOException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import m19.core.exception.MissingFileAssociationException;


/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {

  private Input<String> _file;

  /**
   * @param receiver
   */
  public DoSave(LibraryManager receiver) {
    super(Label.SAVE, receiver);
    _file = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute(){
    try{
      _receiver.save();
    }catch(IOException | MissingFileAssociationException e){
      _form.parse();
      try{
        _receiver.saveAs(_file.value());
      }catch(IOException | MissingFileAssociationException m){
        m.printStackTrace();
      }
    }
  }
}
