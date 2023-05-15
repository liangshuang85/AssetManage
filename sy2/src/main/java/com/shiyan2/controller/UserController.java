package com.shiyan2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyan2.common.QueryPageParam;
import com.shiyan2.common.Result;
import com.shiyan2.entity.Menu;
import com.shiyan2.entity.User;
import com.shiyan2.service.MenuService;
import com.shiyan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 梁爽
 * @since 2023-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //新增
    @PostMapping("/save")
     public Result save(@RequestBody User user){

        return userService.save(user)?Result .suc():Result.fail();
     }

    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }
    //删除
    @PostMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }
    //查询
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(user.getName());
    }
    //新增或修改

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //分页
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam queryPageParam) {
       // return userService.saveOrUpdate(user);

       /* System.out.println(queryPageParam);
        System.out.println("num===>"+queryPageParam.getPageNum());
        System.out.println("size===>"+queryPageParam.getPageSize());
        */
        HashMap param = queryPageParam.getParam();
        String name =(String)param.get("name");

  /*      System.out.println("ac===>"+(String)param.get("ac") );
        System.out.println("name===>"+(String)param.get("name") );*/

        Page<User> page = new Page();

        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total===>"+result.getTotal());

        return  result.getRecords();
    }

    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam queryPageParam) {

        HashMap param = queryPageParam.getParam();
        String name =(String)param.get("name");
        String role =(String)param.get("role");

        Page<User> page = new Page();

        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name)&&!"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }

        if(StringUtils.isNotBlank(role)){
            lambdaQueryWrapper.like(User::getRole,role);
        }



        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total===>"+result.getTotal());

        return  Result.suc(result.getRecords(), result.getTotal());
    }

    //登录页面
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list =userService.lambdaQuery()
                .eq(User::getAc,user.getAc())
                .eq(User::getPassword,user.getPassword()).list();

        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRole()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.suc(res);
        }
        return Result.fail();
    }



}
