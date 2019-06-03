package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/*
* 为达到复用，从basicImport中剥离出来。
* */
public class Tools {
    //浮点数转String只保留整数位
    public static String change2(Double e){
        DecimalFormat decimalFormat=new DecimalFormat("#");
        return decimalFormat.format(e);
    }
    public static String handleDouble(String s){
        if(s==null)return null;
        if(s.indexOf(".")!=-1){
            s=change2(new Double(s));
        }
        s=s.replace(" ","");
        return s==""?null:s;
    }
    public static String buildHandle(String build){
        if(build==null||build=="")return null;
        build=build.trim().replace("栋","");
        build=build.toUpperCase();
        if(build.matches("\\d+")){
            int num= Integer.parseInt(build);
            if(4<=num&&num<=7){
                char letter=(char)('A'+(num-4));
                build=String.valueOf(letter);
                return build;
            }else if(8==num){
                return "I";
            }else if(9==num){
                return "H";
            }else if(10==num){
                return "J";
            }else if(11==num||12==num||(17<=num&&num<=19)){
                return String.valueOf(num);
            }
            else if(13<=num&&num<=15){
                char letter=(char)('E'+(num-13));
                build=String.valueOf(letter);
                return build;
            }else if(num==100){
                return "K";
            }else if(num==111){
                return "L";
            }else if(num==109){
                return "N";
            }else if(num==112){
                return "O";
            }else{
                return null;
            }
        }
        return build;
    }
    public static boolean judgeName(String name){
        if(name==null)return false;
        if(name.replaceAll(" ","").length()==0)return false;
        for(int i=0;i<name.length();i++){
            char t=name.charAt(i);
            if(i>='0'&&i<='9')return false;
            if(t=='!'||t=='$'||t=='@'||t=='~'||t=='%'||t=='^'||t=='&'||t=='('||t==')')return false;
        }
        return true;
    }
    public static  String IDP(String s) {
        if(s==null) return null;
        s = s.toUpperCase().replace(" ","");
        String str = "";
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c >= '0' && c <= '9') {
                str += c;
            }
            if(i == s.length() - 1 && c == 'X') {
                str += c;
            }
        }
        if(str.length() == 18) return str;
        else return null;
    }
    public  static String floorHandle(String floorNum){
        if(floorNum==null)return null;
        floorNum=floorNum.replaceAll(" ","").replace("层","").replace("楼","");
        floorNum=floorNum.replaceAll("[a-zA-Z]","");
        if(floorNum.length()==0)return null;
        return floorNum;
    }
    public static  String roomHandle(String roomNum){
        if(roomNum==null)return null;
        roomNum=roomNum.replaceAll(" ","").toUpperCase();
        char firstChar=roomNum.charAt(0);
        if(firstChar>='A'&&firstChar<='Z'){
            roomNum=roomNum.substring(1,roomNum.length());
        }
        return roomNum;
    }
    public static String bunkHandle(String bunkNum){
        if(bunkNum==null)return null;
        bunkNum=bunkNum.replaceAll(" ","").replaceAll("床","");
        bunkNum=bunkNum.replaceAll("[a-zA-Z]","");
        if(bunkNum.length()==0)return null;
        return bunkNum;
    }
}
