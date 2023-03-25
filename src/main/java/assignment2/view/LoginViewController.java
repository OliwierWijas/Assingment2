package assignment2.view;

import assignment2.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewController implements ViewController,
    PropertyChangeListener
{
  @FXML private TextField nickname;
  @FXML private TextField password;
  @FXML private Label error;

  private ViewHandler viewHandler;
  private LoginViewModel viewModel;
  private Region root;

  public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;

    viewModel.addPropertyChangeListener(this);

    this.viewModel.bindNickname(nickname.textProperty());
    this.viewModel.bindPassword(password.textProperty());
    this.viewModel.bindError(error.textProperty());
  }

  @FXML protected void loginButtonPressed()
  {
    viewModel.login();
  }

  @FXML protected void closeButtonPressed()
  {
    viewModel.disconnect();
    viewHandler.closeView();
  }

  @Override public void reset()
  {
    viewModel.reset();
  }

  @Override public Region getRoot()
  {
    return root;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("PersonLoggedIn"))
        viewHandler.openView(ViewFactory.MESSAGE);
    });
  }
}
