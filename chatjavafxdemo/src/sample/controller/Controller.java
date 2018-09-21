package sample.controller;

import javafx.scene.control.*;
import sample.model.ChatManager;
import sample.model.bean.MsgData;
import sample.model.bean.Userdata;
import sample.model.DatabaseModel;
import sample.view.MainWindow;
import sample.view.window;
import sample.view.Dialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 * 页面控制类 所有页面的控制 包含的方法  exec() 和 $()
 * 包含的属性为View包下的页面类
 */
public class Controller {
    private Dialog dialog;
//    private Register register;
//    private Forget forget;
    private MainWindow mainWindow;
    public static Userdata userdata;
//    private Homepage homepage;
    public static DatabaseModel database;
//    private AlterPerson alterPerson;
//    public static FriendPage friendPage;
//    public static SearchFriend searchFriend;
//    private HeadProtrait headProtrait;
    private String friendName;
    private String friendHead;
//    public static Alert alert;

    public Controller() throws IOException {
//        dialog = new Dialog();
//        register = new Register();
        userdata = new Userdata();
        database = new DatabaseModel();
//        forget = new Forget();
        mainWindow = new MainWindow();
//        homepage = new Homepage();
//        alterPerson = new AlterPerson();
//        alert = new Alert();
//        friendPage = new FriendPage();
//        searchFriend = new SearchFriend();
//        headProtrait = new HeadProtrait();
        MsgData.msg = new Vector<>();
        MsgData.MsgMap = new HashMap<>();
        MsgData.accountList = new Vector<>();
//        database.connect();
//        dialog.show();
        mainWindow.show();

    }

    /**
     * 该方法实现各个页面的各种交互 例如点击当前页面的按钮 跳转至另一个页面 所有功能集合
     *
     * @throws ClassNotFoundException
     */
    public void exec() throws ClassNotFoundException {
//        headProtrait.setModailty(register);//pass
//        headProtrait.setModailty(alterPerson);//pass
//        alert.setModailty(mainWindow);//pass
//        alert.setModailty(searchFriend);//pass
        ChatManager.getInstance().setMainWindow(mainWindow);
        initEvent();
//        dialogExec();//pass
//        forgetExec();//pass
//        alterPersonExec();//pass
//        registerExec();//pass
        sendMsgExec();
//        OptionHead();
//        SearchFriends();//pass
//        find();//pass
//        FriendInfo();
        saveRemark();
//        dialog.show();
        mainWindow.show();
    }

    /**
     * 初始化事件
     */
    public void initEvent() {
//        ((Button) $(mainWindow, "maximization")).setOnAction(event -> {
//            searchFriend.clear();
//            searchFriend.show();
//        });
    }

    /**
     * 该方法通过页面对象 以及给定的id 选择页面的元素  用法:TextField t = (TextField)$(dialog,"UserName");
     * 这样选出登入框对象的id为UserName的输入框 之后就可以为 t 绑定事件了
     *
     * @param window
     * @param id
     * @return
     */
    private Object $(window window, String id) {
        return (Object) window.getRoot().lookup("#" + id);
    }

    /**
     *
     * 登入功能
     */
//    private void dialogExec() {
//        ((Button) $(dialog, "enter")).setOnAction(event -> {
//            //                    mainWindow.setHead(userdata.getHead());
////                    mainWindow.setPersonalInfo(userdata.getAccount(),userdata.getName(),userdata.getAddress(),userdata.getPhone());
//            //输入框禁用
////            ((TextField) $(mainWindow, "input")).setDisable(true);
////            ((Button) $(mainWindow, "send")).setDisable(true);
//            ChatManager.getInstance().connect("127.0.0.1", "");//链接服务器
//
//        });
//    }

    /**
     * 发消息功能
     *
     */
    private void sendMsgExec() {
//        ((Button) $(mainWindow, "server_start")).setOnAction(event -> {
//            System.out.println("server_start");
////            ChatManager.getInstance().serverStart();//开启server端线程
////            ChatManager.getInstance().connect();//开启client端线程
//        });
        ((Button) $(mainWindow, "client_start")).setOnAction(event -> {
            System.out.println("client_start");
            ChatManager.getInstance().connect();//开启client端线程
        });

        ((Button) $(mainWindow,"click_true")).setOnAction(event -> {
            String input = "是";
            System.out.println("====" + input);
            try {
                ChatManager.getInstance().send(input);//向服务器发消息
            } catch (IOException e) {
                e.printStackTrace();
            }
            //读跟写有时会不同步，因此需要synchronized
            synchronized (Controller.class) {
                mainWindow.addRight(userdata.getHead(), input);//添加自己的消息
            }
        });
        ((Button) $(mainWindow,"click_false")).setOnAction(event -> {
            String input = "不是";
            System.out.println("=====" + input);
            try {
                ChatManager.getInstance().send(input);//向服务器发消息
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (Controller.class) {
                mainWindow.addRight(userdata.getHead(), input);//添加自己的消息
            }
        });
        ((Button) $(mainWindow,"click_unknown")).setOnAction(event -> {
            String input = "不知道";
            System.out.println("====" + input);
            try {
                ChatManager.getInstance().send(input);//向服务器发消息
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (Controller.class) {
                mainWindow.addRight(userdata.getHead(), input);//添加自己的消息
            }
        });

        ((Button) $(mainWindow, "send")).setOnAction(event -> {
            String input = ((TextField) $(mainWindow, "input")).getText();
            if (!input.equals("")) {
//                String line = userdata.getAccount() + " " + youAccount + " " + input;
                try {
                    ChatManager.getInstance().send(input);//向服务器发消息
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainWindow.addRight(userdata.getHead(), input);//添加自己的消息
                ((TextField) $(mainWindow, "input")).clear();//清输入框
            } else {
                System.out.println("input = 空");
                return;
            }
        });


    }


//    public void FriendInfo(){
//        ((Button) $(mainWindow,"moref")).setOnAction(event -> {
//            int index = mainWindow.getFriendList().getSelectionModel().getSelectedIndex();
//            String account = MsgDat a.accountList.get(index);
//            if(account.equals("WeChat聊天助手"))
//            {
//                return;
//            }
//            else {
//                if (friendPage.isShowing()) {
//                    friendPage.close();
//                }
//                try {
//                    ResultSet resultSet = database.execResult("SELECT * FROM user WHERE account=?", account);
//                    resultSet.next();
//                    friendPage.setFriendData(resultSet,((Label)$(mainWindow,"Y_account")).getText());
//                    friendPage.show();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public void saveRemark(){
//        ((Button) $(friendPage,"submit")).setOnAction(event -> {
//            String remark = ((TextField) $(friendPage,"remark")).getText();
//            if(remark.equals("")){
//                return ;
//            }
//            int index = MsgData.accountList.indexOf(((Label) $(friendPage,"account")).getText());
//            if(index==-1)
//            {
//                return ;
//            }
//            int index1 = mainWindow.getFriendList().getSelectionModel().getSelectedIndex();
//            if(index == index1) {
//                mainWindow.getFriendVector().get(index).setText(remark);
//                ((Label) $(mainWindow,"Y_account")).setText(remark);
//            }
//            else
//            {
//                mainWindow.getFriendVector().get(index).setText(remark);
//            }
//            try {
//                database.exec("UPDATE companion SET remark=? WHERE I_account=? AND Y_account =?",remark,userdata.getAccount(),((Label) $(friendPage,"account")).getText());
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        });

    }
}
