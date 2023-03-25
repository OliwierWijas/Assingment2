package assignment2.view;

import assignment2.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class MessageViewLoader extends ViewLoader
{
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;

  public MessageViewLoader(String fxmlFile, ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    super(fxmlFile);
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
  }

  @Override protected ViewController createViewController(FXMLLoader loader, Region root)
  {
    MessageViewController messageViewController = loader.getController();
    messageViewController.init(viewHandler, viewModelFactory.getMessageViewModel(), root);
    return messageViewController;
  }
}
