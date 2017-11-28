package com.yuwubao.util;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangyu on 2017/11/16.
 */
public class ThinkTankUtil {

    /**
     * 获取本机ip
     * @return
     * @throws Exception
     */
    public static InetAddress getLocalHostLANAddress() throws Exception {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符串中字符出现的位置
     * @param string
     * @return
     */
    public static int getCharacterPosition(String string){
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile("/").matcher(string);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if(mIdx == 3){
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 数据库备份
     * @param path
     * @throws IOException
     */
    public static void backup(String path, String backupDateConfig) throws IOException {
        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec(backupDateConfig);
        InputStream inputStream = process.getInputStream();//得到输入流，写成.sql文件
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while((s = br.readLine()) != null){
            sb.append(s+"\r\n");
        }
        s = sb.toString();
        //System.out.println(s);
        File file = new File(path);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes());
        fileOutputStream.close();
        br.close();
        reader.close();
        inputStream.close();
    }

    /**
     * 数据库还原
     * @param path
     * @throws IOException
     */
    public static void recover(String path, String recoverDateConfig) throws IOException{
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(recoverDateConfig);
        OutputStream outputStream = process.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while((str = br.readLine()) != null){
            sb.append(str+"\r\n");
        }
        str = sb.toString();
        //System.out.println(str);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
        writer.write(str);
        writer.flush();
        outputStream.close();
        br.close();
        writer.close();
    }

    /**

     * 返回首字母

     * @param strChinese

     * @param bUpCase

     * @return

     */

    public static String getPYIndexStr(String strChinese, boolean bUpCase){

        try{

            StringBuffer buffer = new StringBuffer();

            byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组

            for(int i = 0; i < b.length; i++){

                if((b[i] & 255) > 128){

                    int char1 = b[i++] & 255;

                    char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方

                    int chart = char1 + (b[i] & 255);

                    buffer.append(getPYIndexChar((char)chart, bUpCase));

                    continue;

                }

                char c = (char)b[i];

                if(!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。

                    c = 'A';

                buffer.append(c);

            }

            return buffer.toString();

        }catch(Exception e){

            System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());

        }

        return null;

    }

    /**

     * 得到首字母

     * @param strChinese

     * @param bUpCase

     * @return

     */

    private static char getPYIndexChar(char strChinese, boolean bUpCase){

        int charGBK = strChinese;

        char result;

        if(charGBK >= 45217 && charGBK <= 45252)

            result = 'A';

        else

        if(charGBK >= 45253 && charGBK <= 45760)

            result = 'B';

        else

        if(charGBK >= 45761 && charGBK <= 46317)

            result = 'C';

        else

        if(charGBK >= 46318 && charGBK <= 46825)

            result = 'D';

        else

        if(charGBK >= 46826 && charGBK <= 47009)

            result = 'E';

        else

        if(charGBK >= 47010 && charGBK <= 47296)

            result = 'F';

        else

        if(charGBK >= 47297 && charGBK <= 47613)

            result = 'G';

        else

        if(charGBK >= 47614 && charGBK <= 48118)

            result = 'H';

        else

        if(charGBK >= 48119 && charGBK <= 49061)

            result = 'J';

        else

        if(charGBK >= 49062 && charGBK <= 49323)

            result = 'K';

        else

        if(charGBK >= 49324 && charGBK <= 49895)

            result = 'L';

        else

        if(charGBK >= 49896 && charGBK <= 50370)

            result = 'M';

        else

        if(charGBK >= 50371 && charGBK <= 50613)

            result = 'N';

        else

        if(charGBK >= 50614 && charGBK <= 50621)

            result = 'O';

        else

        if(charGBK >= 50622 && charGBK <= 50905)

            result = 'P';

        else

        if(charGBK >= 50906 && charGBK <= 51386)

            result = 'Q';

        else

        if(charGBK >= 51387 && charGBK <= 51445)

            result = 'R';

        else

        if(charGBK >= 51446 && charGBK <= 52217)

            result = 'S';

        else

        if(charGBK >= 52218 && charGBK <= 52697)

            result = 'T';

        else

        if(charGBK >= 52698 && charGBK <= 52979)

            result = 'W';

        else

        if(charGBK >= 52980 && charGBK <= 53688)

            result = 'X';

        else

        if(charGBK >= 53689 && charGBK <= 54480)

            result = 'Y';

        else

        if(charGBK >= 54481 && charGBK <= 55289)

            result = 'Z';

        else

            result = (char)(65 + (new Random()).nextInt(25));

        if(!bUpCase)

            result = Character.toLowerCase(result);

        return result;

    }


}
