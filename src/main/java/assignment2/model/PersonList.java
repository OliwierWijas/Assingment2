package assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonList implements Serializable
{
  private static PersonList instance;
  private final ArrayList<Person> persons;
  private final ArrayList<String> nicknames;

  private PersonList()
  {
    this.persons = new ArrayList<>();
    this.nicknames = new ArrayList<>();
  }

  public static synchronized PersonList getInstance()
  {
    if (instance == null)
    {
      instance = new PersonList();
    }
    return instance;
  }

  public synchronized void addPerson(Person person)
  {
    for (int i = 0; i < persons.size(); i++)
    {
      if (persons.get(i).equals(person))
      {
        return;
      }
    }
    persons.add(person);
    nicknames.add(person.getNickname());
  }

  public synchronized void changePerson(Person person)
  {
    for (int i = 0; i < persons.size(); i++)
    {
      if (persons.get(i).equals(person))
      {
        persons.remove(persons.get(i));
        persons.add(person);
        break;
      }
    }
  }

  public synchronized ArrayList<Person> getPersons()
  {
    return persons;
  }

  public synchronized ArrayList<String> getNicknames()
  {
    return nicknames;
  }

  public synchronized String toString()
  {
    String temp = "";
    for (int i = 0; i < persons.size(); i++)
    {
      temp += persons.get(i) + "\n";
    }
    return temp;
  }
}
