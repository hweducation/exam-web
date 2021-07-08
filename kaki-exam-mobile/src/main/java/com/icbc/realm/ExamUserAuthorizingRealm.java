package com.icbc.realm;

import com.icbc.pojo.ExamUser;
import com.icbc.service.ExamUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SellerAuthorizingReleam
 *
 * @author kaki
 * @version 1.0
 * @desc  shiro自定义认证域
 * <p>File Created at 2020-01-25<p>
 */
public class ExamUserAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private ExamUserService examUserService;

    /** 授权方法 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /** 身份认证方法 subject.login() */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        try{
            System.out.println("examUserService = " + examUserService);
            // 1. 获取登录用户名
            String userId = token.getPrincipal().toString();


            System.out.println("userId = " + userId);

            // 2. 根据sellerId查询一个Seller
            ExamUser examUser = examUserService.findOne(userId);

            // 3. 判断商家
            if (examUser != null){
                // 密码交给Shiro它判断
                // 第三个参数为盐，注意：区分传过来的盐是否是加密过得盐，这里需要未加密的盐，即：注册时的盐，为了敏捷开发，就不创建盐库了
                return new SimpleAuthenticationInfo(examUser,
                        examUser.getPassword(),
                        ByteSource.Util.bytes(examUser.getUsername()),
                        super.getName());
            }
            return null;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
