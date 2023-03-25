package assignment2.application;

import assignment2.model.Model;
import assignment2.model.ModelManager;
import assignment2.view.ViewHandler;
import assignment2.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class MyApplication extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    Model model = new ModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }
}
