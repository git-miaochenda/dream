package com.dream.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class TestFreemarkerController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

//    @RequestMapping("/test/fm")
//    @ResponseBody
    public String fm() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("demo.ftl");
        //设置模板输出的目录以及输出的文件名（目录必须是存在的）
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\temp\\demo.html"));
        //绑定数据
        HashMap data = new HashMap<>();
        //绑定String
        data.put("msg","这是spring-freemarker测试绑定数据");
        //生成文件--第一个参数data是绑定的数据
        template.process(data,fileWriter);
        //关闭流
        fileWriter.close();
        return "OK";

    }

}
