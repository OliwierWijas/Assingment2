package assignment2.viewmodel;

import assignment2.model.Message;
import assignment2.model.Model;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatViewModel implements PropertyChangeListener
{
  private final Model model;
  private String user;
  private String friend;
  private final SimpleStringProperty text;
  private final SimpleListProperty<Message> messages;
  private final StringProperty error;

  public ChatViewModel(Model model)
  {
    this.model = model;
    this.user = null;
    this.friend = null;
    this.text = new SimpleStringProperty("");
    this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
    this.error = new SimpleStringProperty("");
    this.model.addPropertyChangeListener(this);
  }

  public void sendMessage()
  {
    try
    {
      model.sendMessage(text.get(), user, friend);
      text.set("");
    }
    catch (Exception e)
    {
      error.set(e.getMessage());
    }
  }

  public void reset()
  {
    text.set("");
    error.set("");
  }

  private void resetMessagesList()
  {
    try
    {
      messages.clear();
      messages.addAll(model.getMessages(user, friend));
    }
    catch (Exception e)
    {
      error.set(e.getMessage());
    }
  }

  public void bindText(StringProperty property)
  {
    text.bindBidirectional(property);
  }

  public void bindMessagesList(ObjectProperty<ObservableList<Message>> property)
  {
    property.bind(messages);
  }

  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("PersonLoggedIn"))
        this.user = (String) evt.getNewValue();
      else if (evt.getPropertyName().equals("ChatEntered"))
      {
        this.friend = (String) evt.getNewValue();
        resetMessagesList();
      }
      else if (evt.getPropertyName().equals("MessageSent") || evt.getPropertyName().equals("ResetModel"))
        resetMessagesList();
    });
  }
}
