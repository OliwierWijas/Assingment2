package assignment2.model;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public interface Model
{
  String login(String nickname, String password)
      throws IOException, InterruptedException;
  ArrayList<String> getPersonsWithoutFriends(String person)
      throws IOException, InterruptedException;
  void addFriend(String person, String friend)
      throws IOException, InterruptedException;
  ArrayList<String> getFriends(String person)
      throws IOException, InterruptedException;
  void sendMessage(String content, String sender, String receiver)
      throws IOException, InterruptedException;
  ArrayList<Message> getMessages(String sender, String receiver)
      throws IOException, InterruptedException;
  void disconnect();
  void addPropertyChangeListener(PropertyChangeListener listener);
}
