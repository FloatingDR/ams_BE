package cn.cafuc.flyeat.sb.dormitorymanagement.util.MyShiro;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroRealm  extends AuthorizingRealm {

        @Autowired
        UserService userService;

        /*认证逻辑*/
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            UsernamePasswordToken token1=(UsernamePasswordToken)authenticationToken;
            User user = userService.selectByAccount(token1.getUsername());
            if (user == null) {
                throw new UnknownAccountException("用户名不存在!");
            }
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }

        /*
         * 授权逻辑
         * */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

            return null;
        }



}
