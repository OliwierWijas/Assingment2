package assignment2.viewmodel;

import assignment2.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageViewModel implements PropertyChangeListener
{
  private final Model model;
  private String user;
  private final StringProperty error1;
  private final StringProperty error2;
  private final SimpleListProperty<String> friends;
  private final SimpleListProperty<String> persons;
  private final SimpleObjectProperty<String> friend;
  private final SimpleObjectProperty<String> person;
  private final PropertyChangeSupport support;

  public MessageViewModel(Model model)
  {
    this.model = model;
    this.user = null;
    this.error1 = new SimpleStringProperty("");
    this.error2 = new SimpleStringProperty("");
    this.friends = new SimpleListProperty<>(FXCollections.observableArrayList());
    this.persons = new SimpleListProperty<>(FXCollections.observableArrayList());
    this.friend = new SimpleObjectProperty<>();
    this.person = new SimpleObjectProperty<>();
    this.support = new PropertyChangeSupport(this);
    this.model.addPropertyChangeListener(this);
  }

  public void enterChat()
  {
    if (user == null)
    {
      throw new IllegalArgumentException("Error while logging.");
    }
    else if (friend.get() != null)
    {
      this.support.firePropertyChange("ChatEntered", null, friend.get());
    }
    else
      error1.set("No friend selected.");
  }

  public void logOut()
  {
    if (user == null)
    {
      error1.set("Error while logging.");
    }
    else
      user = null;
  }

  public void addFriend()
  {
    if (user == null)
    {
      error2.set("Error while logging.");
    }
    try
    {
      model.addFriend(user, person.get());
    }
    catch (Exception e)
    {
      error2.set(e.getMessage());
    }
  }

  public void reset()
  {
    error1.set("");
    error2.set("");
    resetFriendsList();
    resetPersonsList();
  }

  private void resetFriendsList()
  {
    try
    {
      friends.clear();
      friends.addAll(model.getFriends(user));
    }
    catch (Exception e)
    {
      error1.set(e.getMessage());
    }
  }

  private void resetPersonsList()
  {
    try
    {
      persons.clear();
      persons.addAll(model.getPersonsWithoutFriends(user));
    }
    catch (Exception e)
    {
      error2.set(e.getMessage());
    }
  }

  public void bindError1(StringProperty property)
  {
    property.bind(error1);
  }

  public void bindError2(StringProperty property)
  {
    property.bind(error2);
  }

  public void bindFriendsList(ObjectProperty<ObservableList<String>> property)
  {
    property.bind(friends);
  }

  public void bindPersonsList(ObjectProperty<ObservableList<String>> property)
  {
    property.bind(persons);
  }

  public void bindFriend(ReadOnlyObjectProperty<String> property)
  {
    friend.bind(property);
  }

  public void bindPerson(ReadOnlyObjectProperty<String> property)
  {
    person.bind(property);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    this.support.addPropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("PersonLoggedIn"))
        this.user = (String) evt.getNewValue();
      else if (evt.getPropertyName().equals("PersonAdded"))
        resetPersonsList();
      else if (evt.getPropertyName().equals("FriendAdded"))
      {
        resetFriendsList();
        resetPersonsList();
      }
      else if (evt.getPropertyName().equals("ResetModel"))
      {
        resetFriendsList();
        resetPersonsList();
      }
    });
  }
}
