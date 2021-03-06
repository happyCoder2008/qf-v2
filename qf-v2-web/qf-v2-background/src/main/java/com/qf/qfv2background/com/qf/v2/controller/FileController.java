package com.qf.qfv2background.com.qf.v2.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v2.common.pojo.MultiUploadResultBean;
import com.qf.v2.common.pojo.ResutlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author chenzetao
 * @Date 2019/12/22
 */
@Controller
@RequestMapping("file")
public class FileController {
    @Value("${image.server}")
    private String IMAGE_SERVER;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("upload")
    @ResponseBody
    public ResutlBean upload(MultipartFile file){
        System.out.println(file+"!!!!!!!");
        //得到文件真实名称
        String filename = file.getOriginalFilename();
        //截取文件后缀
        String suffixName = filename.substring(filename.lastIndexOf(".")+1);
        try {
            //获取到上传文件对象，把对象上传到FastDFS上
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffixName, null);
            //得到上传路径
            String fullPath = storePath.getFullPath();
            //把服务器上传的文件地址返回给客户端
            String path = new StringBuilder(IMAGE_SERVER).append(fullPath).toString();

            return ResutlBean.success(path);

        } catch (IOException e) {
            e.printStackTrace();
            return ResutlBean.error("网络有问题，请稍后再试!!");
        }
    }

    @RequestMapping("multiUpload")
    @ResponseBody
    public MultiUploadResultBean multiUpload(MultipartFile[] files){

        MultiUploadResultBean resultBean = new MultiUploadResultBean();
        //装路径的数组
        String[] data = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            //得到文件真实名称
            String filename = files[i].getOriginalFilename();
            //截取文件后缀
            String suffixName = filename.substring(filename.lastIndexOf(".")+1);
            try {
                //获取到上传文件对象，把对象上传到FastDFS上
                StorePath storePath = fastFileStorageClient.uploadFile(files[i].getInputStream(), files[i].getSize(), suffixName, null);
                //得到上传路径
                String fullPath = storePath.getFullPath();
                //把服务器上传的文件地址返回给客户端
                String path = new StringBuilder(IMAGE_SERVER).append(fullPath).toString();
                data[i] = path;
            } catch (IOException e) {
                e.printStackTrace();
                resultBean.setErrno("-1");
                return resultBean;
            }
        }
        //正常上传的情况
        resultBean.setErrno("0");
        resultBean.setData(data);
        return resultBean;
    }
}
