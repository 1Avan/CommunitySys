package com.ivan.communitymanagementsys.controller;
import com.ivan.communitymanagementsys.constant.JwtClaimsConstant;
import com.ivan.communitymanagementsys.context.BaseContext;
import com.ivan.communitymanagementsys.dto.UserDTO;
import com.ivan.communitymanagementsys.dto.UserLoginDTO;
import com.ivan.communitymanagementsys.dto.UserPageQueryDTO;
import com.ivan.communitymanagementsys.entity.User;
import com.ivan.communitymanagementsys.properties.JwtProperties;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.service.UserService;
import com.ivan.communitymanagementsys.utils.JwtUtils;
import com.ivan.communitymanagementsys.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@Tag(name = "用户相关")
@RequestMapping("/cms/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}",userLoginDTO);
         User user = userService.login(userLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtils.genJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = new UserLoginVO().builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .roleId(user.getRoleId())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Result<Object> logout(){
        return Result.success();
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        log.info("用户注册，{}",userDTO);
        userService.register(userDTO);
        return Result.success();
    }




    /**
     * 根据用户名查询用用户
     * @param username
     * @return
     */
    @Operation(summary = "根据用户名查询用用户")
    @GetMapping("/userInfo")
    public Result<User> getUserByUserName(@RequestParam String username){
        log.info("查询用户信息，{}",username);
        User user = userService.getUserByUserName(username);
        return Result.success(user);
    }


    /**
     * 用户注销
     * @return
     */
    @Operation(summary = "用户注销")
    @PostMapping("/logoff")
    public Result logoff(){
        log.info("用户注销,{}",BaseContext.getCurrentId());
        userService.logoff();
        return Result.success();

    }


    /**
     * 用户修改个人信息
     * @return
     */
    @Operation(summary = "修改个人信息")
    @PutMapping
    public Result changeUserByself(@RequestBody User user){
        log.info("修改个人信息，{}",user);
        userService.changeUserByself(user);
        return Result.success();
    }

    @Operation(summary = "编辑用户信息")
    @PutMapping("/edit")
    public Result changeUserByOther(@RequestBody User user){
        log.info("修改用户信息，{}",user);
        userService.changeUserByOther(user);
        return Result.success();
    }



    /**
     * 分页查询所有用户
     * @return
     */
    @Operation(summary = "分页查询所有用户")
    @GetMapping("/all")
    public Result<PageResult> getAllUser( UserPageQueryDTO userPageQueryDTO){
        log.info("分页查询所有用户:{}",userPageQueryDTO);
        PageResult pageResult   = userService.getAllUser(userPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id){
        log.info("根据id查询用户信息:{}",id);
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @Operation(summary = "根据id删除用户")
    @DeleteMapping("/{id}")
    public Result deleteUserById(@PathVariable Long id){
        log.info("根据id删除用户{}",id);
        userService.deleteUserById(id);
        return Result.success();
    }

    /**
     * 管理员或社长添加用户
     * @param user
     * @return
     */

    @Operation(summary = "管理员或社长添加用户")
    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        log.info("管理员添加用户，{}",user);
        userService.addUser(user);
        return Result.success();
    }

}
