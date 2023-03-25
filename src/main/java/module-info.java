module com.example.assingment2 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires com.google.gson;
  requires spring.core;

  opens assignment2.view to javafx.fxml, com.google.gson;
  opens assignment2.client to com.google.gson;
  opens assignment2.server to com.google.gson;
  opens assignment2.model to com.google.gson;
  opens assignment2.viewmodel to com.google.gson;
  exports assignment2.application;
}