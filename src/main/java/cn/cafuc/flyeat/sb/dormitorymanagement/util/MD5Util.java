package cn.cafuc.flyeat.sb.dormitorymanagement.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {
    private static final String SALT = "flyeat";

    public static String md5(String password) {
        String hashAlgorithmName="MD5";
        ByteSource byteSalt=ByteSource.Util.bytes(SALT);
        Object source=password;
        int hashIterations=18;//赵十八专用加密次数
        SimpleHash result=new SimpleHash(hashAlgorithmName,source,byteSalt,hashIterations);

        return result.toString();
    }


}
