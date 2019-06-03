package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.UserService;
import cn.cafuc.flyeat.sb.dormitorymanagement.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthContoller {
    @Autowired
    UserService userService;

   @PostMapping("/login")
   public ResponseBean Login(String account,String password){

        Subject currUser= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(account, MD5Util.md5(password));
        try {
            currUser.login(token);
        }catch (UnknownAccountException e){
            return new ResponseBean(401,"no this user",null);

        }catch (IncorrectCredentialsException e){
            return new ResponseBean(403,"password error",null);
        }
        return new ResponseBean(200,"success",null);
    }



    @GetMapping("/test")
    public String test(){
        return "build successs";
    }


    @PostMapping("/changepass")
    public ResponseBean changePassword(String newPassword){
        Subject currUser= SecurityUtils.getSubject();
        User user=(User)currUser.getPrincipals().getPrimaryPrincipal();
        userService.changePass(user.getAccount(),MD5Util.md5(newPassword));
        currUser.logout();
        return new ResponseBean(200,"success",null);
    }
}
