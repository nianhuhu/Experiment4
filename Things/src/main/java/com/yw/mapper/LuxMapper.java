package com.yw.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yw.entity.Lux;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Demo Class
 *
 * @Class EmployeeMapper
 * @Author Administrator
 * @Date 2020/5/19
 * @Version Code No.1
 * @Description:
 *      基于MyBatis   在Mapper接口中编写CRUD相关的方法，提供Mapper接口对应的SQL映射文件以及方法对应的SQL语句
 *      基于MP(MyBatis-Plus)  让xxMapper接口继承BaseMapper接口即可
 *      BaseMapper<T> 泛型指定的就是当前Mapper接口所操作的实体类类型
 **/
@Component
@Mapper
public interface LuxMapper extends BaseMapper<Lux> {

}
