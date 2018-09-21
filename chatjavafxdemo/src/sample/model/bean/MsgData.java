package sample.model.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午9:50
 * good luck
 **/

public class MsgData {
    public static Vector<Vector<String>> msg = new Vector<>();//保存消息
    public static Map<String, Vector<String>> MsgMap = new HashMap<>();//保存消息
    public static Vector<String> accountList = new Vector<>();//保存好友账号
    public static Map<String,Integer> msgTip = new HashMap<>();//保存消息提示
}
