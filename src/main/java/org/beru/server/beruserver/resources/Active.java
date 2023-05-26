package org.beru.server.beruserver.resources;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.beru.server.beruserver.controller.DataBaseController;
import org.beru.server.beruserver.controller.SftpController;
import org.beru.server.beruserver.controller.files.FileExplorerController;
import org.beru.server.beruserver.model.SSH;
import org.beru.server.beruserver.model.db.SqlManager;
import org.beru.server.beruserver.model.login.User;

public class Active {
    public static Stage activeStage;
    public static R r;
    public static User user;
    public static SSH ssh;
    public static SftpController sftpController;
    public static DataBaseController dataBaseController;
    public static FileExplorerController fileExplorerController;
    public static TabPane tabPane;
}
