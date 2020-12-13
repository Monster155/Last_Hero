package ru.itlab.lasthero.GameServer;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
import ru.itlab.lasthero.GameServer.Player.OtherUser;

public class OtherUsersController {
    private Group group;
    private ArrayList<Pair<Boolean, OtherUser>> otherUsersToUpdate;
    private HashMap<Integer, OtherUser> otherUsers;

    public OtherUsersController() {
        group = new Group();
    }

    public void addNewOtherUser(OtherUser otherUser) {

    }

    public void updateOtherUser() {

    }

    public Group getGroup() {
        return group;
    }
}
