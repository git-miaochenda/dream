package dream.test;

import com.dream.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class TestFreemarker {
    @Test
    public void testFreemarkDemo() throws IOException, TemplateException {
        //创建一个configuration对象，用来设置模板信息，构造方法中有一个是freemarker的版本号的参数
        Configuration configuration = new Configuration(Configuration.getVersion());
        //通过Configuration来得到一个模板，通过它来读取和设置模板信息
        //2、设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File
                ("D:\\JAVA\\IEDA2019\\dream\\dream_item_web\\src\\test\\resources"));
        //3、设置模板的字符编码
        configuration.setDefaultEncoding("UTF-8");
        //4、加载模板文件
        Template template = configuration.getTemplate("demo.ftl");
        //5、设置模板输出的目录以及输出的文件名（目录必须是已经存在的）
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\temp\\demo.html"));
        //绑定数据
        HashMap data = new HashMap<>();
        //绑定string
        data.put("msg","这是freemarker测试绑定数据");
        //绑定指定的pojo对象，例如TbItem
        TbItem tbItem1 = new TbItem();
        tbItem1.setTitle("测试freemarker绑定对象1");
        TbItem tbItem2=new TbItem();
        tbItem2.setTitle("测试freemarker绑定的对象2");
        TbItem tbItem3=new TbItem();
        tbItem3.setTitle("测试freemarker绑定对象3");

        List<Object> items = new ArrayList<>();
        items.add(tbItem1);
        items.add(tbItem2);
        items.add(tbItem3);
        data.put("items",items);

        Map<String,TbItem> itemMap = new HashMap<>();
        itemMap.put("first",tbItem1);
        itemMap.put("second",tbItem2);
        itemMap.put("third",tbItem3);
        data.put("itemMap",itemMap);

        data.put("tbItem",tbItem1);
        data.put("date",new Date());
        //6、生成文件
        template.process(data,fileWriter);
        //7、关闭流
        fileWriter.close();
    }
    @Test
    public void testSpringFreemarker() throws IOException, TemplateException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/springmvc.xml");
        FreeMarkerConfigurer freeMarkerConfigurer = ctx.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("demo.ftl");
        //设置模板输出的目录以及输出的文件名（目录必须是存在的）
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\temp\\demo.html"));
        //绑定数据
        HashMap data = new HashMap<>();
        //绑定String
        data.put("msg","这是spring-freemarker测试绑定数据");
        //绑定指定的pojo对象，例如TbItem
        TbItem tbItem1=new TbItem();
        tbItem1.setTitle("测试freemarker绑定对象1");
        TbItem tbItem2 = new TbItem();
        tbItem2.setTitle("测试freemarker绑定对象2");
        TbItem tbItem3 = new TbItem();
        tbItem3.setTitle("测试freemarker绑定对象3");

        List<Object> items = new ArrayList<>();
        items.add(tbItem1);
        items.add(tbItem2);
        items.add(tbItem3);
        data.put("items",items);

        Map<String, TbItem> itemMap = new HashMap<>();
        itemMap.put("first",tbItem1);
        itemMap.put("second",tbItem2);
        itemMap.put("third",tbItem3);
        data.put("itemMap",itemMap);

        data.put("tbItem",tbItem1);
        data.put("data",new Date());

        //生成文件--第一个参数data是绑定数据
        template.process(data,fileWriter);
        //关闭流
        fileWriter.close();

    }
}
