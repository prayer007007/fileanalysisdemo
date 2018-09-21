import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //如果文件过大，用synchronized保证IO未执行完的操作
        synchronized (Main.class) {
            String filePath = new File("").getAbsolutePath() + "/testfile.txt";
            File file = new File(filePath);
            BufferedReader reader = null;
            Map<String, Double> resultMap = new HashMap<>();
            Map<String, Double> resultMapNew = new HashMap<>();
            Map<Long,String> problemMapStr = new HashMap<>();
            long problemMapCount = 0;
            double countAll = 0;
            try {
                //以行为单位读取文件内容，一次读一整行
                reader = new BufferedReader(new FileReader(file));
                String tempStr = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempStr = reader.readLine()) != null) {
                    // 显示行号
//                System.out.println("line " + line + ": " + tempStr);
//                line++;
                    //如果是按照规范的tab键划分就用这个
                    String[] tempArray;
                    try {
                        tempArray = tempStr.split("\\t+");
                    } catch (Exception e) {
                        //如果是按照不规范的tab键划分用这个
                        try {
                            tempArray = tempStr.split("\\t*\\s+");
                        } catch (Exception e1) {
                            //如果还是出错，说明文件内容格式有问题
                            System.out.println("文件内容格式有问题");
                            //记录出错的一行
                            problemMapStr.put(++problemMapCount,tempStr);
                            //如果格式有误就退出，不跳过错误的话
                            break;
                        }
                    }
                    double tempCount = Double.parseDouble(tempArray[1]);
                    resultMap.put(tempArray[0], tempCount);
                    countAll += tempCount;
//                  System.out.println(tempStr);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
            if (countAll > 0) {
                //容量大时遍历
                for (Map.Entry<String, Double> entry : resultMap.entrySet()) {
                    resultMapNew.put(entry.getKey(), entry.getValue() / countAll);
                    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                }
            } else {
                resultMapNew = resultMap;
            }
            System.out.println("计算之后的结果为：");
            for(Map.Entry<String,Double> entryNew : resultMapNew.entrySet()){
                System.out.println(entryNew.getKey() + " : " + entryNew.getValue());
            }
            System.out.println("Done!");
        }
    }
}
