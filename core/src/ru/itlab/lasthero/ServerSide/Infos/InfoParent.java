package ru.itlab.lasthero.ServerSide.Infos;

import ru.itlab.lasthero.Protocol;

public abstract class InfoParent {
    public void decodeMessage(String message) {
//        System.out.println(message);
        int command = Integer.parseInt(message.substring(0, 2));
        String[] data = message.substring(2).split(Protocol.DIVIDER);
        executeCommand(command, data);
    }

    public abstract void executeCommand(int command, String data[]);
}
