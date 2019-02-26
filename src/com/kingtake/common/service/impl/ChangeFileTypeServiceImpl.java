package com.kingtake.common.service.impl;

import java.io.File;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.service.ChangeFileTypeServiceI;

@Service("changeFileTypeServiceImpl")
@Transactional
public class ChangeFileTypeServiceImpl extends CommonServiceImpl implements ChangeFileTypeServiceI {

	@Override
	public void changeFileType(String path) {  
        File file = new File(path);  
        //获取所有目录下的文件、文件夹  
        File[] files = file.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isDirectory()) {  
            	changeFileType(files[i].getAbsolutePath());  
            } else {  
                String oldPath = files[i].getAbsolutePath();  
                //获取文件类型  
                String prefix = oldPath.substring(oldPath.lastIndexOf(".") + 1);  
                //需要替换的文件类型  
                String newPath = oldPath.replace(".java", ".text");  
                //指定复制替换的文件类型  
                if (prefix.equals("java")) {  
                    //copy(oldPath, newPath);  
                }  
                System.out.println(files[i].getAbsolutePath());  
            }  
        }  
    }


}