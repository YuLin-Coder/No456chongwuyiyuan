package com.forest.demo.Controller;

import com.forest.demo.Entity.Forest_CustomerEntity;
import com.forest.demo.Entity.Forest_Customer_PetsEntity;
import com.forest.demo.Entity.Forest_VetDoctorEntity;
import com.forest.demo.Service.Forest_CustomerService;
import com.forest.demo.Service.Forest_Customer_PetsService;
import com.forest.demo.Service.Forest_VetDoctorService;
import global.Forest_variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class Forest_CustomerController {
    //使用map存储数据
    private Map<String, Object> map = new HashMap<>();
    //数据页数和起始值
    private int count, num;
    //映射
    @Autowired
    private Forest_CustomerService forest_customerService;
    @Autowired
    private Forest_Customer_PetsService forest_customer_petsService;
    /**
     * insertSelective
     * 首次加载页面
     */
    @RequestMapping("/ForestCustomerEntitySelect")
    public Map<String, Object> ForestCustomerEntitySelect(@RequestBody Forest_CustomerEntity model, HttpServletRequest request) {
        //求出统计的数据
        num = count(model);
        //判断是否登录
        if (new Forest_variable().variableNameSession(request) == 500) {
            map.put("code", 500);
            return map;
        }
        //判断是否是首页
        if (model.getNum() != 0) {
            //若不是首页，则获取起始值
            int ss = model.getNum() * model.getSize();
            //存储起始值
            model.setNum(ss);
        }
        //查询的起始页
        List<Forest_CustomerEntity> list = forest_customerService.ForestCustomerEntitySelect(model);
        //存储xiaoc的登录名
        map.put("name", new Forest_variable().sessionName(request));
        //存储的数据
        map.put("customer", list);
        //存储页数
        map.put("num", num);
        //状态码 200
        map.put("code", 200);
        return map;
    }

    /**
     * 求出统计的数据
     */
    public int count(Forest_CustomerEntity model) {
        //查询的统计数据
        count = forest_customerService.ForestCustomerEntityRow();
        //判断求出页数
        if (count % model.getSize() == 0) {
            num = count / model.getSize();
        } else {
            num = count / model.getSize() + 1;
        }
        //如果信息大于8页  只显示8页
        if (num >= 8) {
            return 8;
        }
        return num;
    }
    //搜索数据
    @RequestMapping("/ForestCustomerEntitySearch")
    public Map<String, Object> ForestCustomerEntitySearch(@RequestBody Forest_CustomerEntity model, HttpServletRequest request) {
        //求出统计的数据
        num = count1(model);
        //判断是否登录
        if (new Forest_variable().variableNameSession(request) == 500) {
            map.put("code", 500);
            return map;
        }
        //判断是否是首页
        if (model.getNum() != 0) {
            //若不是首页，则获取起始值
            int ss = model.getNum() * model.getSize();
            //存储起始值
            model.setNum(ss);
        }
        //查询的起始页
        List<Forest_CustomerEntity> list = forest_customerService.ForestCustomerEntitySearch(model);
        //存储xiaoc的登录名
        map.put("name", new Forest_variable().sessionName(request));
        //存储的数据
        map.put("customer", list);
        //存储页数
        map.put("num", num);
        //状态码 200
        map.put("code", 200);
        return map;
    }

    /**
     * 求出统计的数据
     */
    public int count1(Forest_CustomerEntity model) {
        //查询的统计数据
        count = forest_customerService.ForestCustomerEntitySearchRow(model);
        //判断求出页数
        if (count % model.getSize() == 0) {
            num = count / model.getSize();
        } else {
            num = count / model.getSize() + 1;
        }
        //如果信息大于8页  只显示8页
        if (num >= 8) {
            return 8;
        }
        return num;
    }
    //客户详情
    @PostMapping("/ForestCustomerEntityDetails")
    public Map<String, Object> ForestCustomerEntityDetails(@RequestBody Forest_CustomerEntity model, HttpServletRequest request) {
        //判断是否登录
        if (new Forest_variable().variableNameSession(request) == 500) {
            map.put("code", 500);
            return map;
        }
        List<Forest_CustomerEntity> list = forest_customerService.ForestCustomerEntityDetails(model);
        if(list.size()==0)
        {
            //状态码 200
            map.put("code", 400);
            return map;
        }
        //存储的数据
        map.put("customer", list);
        //状态码 200
        map.put("code", 200);
        return map;
    }
    //新增数据
    @PostMapping("/ForestCustomerEntityRegister")
    public  Map<String,Object> ForestCustomerEntityRegister(@RequestBody Forest_CustomerEntity forest_customerEntity){
        //设置时间的格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取时间的方法
        Date d = new Date();
        //将获取的时间转换成设置的时间格式进行存储
        forest_customerEntity.setForest_Customer_Time(sf.format(d));
        int register=forest_customerService.ForestCustomerEntityRegister(forest_customerEntity);
        if(register==1){
            map.put("code",200);
            return map;
        }
        map.put("code",500);
        return map;
    }
    //编号详情
    @PostMapping("/ForestCustomerEntityID")
    public Map<String, Object> ForestCustomerEntityID(@RequestBody Forest_CustomerEntity model, HttpServletRequest request) {
        //判断是否登录
        if (new Forest_variable().variableNameSession(request) == 500) {
            map.put("code", 500);
            return map;
        }
        List<Forest_CustomerEntity> list = forest_customerService.ForestCustomerEntityID(model);
        //存储的数据
        map.put("customer", list);
        //状态码 200
        map.put("code", 200);
        return map;
    }
    //更改数据
    @PostMapping("/ForestCustomerEntityUpdate")
    public Map<String, Object> ForestCustomerEntityUpdate(@RequestBody Forest_CustomerEntity model) {
        //设置时间的格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取时间的方法
        Date d = new Date();
        //将获取的时间转换成设置的时间格式进行存储
        model.setForest_Customer_Time(sf.format(d));
        int list = forest_customerService.ForestCustomerEntityUpdate(model);
        if(list==1){
            map.put("code", 200);
            return map;
        }
        //状态码 200
        map.put("code", 400);
        return map;
    }
    //客户详情
    @PostMapping("/ForestCustomerPetsEntitySearchCustomerAll")
    public Map<String, Object> ForestCustomerPetsEntitySearchCustomerAll(@RequestBody Forest_Customer_PetsEntity model) {
        System.out.println(model.getForest_Customer_ID());
        List<Forest_Customer_PetsEntity> list =forest_customer_petsService.ForestCustomerPetsEntitySearchCustomerAll(model);
       if(list.size()==0){
           //状态码 200
           map.put("code", 500);
           return map;
       }
        //存储的数据
        map.put("customerPet", list);
        //状态码 200
        map.put("code", 200);
        return map;
    }
}
