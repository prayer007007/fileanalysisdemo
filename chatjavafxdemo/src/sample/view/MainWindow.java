package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import sample.model.Tool;

import java.io.IOException;
import java.util.Vector;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午9:42
 * good luck
 **/
public class MainWindow extends window {
    private ListView friendList;
    private ListView chatList;
    private Vector<FriendListItem> friendVector;
    private Vector<ChatListItem> chatVector;
    private ContextMenu contextMenu;
    private Vector<MenuItem> rightItem;
    private double xOffset;
    private double yOffset;
    public MainWindow() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Fxml/MainWindow.fxml"));
        Scene scene = new Scene(root, 1400, 700);
        scene.setFill(Color.TRANSPARENT);
        setTitle("Hello World");
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        setResizable(false);
        setTitle("We Chat");
        friendList = ((ListView) $("FirendList"));
        chatList = ((ListView) $("ChatList"));
        friendVector = new Vector<>();
        contextMenu = new ContextMenu();
        rightItem = new Vector<>();
        rightItem.add(new MenuItem("  添加好友  "));
        rightItem.add(new MenuItem("  设置      "));
        rightItem.add(new MenuItem("  最小化    "));
        rightItem.add(new MenuItem("  退出      "));
//        BackgroundMenu();
        setIcon();
        move();
        quit();
        minimiser();
        initTooltip();

    }

    //别人的消息
    public void addLeft(String head,String Msg){
        chatList.getItems().add(new ChatListItem().Left(head,Msg, Tool.getWidth(Msg),Tool.getHight(Msg)));
    }
    //自己的消息
    public void addRight(String head,String Msg){
        chatList.getItems().add(new ChatListItem().Right(head,Msg,Tool.getWidth(Msg),Tool.getHight(Msg)));
    }

    public void setHead(String Head){
        setHeadPortrait(((Button) $("individual")),Head);
    }

    public void initTooltip(){
        ((Button) $("maximization")).setTooltip(new Tooltip("添加好友"));
        ((Button) $("setting")).setTooltip(new Tooltip("设置"));
        ((Button) $("individual")).setTooltip(new Tooltip("个人资料"));
        ((Button) $("moref")).setTooltip(new Tooltip("好友资料"));
        ((TextField) $("search")).setTooltip(new Tooltip("查找好友"));
        ((Button) $("send")).setTooltip(new Tooltip("发送"));
    }

    @Override
    public void quit() {
        ((Button) $("quit1")).setTooltip(new Tooltip("退出"));
        ((Button) $("quit1")).setOnAction(event -> {
            close();
            System.exit(0);
        });
    }

    @Override
    public void minimiser() {

    }

    public static void setHeadPortrait(Button button, String head){
        button.setStyle(String.format("-fx-background-image: url('/view/Fxml/CSS/Image/head/head1.jpg')",head));
    }
//    public static void setHeadPortrait(Button button ,String background,String file){
//        button.setStyle(String.format("-fx-background-image: url('/view/Fxml/CSS/Image/%s/%s.jpg')",file,background));
//    }

    /**
     * 好友列表
     * @return
     */
    public ListView getFriendList() {
        return friendList;
    }
}

// 聊天列表项类
class ChatListItem{
    private Pane pane;
    private Button head;
    private TextArea text;
    private Pane left;
    private Pane right;
    private Button arrow;
    public ChatListItem(){
        pane = new Pane();
        head = new Button();
        text = new TextArea();
        pane.setPrefSize(730,150);
        left = new Pane();
        right = new Pane();
        arrow = new Button();
        arrow.setDisable(false);
        arrow.setPrefSize(32,32);
        left.setPrefSize(580,70);
        right.setPrefSize(580,70);
        head.getStyleClass().add("head");
        pane.getStyleClass().add("pane");
        left.getStyleClass().add("pane");
        right.getStyleClass().add("pane");
        head.setPrefSize(50,50);
        text.setPrefSize(480,50);
        text.setWrapText(true);
        text.setEditable(false);
    }
    public Pane Left(String ihead,String itext,double width,double hight){//别人的消息
        text.getStyleClass().add("lefttext");
        arrow.getStyleClass().add("leftarrow");
        pane.setPrefHeight(110+hight);
        left.setPrefHeight(30+hight);
        head.setLayoutY(10);
        head.setLayoutX(10);
        text.setPrefSize(width,hight);
        text.setLayoutX(100);
        text.setLayoutY(30);
        arrow.setLayoutY(40);
        arrow.setLayoutX(85);
        text.setText(itext);
//        MainWindow.setHeadPortrait(head,ihead);
        left.getChildren().add(head);
        left.getChildren().add(text);
        left.getChildren().add(arrow);
        pane.getChildren().add(left);

        return pane;


    }
    public Pane Right(String ihead,String itext,double width,double hight){//自己的消息
        text.getStyleClass().add("righttext");
        arrow.getStyleClass().add("rightarrow");
        head.setLayoutY(10);
        head.setLayoutX(510);
        pane.setPrefHeight(110+hight);
        right.setPrefHeight(30+hight);
        text.setPrefSize(width,hight);
        //text=480 0 text = 470 10 460 20 480-width
        text.setLayoutY(30);
        text.setLayoutX(480-width);
        arrow.setLayoutY(40);
        arrow.setLayoutX(475);
        text.setText(itext);
//        MainWindow.setHeadPortrait(head,ihead);
        right.getChildren().add(head);
        right.getChildren().add(text);
        right.getChildren().add(left);
        right.getChildren().add(arrow);
        right.setLayoutX(150);
        pane.getChildren().add(right);
        return pane;

    }

}
