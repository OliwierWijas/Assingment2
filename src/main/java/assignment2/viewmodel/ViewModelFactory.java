package assignment2.viewmodel;

import assignment2.model.Model;

public class ViewModelFactory
{
  private final LoginViewModel loginViewModel;
  private final MessageViewModel messageViewModel;
  private final ChatViewModel chatViewModel;

  public ViewModelFactory(Model model)
  {
    this.loginViewModel = new LoginViewModel(model);
    this.messageViewModel = new MessageViewModel(model);
    this.chatViewModel = new ChatViewModel(model);

    this.loginViewModel.addPropertyChangeListener(messageViewModel);
    this.loginViewModel.addPropertyChangeListener(chatViewModel);
    this.messageViewModel.addPropertyChangeListener(chatViewModel);
  }

  public LoginViewModel getLoginViewModel()
  {
    return loginViewModel;
  }

  public MessageViewModel getMessageViewModel()
  {
    return messageViewModel;
  }

  public ChatViewModel getChatViewModel()
  {
    return chatViewModel;
  }
}
