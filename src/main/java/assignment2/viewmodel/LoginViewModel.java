package assignment2.viewmodel;

import assignment2.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel
{
  private final Model model;
  private final StringProperty nickname;
  private final StringProperty password;
  private final StringProperty error;

  private final PropertyChangeSupport support;

  public LoginViewModel(Model model)
  {
    this.model = model;
    this.nickname = new SimpleStringProperty("");
    this.password = new SimpleStringProperty("");
    this.error = new SimpleStringProperty("");
    this.support = new PropertyChangeSupport(this);
  }

  public void login()
  {
    try
    {
      String person = this.model.login(nickname.get(), password.get());
      error.set("");
      this.support.firePropertyChange("PersonLoggedIn", null, person);
    }
    catch (Exception e)
    {
      error.set(e.getMessage());
    }
  }

  public void reset()
  {
    nickname.set("");
    password.set("");
    error.set("");
  }

  public void bindNickname(StringProperty property)
  {
    nickname.bindBidirectional(property);
  }

  public void bindPassword(StringProperty property)
  {
    password.bindBidirectional(property);
  }

  public void disconnect()
  {
    this.model.disconnect();
  }

  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    this.support.addPropertyChangeListener(listener);
  }
}
