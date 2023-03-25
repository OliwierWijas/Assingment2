package assignment2.model;

import assignment2.client.Client;
import assignment2.client.ClientInterface;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ModelManager implements Model, PropertyChangeListener
{
  private final PropertyChangeSupport support;
  private final ClientInterface client;

  public ModelManager() throws IOException
  {
    this.support = new PropertyChangeSupport(this);
    this.client = new Client();
    this.client.addPropertyChangeListener(this);
  }

  public String login(String nickname, String password) throws IOException, InterruptedException
  {
    return client.login(nickname, password);
  }

  public ArrayList<String> getPersonsWithoutFriends(String person)
      throws IOException, InterruptedException
  {
    return client.getPersonsWithoutFriends(person);
  }

  public void addFriend(String person, String friend)
      throws IOException, InterruptedException
  {
    client.addFriend(person, friend);
  }

  public ArrayList<String> getFriends(String person)
      throws IOException, InterruptedException
  {
    return client.getFriends(person);
  }

  public void sendMessage(String content, String sender, String receiver)
      throws IOException, InterruptedException
  {
    Message message = new Message(content, sender, receiver);
    client.sendMessage(content, sender, receiver);
  }

  public ArrayList<Message> getMessages(String sender, String receiver)
      throws IOException, InterruptedException
  {
    return client.getMessages(sender, receiver);
  }

  public void disconnect()
  {
    try
    {
      this.client.disconnect();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    this.support.addPropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("PersonAdded"))
        this.support.firePropertyChange("PersonAdded", null, evt.getNewValue());
      else if (evt.getPropertyName().equals("FriendAdded"))
        this.support.firePropertyChange("FriendAdded", null, evt.getNewValue());
      else if (evt.getPropertyName().equals("MessageSent"))
        this.support.firePropertyChange("MessageSent", null, evt.getNewValue());
      else if (evt.getPropertyName().equals("ResetClient"))
        this.support.firePropertyChange("ResetModel", false, true);
      else if (evt.getPropertyName().equals("ActiveUsersChange"))
        this.support.firePropertyChange("ActiveUsersChange", null, evt.getNewValue());
    });
  }
}
