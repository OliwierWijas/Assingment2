package assignment2.model;

import java.io.Serializable;

public class Message implements Serializable
{
  private final String content;
  private final String sender;
  private final String receiver;

  public Message(String content, String sender, String receiver)
  {
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
  }

  public String getContent()
  {
    return content;
  }

  public String getSender()
  {
    return sender;
  }

  public String getReceiver()
  {
    return receiver;
  }

  public String toString()
  {
    return sender + ": " + content;
  }
}
