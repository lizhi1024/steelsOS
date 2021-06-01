package com.lzos.steels.admin.controller;

import com.lzos.steels.admin.entity.dto.User;
import com.lzos.steels.admin.exception.BizException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @PostMapping("/user")
    public boolean insert(@RequestBody User user) {
        System.out.println("开始新增...");
        //如果姓名为空就手动抛出一个自定义的异常！
        if(user.getName() == null){
            throw new BizException("-1","用户姓名不能为空！");
        }
        return true;
    }

    @PutMapping("/user")
    public boolean update(@RequestBody User user) {
        System.out.println("开始更新...");
        //这里故意造成一个空指针的异常，并且不进行处理
        String str = null;
        str.equals("111");
        return true;
    }

    @DeleteMapping("/user")
    public boolean delete(@RequestBody User user)  {
        System.out.println("开始删除...");
        //这里故意造成一个异常，并且不进行处理
        Integer.parseInt("abc123");
        return true;
    }

    @GetMapping("/user")
    public List<User> findByUser(User user) {
        System.out.println("开始查询...");
        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setId(1);
        user2.setName("lizhi");
        user2.setAge(18);
        userList.add(user2);
        return userList;
    }

}
