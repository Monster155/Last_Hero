package LobbyList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class ShowLobbyList implements Runnable {
    private HashMap<Integer, Room> rooms;

    public ShowLobbyList(HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void run() {
        Application.launch(FX.class, "");
    }

    public static class FX extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            System.out.println("start");
            ScrollPane scrollPane = new ScrollPane();
            FXRoomController roomController = new FXRoomController();
            scrollPane.setPrefSize(700, 400);
            VBox vBox = new VBox();
            for (int i = 0; i < roomController.size(); i++) {
                VBox boxUsers = new VBox(10);
                System.out.println("We have some rooms: " + roomController.size());
                for (int j = 0; j < roomController.getRoom(i).getConnectedUsers().values().size(); i++) {
                    Text userNameText = new Text(roomController.getRoom(i).getConnectedUsers().get(j).toString());
                    userNameText.setFont(Font.font(20));
                    boxUsers.getChildren().add(userNameText);
                }
                Text nameText = new Text();
                nameText.setText(roomController.getRoom(i).getName());
                nameText.setFont(Font.font(40));
                Text countOfUsersText = new Text();
                countOfUsersText.setText(roomController.getRoom(i).getUsersCount() + "/10");
                countOfUsersText.setFont(Font.font(30));
                HBox hBox = new HBox(150, nameText, countOfUsersText, boxUsers);
                hBox.setAlignment(Pos.TOP_CENTER);
                hBox.setFillHeight(true);
                vBox.getChildren().add(hBox);
            }
            Scene scene = new Scene(vBox);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
