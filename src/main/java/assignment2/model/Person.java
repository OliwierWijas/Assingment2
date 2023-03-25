package assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable
{
  private final String nickname;
  private final String password;
  private final ArrayList<Message> messages;
  private final ArrayList<String> friends;

  public Person(String nickname, String password)
  {
    this.nickname = nickname;
    this.password = password;
    this.messages = new ArrayList<>();
    this.friends = new ArrayList<>();
  }

  public String getNickname()
  {
    return nickname;
  }

  public String getPassword()
  {
    return password;
  }

  public void addMessage(Message message)
  {
    messages.add(message);
  }

  public ArrayList<Message> getMessages()
  {
    return messages;
  }

  public void addFriend(Person friend)
  {
    for (int i = 0; i < friends.size(); i++)
    {
      if (friends.get(i).equals(friend))
        return;
    }
    friends.add(friend.getNickname());
  }

  public ArrayList<String> getFriends()
  {
    return friends;
  }

  public String toString()
  {
    return nickname;
  }

  public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != getClass())
    {
      return false;
    }

    Person other = (Person) obj;
    return this.nickname.equals(other.nickname);
  }
}
