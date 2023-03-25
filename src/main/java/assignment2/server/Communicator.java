package assignment2.server;

import assignment2.file.FileLog;
import assignment2.model.Person;
import assignment2.model.PersonList;

import java.io.*;
import java.net.Socket;

public class Communicator implements Runnable
{
  private final Socket socket;
  private final Broadcaster broadcaster;
  private FileLog fileLog;
  private File file;

  private final PersonList personList;

  public Communicator(Socket socket, Broadcaster broadcaster)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    this.personList = PersonList.getInstance();
    this.file = new File("src/main/java/assignment2/file/Log");
    this.fileLog = FileLog.getInstance(file);
  }

  private synchronized void communicate() throws IOException
  {
    try
    {
      InputStream inputStream = socket.getInputStream();
      OutputStream outputStream = socket.getOutputStream();

      ObjectOutputStream output = new ObjectOutputStream(outputStream);
      ObjectInputStream input = new ObjectInputStream(inputStream);

      loop:while (true)
      {
        Object request = input.readObject();

        if (request != null)
        {
          if (request instanceof Person)
          {
            Person temp = ((Person) request);
            personList.addPerson(temp);
            personList.changePerson(temp);
            broadcaster.broadcast(personList);
          }
          else if (request.equals("GET_PERSONS"))
          {
            broadcaster.broadcast(personList);
          }
          else if (request.toString().equals("EXIT"))
          {
            break loop;
          }
          else
            fileLog.log((String) request);
        }
      }
    }
    catch (ClassNotFoundException e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      socket.close();
    }
  }

  @Override public void run()
  {
    try
    {
      communicate();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
