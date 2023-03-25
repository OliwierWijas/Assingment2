package assignment2.view;

import assignment2.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class LoginViewLoader extends ViewLoader
{
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;

  public LoginViewLoader(String fxmlFile, ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    super(fxmlFile);
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
  }

  @Override protected ViewController createViewController(FXMLLoader loader, Region root)
  {
    LoginViewController loginViewController = loader.getController();
    loginViewController.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
    return loginViewController;
  }
}
