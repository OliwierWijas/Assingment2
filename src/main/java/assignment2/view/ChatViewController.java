package assignment2.view;

import assignment2.model.Message;
import assignment2.viewmodel.ChatViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class ChatViewController implements ViewController
{
  @FXML private ListView<Message> messages;
  @FXML private TextField text;
  @FXML private Label error;

  private ViewHandler viewHandler;
  private ChatViewModel viewModel;
  private Region root;

  public void init(ViewHandler viewHandler, ChatViewModel viewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;

    this.viewModel.bindMessagesList(messages.itemsProperty());
    this.viewModel.bindText(text.textProperty());
    this.viewModel.bindError(error.textProperty());
  }

  @FXML protected void sendButtonPressed()
  {
    viewModel.sendMessage();
  }

  @FXML protected void backButtonPressed()
  {
    viewHandler.openView(ViewFactory.MESSAGE);
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
