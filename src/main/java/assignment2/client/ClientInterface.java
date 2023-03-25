package assignment2.client;

import assignment2.model.Message;
import assignment2.model.Person;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public interface ClientInterface
{
  void disconnect() throws IOException;
  String login(String nickname, String password)
      throws IOException, InterruptedException;
  void addPerson(Person person) throws IOException, InterruptedException;
  void addFriend(String person, String friend)
      throws IOException, InterruptedException;
  ArrayList<String> getFriends(String person);
  ArrayList<String> getPersonsWithoutFriends(String person);
  void sendMessage(String content, String sender, String receiver)
      throws IOException, InterruptedException;
  ArrayList<Message> getMessages(String sender, String receiver);
  void addPropertyChangeListener(PropertyChangeListener listener);
  void receiveBroadcast(Object object);
}
