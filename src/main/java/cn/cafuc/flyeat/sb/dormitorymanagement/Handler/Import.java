package cn.cafuc.flyeat.sb.dormitorymanagement.Handler;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;

import java.io.IOException;

public interface Import {
    public ResponseBean importInfo(String filePath) throws IOException;
}
