package LobbyList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class ShowLobbyList implements Runnable {
    private static HashMap<Integer, Room> rooms;
    private static FXRoomController roomController;

    public ShowLobbyList(HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
        roomController = new FXRoomController();
        roomController.setListOfRooms(rooms);
    }

    @Override
    public void run() {
        Application.launch(FX.class, "");
    }

    public static class FX extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            System.out.println("FX. Start..");
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefSize(700, 400);
            VBox vBox = new VBox(20);
            vBox.setFillWidth(true);
            Button refreshButton = new Button("refresh");
            vBox.getChildren().add(refreshButton);
            refreshButton.setOnMousePressed(event -> {
                if (vBox.getChildren().size() > 1) {
                    vBox.getChildren().remove(1, roomController.size()+1);
                }
                for (int i = 0; i < roomController.size(); i++) {
                    Text nameText = new Text();
                    nameText.setText(roomController.getRoom(i).getName());
                    nameText.setFont(Font.font(40));

                    Text countOfUsersText = new Text();
                    countOfUsersText.setText(roomController.getRoom(i).getUsersCount() + "/10");
                    countOfUsersText.setFont(Font.font(30));

                    VBox boxUsers = new VBox(20);
                    for (User u: roomController.getRoom(i).getConnectedUsers().values()) {
                        Text userNameText = new Text();
                        userNameText.setFont(Font.font(20));
                        userNameText.setText(u.getUserName());
                        boxUsers.getChildren().add(userNameText);
                    }
                    HBox hBox = new HBox(100, nameText, countOfUsersText, boxUsers);
                    hBox.setStyle("-fx-border-color: black");
                    hBox.setAlignment(Pos.TOP_CENTER);
                    hBox.setFillHeight(true);
                    vBox.getChildren().add(hBox);
                    vBox.setFillWidth(true);
                    System.out.println("FX. I drew room:" + i);
                }
            });
            scrollPane.setContent(vBox);
            primaryStage.setScene(new Scene(scrollPane, 700,400));
            primaryStage.show();
        }
    }
}
