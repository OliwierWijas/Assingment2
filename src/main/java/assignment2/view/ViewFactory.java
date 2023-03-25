package assignment2.view;

import assignment2.viewmodel.ViewModelFactory;
import javafx.scene.layout.Region;

public class ViewFactory
{
  private static ViewFactory instance;
  public static final String LOGIN = "LOGIN";
  public static final String MESSAGE = "MESSAGE";
  public static final String CHAT = "CHAT";

  private final LoginViewLoader loginViewLoader;
  private final MessageViewLoader messageViewLoader;
  private final ChatViewLoader chatViewLoader;

  private ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    loginViewLoader = new LoginViewLoader("LoginView.fxml", viewHandler, viewModelFactory);
    messageViewLoader = new MessageViewLoader("MessageView.fxml", viewHandler, viewModelFactory);
    chatViewLoader = new ChatViewLoader("ChatView.fxml", viewHandler, viewModelFactory);
  }

  public static synchronized ViewFactory getInstance(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    if (instance == null)
    {
      instance = new ViewFactory(viewHandler, viewModelFactory);
    }
    return instance;
  }

  public Region load(String id)
  {
    return switch (id) {
      case LOGIN -> loginViewLoader.loadView();
      case MESSAGE -> messageViewLoader.loadView();
      case CHAT -> chatViewLoader.loadView();
      default -> throw new IllegalArgumentException("Unknown id: " + id);
    };
  }
}
