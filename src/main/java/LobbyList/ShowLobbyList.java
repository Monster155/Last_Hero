package LobbyList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowLobbyList extends Application {
    ObservableList<Room> rooms;
    private FXRoomController lobbyController;
    private ArrayList<User> users;

    public ShowLobbyList(HashMap<Integer, Room> rooms, ArrayList<User> users) {
        this.rooms = FXCollections.observableArrayList(rooms.values());
        this.users = users;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        rooms = FXCollections.observableArrayList(
                new Room("Room1", "127.0.0.1", 1, users),
                new Room("Room2", "127.0.0.1", 2, users),
                new Room("Room3", "127.0.0.1", 3, users)
        );
        lobbyController = new FXRoomController();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportWidth(700);
        scrollPane.setPrefViewportHeight(400);

        TableView<Room> tableView = new TableView<>(rooms);
        tableView.setPrefWidth(700);
        tableView.setPrefHeight(400);
        TableColumn<Room, String> roomNameCol = new TableColumn<>("Name of room:");
        TableColumn<Room, String> usersCountInRoomCol = new TableColumn<>("Counts of players:");
        TableColumn<Room, String> maxUsersCountInRoomCol = new TableColumn<>("Of Max:");
        TableColumn<Room, ArrayList<User>> connectedUsersCol = new TableColumn<>("Users:");
        tableView.getColumns().addAll(roomNameCol, usersCountInRoomCol, maxUsersCountInRoomCol, connectedUsersCol);
        roomNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersCountInRoomCol.setCellValueFactory(new PropertyValueFactory<>("usersCount"));
        maxUsersCountInRoomCol.setCellValueFactory(new PropertyValueFactory<>("MAX_COUNT_OF_USERS"));
        connectedUsersCol.setCellValueFactory(new PropertyValueFactory<>("users"));

        scrollPane.setContent(tableView);
        primaryStage.setTitle("Lobbies list");
        primaryStage.setScene(new Scene(scrollPane, 700, 400));
        primaryStage.show();


    }

    public void setRoom(Room room) {
        lobbyController.addRoom(room);

    }
}
 /*  for (int i=0;i<lobbyController.size(); i++){
            System.out.println("We have some rooms: " + lobbyController.size());
            Label labelName = new Label();
            labelName.setId("nameOfRoomLabel");
            labelName.setText(lobbyController.getRoom(i).getName());

            Label countOfUsersLabel = new Label();
            labelName.setId("countOfUsersLabel");
            countOfUsersLabel.setText(String.valueOf(lobbyController.getRoom(i).getUsersCount()) + "/10");
            HBox hBox = new HBox(labelName, countOfUsersLabel);
            hBox.setAlignment(Pos.TOP_CENTER);
            hBox.setSpacing(300);
        }*/