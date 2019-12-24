package com.qf.v2.common.pojo;

import java.io.Serializable;

/**
 * @Author chenzetao
 * @Date 2019/12/22
 */
public class MultiUploadResultBean implements Serializable {


    private String errno;
    private String[] data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
