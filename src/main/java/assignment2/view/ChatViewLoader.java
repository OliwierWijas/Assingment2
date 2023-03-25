package assignment2.view;

import assignment2.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class ChatViewLoader extends ViewLoader
{
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;

  public ChatViewLoader(String fxmlFile, ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    super(fxmlFile);
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
  }

  @Override protected ViewController createViewController(FXMLLoader loader, Region root)
  {
    ChatViewController chatViewController = loader.getController();
    chatViewController.init(viewHandler, viewModelFactory.getChatViewModel(), root);
    return chatViewController;
  }
}
