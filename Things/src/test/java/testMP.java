
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yw.entity.Lux;
import com.yw.mapper.LuxMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Demo Class
 *
 * @Class testMP
 * @Author Administrator
 * @Date 2020/6/11
 * @Version Code No.1
 * @Description:
 **/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class testMP {

    //private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    //private LuxMapper luxMapper = ioc.getBean("luxMapper",LuxMapper.class);

    @Autowired
    private LuxMapper luxMapper;


    @Test
    public void testDataSource() throws SQLException {
//        DataSource dataSource = ioc.getBean("dataSource",DataSource.class);
//        System.out.println(dataSource);
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
    }


    @Test
    public void testInsert(){
        //tcp://115.28.108.146:1883
        // 初始化Lux对象
        Lux lux = new Lux();
        lux.setLuxNumber("11");
        lux.setLuxTime(new java.util.Date());
        System.out.println("时间:"+new java.util.Date());
        System.out.println(luxMapper);
        System.out.println(lux);
        Integer integer = luxMapper.insertAllColumn(lux);
        System.out.println("返回值:" + integer);

    }

    @Test
    public void testEntityCustomer(){
        List<Lux> list = luxMapper.selectList(new EntityWrapper<Lux>()
                .like("lux_time","2020-06-18"));
        for (Lux lux : list){
            System.out.println(lux);
        }
    }






}
