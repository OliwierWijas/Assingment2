package assignment2.view;

import assignment2.viewmodel.MessageViewModel;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class MessageViewController implements ViewController
{
  @FXML private ListView<String> friends;
  @FXML private ListView<String> persons;
  @FXML private Label error1;
  @FXML private Label error2;

  private ReadOnlyObjectProperty<String> friend;
  private ReadOnlyObjectProperty<String> person;

  private ViewHandler viewHandler;
  private MessageViewModel viewModel;
  private Region root;

  public void init(ViewHandler viewHandler, MessageViewModel viewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;

    this.viewModel.bindFriendsList(friends.itemsProperty());
    this.viewModel.bindPersonsList(persons.itemsProperty());
    this.viewModel.bindError1(error1.textProperty());
    this.viewModel.bindError2(error2.textProperty());
    this.friend = friends.getSelectionModel().selectedItemProperty();
    this.viewModel.bindFriend(friend);
    this.person = persons.getSelectionModel().selectedItemProperty();
    this.viewModel.bindPerson(person);
  }

  @FXML protected void logOutButtonPressed()
  {
    viewModel.logOut();
    viewHandler.openView(ViewFactory.LOGIN);
  }

  @FXML protected void chatButtonPressed()
  {
    viewModel.enterChat();
    viewHandler.openView(ViewFactory.CHAT);
  }

  @FXML protected void addFriendButtonPressed()
  {
    viewModel.addFriend();
  }

  @Override public void reset()
  {
    viewModel.reset();
  }

  @Override public Region getRoot()
  {
    return root;
  }
}
