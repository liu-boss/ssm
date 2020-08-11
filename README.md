# ssm
自用的ssm的web集成环境,开箱即用。

1. Spring . SpringMVC . Mybatis


application-dao.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    <!--配置包扫描-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--配置数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${user}"/>
        <property name="password" value="${password}"/>
        <property name="url" value="${url}"/>
        <property name="driverClassName" value="${driverClass}"/>
        <!-- 配置初始化大小、最小、最大连连接数量 -->
        <property name="initialSize" value="5"/>
        <property name="minIdle" value="10"/>
        <property name="maxActive" value="20"/>

        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="60000"/>

        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="2000"/>

        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="600000"/>

        <!--validationQuery 用来检测连接是否有效的 sql，要求是一个查询语句，常用 select 'x'。
            但是在 oracle 数据库下需要写成 select 'x' from dual 不然实例化数据源的时候就会失败,
            这是由于 oracle 和 mysql 语法间的差异造成的-->
        <property name="validationQuery" value="select 'x'"/>
        <!--建议配置为 true，不影响性能，并且保证安全性。申请连接的时候检测，
        如果空闲时间大于 timeBetweenEvictionRunsMillis，执行 validationQuery 检测连接是否有效。-->
        <property name="testWhileIdle" value="true"/>
        <!--申请连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。-->
        <property name="testOnBorrow" value="false"/>
        <!--归还连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。-->
        <property name="testOnReturn" value="false"/>

        <!-- 配置监控统计拦截的 filters Druid 连接池的监控信息主要是通过 StatFilter 采集的，
        采集的信息非常全面，包括 SQL 执行、并发、慢查、执行时间区间分布等-->
        <property name="filters" value="stat"/>
    </bean>

    <!--配置mybatis的SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis/mybatis-config.xml"/>
        <!--配置mybatis扫面的实体类的包-->
        <property name="typeAliasesPackage" value="com.coderman.model"/>
        <!--   扫描sql配置文件：mapper需要的xml文件-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

  
    <!-- DAO接口所在包名，Spring会自动查找其下的类 -->
    <bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.coderman.mapper"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="properties">
            <value>
                <!-- 通用mapper -->
                mappers=tk.mybatis.mapper.common.Mapper
            </value>
        </property>
    </bean>

</beans>

```


application-service.xml

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context-2.5.xsd
           http://www.springframework.org/schema/tx
           http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd"
>
    <!--扫描service所有使用注解的类型-->
    <context:component-scan base-package="com.coderman.service"/>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--配置基于注解的生命式事务-->
    <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true" />


</beans>

```

application-web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/context/spring-aop.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--扫描web相关的bean-->
    <context:component-scan base-package="com.coderman.web"/>


    <!--配置注解驱动的配置-->
    <mvc:annotation-driven />

    <!--
        DispatcherServlet请求映射配置为"/"，则Spring MVC将捕获Web容器所有的请求，包括静态资源的请求，
        Spring MVC会将它们当成一个普通请求处理，因此找不到对应处理器将导致错误。
        如果没有作映射，就交给 WEB 应用服务器默认的 Servlet 处理，从而找到对应的静态资源，只有再找不到资源时才会报错。
        -->
    <mvc:default-servlet-handler/>

    <!-- 配置视图  BeanNameViewResolver 解析器: 使用视图的名字来解析视图:(在异常处理中使用的: ModelAndView("jsonView", result.toMap());) -->
    <bean class="org.springframework.web.servlet.view.BeanNameViewResolver" />


    <!--自己的异常处理器-->
    <bean class="com.coderman.common.SpringExceptionResolver" />

    <!--json的视图解析器-->
    <bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />

    <!--jsp视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

4. 通用Mapper

```xml

 <!--配置通用mapper-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">-->
 <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>-->
    <!--给出需要扫描Dao接口包-->
   <property name="basePackage" value="com.coderman.mapper"/>-->
  </bean>
```

5. Jsp

6. Jstl

7. 分页插件

8. Druid数据源

9. Slf4j+Logback

10. 全局异常处理 (Ajax+页面)
```java
package com.coderman.common;

import com.coderman.exception.BizException;
import com.coderman.exception.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SpringExceptionResolver implements HandlerExceptionResolver {

    private Logger log= LoggerFactory.getLogger(SpringExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "系统异常,请联系管理员";

        if (isAjax(request)) {
            log.error("ajax请求出现异常:" + url, ex);
            if (ex instanceof BizException) {
                log.error("业务异常, url:" + url, ex);
                JsonData result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else if(ex instanceof ParamException){
                log.error("参数异常, url:" + url, ex);
                JsonData result = JsonData.fail(JsonData.PARAM_ERROR,ex.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else {//系统异常
                log.error("系统异常, url:" + url, ex);
                JsonData result = JsonData.fail(JsonData.INTERNAL_SERVER_ERROR,defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else {
            log.error("页面请求出现异常:" + url, ex);
            Map<String,Object> map=new HashMap<>();
            map.put("errorMsg",ex.toString());
            mv = new ModelAndView("error/exception",map);
        }
        return mv;
    }

    /**
     * 判断网络请求是否为ajax
     *
     * @param req
     * @return
     */
    private boolean isAjax(HttpServletRequest req) {
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }
}

```
11. 封装通用数据返回对象
12. hibernate-validator 数据校验

```java

package com.coderman.util;

import com.coderman.exception.ParamException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap<String,String> errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation)iterator.next();
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map<String,String> errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object);
        } while (errors.isEmpty());

        return errors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first);
        }
    }

    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (!CollectionUtils.isEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}

```

13. Mybatis 逆向生成

```java
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/7/6 11:13
 * @Version 1.0
 **/
public class MybatisGenerator {


    public static void main(String[] args) throws Exception {
        Logger logger=LoggerFactory.getLogger(MybatisGenerator.class);
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream inputStream = MybatisGenerator.class.getResourceAsStream("mybatis/mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
        logger.info("generator success");
    }
}

```
14. MD5工具类


15. Spring 测试模块

```java
package com.coderman.service;

import com.coderman.mapper.UserMapper;
import com.coderman.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/11 09:40
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-dao.xml","classpath:spring/application-service.xml"})
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void listAll() {
        List<User> users = userMapper.listAll();
        System.out.println(users);
    }

    @Test
    public void listAll2(){
        List<User> users = userService.listAll();
        System.out.println(users);
    }
}

```
......
