package com.ivan.communitymanagementsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ivan.communitymanagementsys.constant.RoleConstant;
import com.ivan.communitymanagementsys.constant.StatusConstant;
import com.ivan.communitymanagementsys.context.BaseContext;
import com.ivan.communitymanagementsys.dto.UserDTO;
import com.ivan.communitymanagementsys.dto.UserLoginDTO;
import com.ivan.communitymanagementsys.dto.UserPageQueryDTO;
import com.ivan.communitymanagementsys.entity.User;
import com.ivan.communitymanagementsys.exception.BaseException;
import com.ivan.communitymanagementsys.mapper.UserMapper;
import com.ivan.communitymanagementsys.result.PageResult;
import com.ivan.communitymanagementsys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

import static com.ivan.communitymanagementsys.constant.StatusConstant.DISABLE;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult getAllUser(UserPageQueryDTO userPageQueryDTO) {
        //开始分页查询
        //借用PageHelper方法对分页查询语句进行动态拼接
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.findAll(userPageQueryDTO);//后续定义
        long total = page.getTotal();
        List<User> userList= page.getResult();
        return new PageResult(total,userList);
    }

    @Override
    public void changeUserByself(User user) {

        if(user.getPassword()!=null && !user.getPassword().equals(""))
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));//将密码进行md5加密后存入数据库
        userMapper.update(user);
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        //1.根据用户名查询用户是否存在
        User user = userMapper.getByUsername(username);
        System.out.println(user);
        //2.比较用户输入密码是否正确等各种情况
        if(user==null){
//            throw new Exception("用户名不存在");
            log.info("用户名不存在");
            throw new BaseException("用户名不存在");
        }
        if(user.getStatus()==DISABLE){
            throw new BaseException("用户被冻结");
        }
        //密码对比
        //对前端传递的密码进行md5加密然后进行对比
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(user.getPassword())){
            throw new BaseException("密码错误");
        }

        //用户被锁定
        if(user.getStatus()== DISABLE){
            //用户已注销或被禁用
            throw new BaseException("用户被禁用");
        }

        //3.返回实体对象
        return user;
    }

    /**
     * 用户注册
     * @param userDTO
     */
    @Override
    public void register(UserDTO userDTO) {
        User user = new User();
        //将userDTO对象的属性拷贝到User对象
        BeanUtils.copyProperties(userDTO,user);//从方法前面的拷贝到方法后的对象（employeeDTO-->employee）
        user.setRoleId(RoleConstant.ORDINARY);//用户注册都是普通用户
        user.setStatus(StatusConstant.ENABLE);//用户状态为启用
        user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));//将密码进行md5加密后存入数据库
        userMapper.insert(user);
    }

    @Override
    public void logoff() {
        Long id = BaseContext.getCurrentId();
        Integer Status = DISABLE;
        userMapper.logoff(id,Status);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        User user = userMapper.getByUsername(username);
        return user;
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */

    @Override
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);
        return user;
    }

    /**
     * 根据用户id删除用户
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void addUser(User user) {
        //插入用户密码默认是123456
        user.setStatus(StatusConstant.ENABLE);//用户状态为启用
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));//将初始密码进行md5加密后存入数据库
        user.setStatus(StatusConstant.ENABLE);//用户状态为启用
        userMapper.insert(user);
    }

    @Override
    public void changeUserByOther(User user) {
        userMapper.updateUser(user);
    }


}
