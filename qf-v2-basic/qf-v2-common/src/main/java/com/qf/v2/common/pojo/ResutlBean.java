package com.qf.v2.common.pojo;

/**
 * @Author chenzetao
 * @Date 2019/12/22
 */
public class ResutlBean {
    //状态码
    private Integer statusCode;
    //操作成功后返回的信息
    private String data;
    //操作失败后返回的信息
    private String msg;

    public static ResutlBean success(String data){
        ResutlBean resutlBean = new ResutlBean();
        resutlBean.setStatusCode(200);
        resutlBean.setData(data);
        return resutlBean;
    }

    public static ResutlBean error(String msg){
        ResutlBean resutlBean = new ResutlBean();
        resutlBean.setStatusCode(500);
        resutlBean.setMsg(msg);
        return resutlBean;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
