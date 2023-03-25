package assignment2.client;

import assignment2.model.Message;
import assignment2.model.Person;
import assignment2.model.PersonList;
import assignment2.model.validation.StringValidator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements ClientInterface
{
  public static final String HOST = "localhost";
  public static final int PORT = 8080;
  public static final String GROUP_ADDRESS = "230.0.0.0";
  public static final int GROUP_PORT = 8888;

  private final Socket socket;
  private final ObjectInputStream input;
  private final ObjectOutputStream output;
  private final MessageListener messageListener;

  private ArrayList<String> nicknames;
  private ArrayList<Person> persons;
  private PersonList personList;
  private final PropertyChangeSupport support;

  public Client() throws IOException
  {
    this.support = new PropertyChangeSupport(this);
    this.socket = new Socket(HOST, PORT);

    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();

    this.output = new ObjectOutputStream(outputStream);
    this.input = new ObjectInputStream(inputStream);

    this.personList = PersonList.getInstance();
    this.persons = new ArrayList<>();
    this.nicknames = new ArrayList<>();

    this.messageListener = new MessageListener(this, GROUP_ADDRESS, GROUP_PORT);
    Thread thread = new Thread(messageListener);
    thread.start();
  }

  @Override public synchronized void disconnect() throws IOException
  {
    output.writeObject(socket.getLocalAddress() + " disconnected.");
    output.flush();
    messageListener.close();
    output.close();
    input.close();
    socket.close();
  }

  @Override public synchronized String login(String nickname, String password)
      throws IOException, InterruptedException
  {
    StringValidator.validateNickname(nickname);
    StringValidator.validatePassword(password);

    output.writeObject("GET_PERSONS");
    output.flush();
    this.wait();

    Person person = new Person(nickname, password);

    for (Person temp : persons)
    {
      if (temp.getNickname().equals(nickname) && temp.getPassword().equals(password))
        return temp.getNickname();
    }

    if (nicknames.contains(nickname))
      throw new IllegalArgumentException("Nickname already exists");

    addPerson(person);

    output.writeObject(socket.getLocalAddress() + " logged in.");
    output.flush();

    return person.getNickname();
  }

  @Override public synchronized void addPerson(Person person)
      throws IOException, InterruptedException
  {
    output.writeObject(person);
    output.flush();
    this.wait();

    output.writeObject(socket.getLocalAddress() + " created an account.");
    output.flush();

    this.support.firePropertyChange("PersonAdded", null, person.getNickname());
  }

  @Override public synchronized ArrayList<String> getPersonsWithoutFriends(String person)
  {
    if (person == null)
      throw new IllegalArgumentException("Error while logging");

    ArrayList<String> friends = getFriends(person);
    ArrayList<String> temp = (ArrayList<String>) nicknames.clone();
    for (String friend : friends)
    {
      for (int i = 0; i < temp.size(); i++)
      {
        if (temp.get(i).equals(friend))
          temp.remove(temp.get(i));
      }
    }
    for (int i = 0; i < temp.size(); i++)
    {
      if (temp.get(i).equals(person))
        temp.remove(temp.get(i));
    }
    return temp;
  }

  @Override public synchronized void addFriend(String person, String friend)
      throws IOException, InterruptedException
  {
    if (person == null)
      throw new IllegalArgumentException("Error while logging.");
    else if (friend == null)
      throw new IllegalArgumentException("No friend selected.");

    Person temp1 = null;
    Person temp2 = null;

    for (Person value : persons)
    {
      if (value.getNickname().equals(person))
      {
        temp1 = value;
      }
      else if (value.getNickname().equals(friend))
      {
        temp2 = value;
      }
    }

    if (temp1 == null || temp2 == null)
      throw new IllegalArgumentException("Friend not added");

    temp1.addFriend(temp2);
    temp2.addFriend(temp1);

    output.writeObject(temp1);
    output.flush();
    this.wait();
    output.writeObject(temp2);
    output.flush();
    this.wait();

    output.writeObject(socket.getLocalAddress() + " added new friend " + friend + ".");
    output.flush();

    this.support.firePropertyChange("FriendAdded", null, friend);
  }

  @Override public synchronized ArrayList<String> getFriends(String person)
  {
    if (person == null)
      throw new IllegalArgumentException("Error while logging");

    for (Person temp : persons)
    {
      if (temp.getNickname().equals(person))
        return temp.getFriends();
    }
    return new ArrayList<>();
  }

  @Override public synchronized void sendMessage(String content, String sender, String receiver)
      throws IOException, InterruptedException
  {
    if (sender == null)
      throw new IllegalArgumentException("Error while logging.");
    else if (receiver == null)
      throw new IllegalArgumentException("Chat error.");

    Message message = new Message(content, sender, receiver);
    Person temp1 = null;
    Person temp2 = null;
    for (Person person : persons)
    {
      if (person.getNickname().equals(sender))
      {
        temp1 = person;
        temp1.addMessage(message);
      }
      if (person.getNickname().equals(receiver))
      {
        temp2 = person;
        temp2.addMessage(message);
      }
    }

    if (temp1 == null && temp2 == null)
    {
      throw new IllegalArgumentException("Message not sent");
    }

    output.writeObject(temp1);
    output.flush();
    this.wait();
    output.writeObject(temp2);
    output.flush();
    this.wait();

    output.writeObject(socket.getLocalAddress() + " sent a message " + message.getContent() + " to " + receiver + ".");

    this.support.firePropertyChange("MessageSent", null, message);
  }

  @Override public synchronized ArrayList<Message> getMessages(String sender, String receiver)
  {
    if (sender == null)
      throw new IllegalArgumentException("Error while logging.");
    else if (receiver == null)
      throw new IllegalArgumentException("Chat error.");

    ArrayList<Message> tempList = new ArrayList<>();
    if (sender.equals(receiver))
    {
      for (Person temp : persons)
      {
        if (temp.getNickname().equals(sender))
        {
          for (int i = 0; i < temp.getMessages().size(); i++)
          {
            if (temp.getMessages().get(i).getSender().equals(sender) && temp.getMessages().get(i).getReceiver().equals(receiver))
              tempList.add(temp.getMessages().get(i));
          }
          return tempList;
        }
      }
    }

    for (Person temp : persons)
    {
      if (temp.getNickname().equals(sender))
      {
        for (int i = 0; i < temp.getMessages().size(); i++)
        {
          if (temp.getMessages().get(i).getSender().equals(receiver) || temp.getMessages().get(i).getReceiver().equals(receiver))
            tempList.add(temp.getMessages().get(i));
        }
        return tempList;
      }
    }
    throw new IllegalArgumentException("Messages cannot be accessed.");
  }

  public synchronized void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    this.support.addPropertyChangeListener(listener);
  }

  @Override public synchronized void receiveBroadcast(Object object)
  {
    if (object instanceof PersonList)
    {
      this.personList = (PersonList) object;
      this.persons = (ArrayList<Person>) this.personList.getPersons().clone();
      this.nicknames = (ArrayList<String>) this.personList.getNicknames().clone();
      this.support.firePropertyChange("ResetClient", false, true);
      this.notify();
    }
  }
}
