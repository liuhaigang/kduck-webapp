package cn.kduck.webapp.security;

import cn.kduck.core.utils.StringUtils;
import cn.kduck.core.web.GlobalErrorController;
import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.module.resource.service.ResourceOperate;
import cn.kduck.module.resource.service.ResourceService;
import cn.kduck.security.access.AbstractRoleAccessVoter;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.kduck.module.authorize.service.AuthorizeOperate.AUTHORIZE_TYPE_HIERARCHICAL;
import static cn.kduck.module.authorize.service.AuthorizeOperate.AUTHORIZE_TYPE_ROLE;
import static cn.kduck.module.authorize.service.AuthorizeOperate.AUTHORIZE_TYPE_USER;

/**
 * LiuHG
 */
@Component
public class CustomRoleAccessVoter extends AbstractRoleAccessVoter {

    private final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private ResourceService resourceService;

//    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    private HierarchicalAuthorizeService hierarchicalAuthorizeService;

    public CustomRoleAccessVoter(AccountService accountService){
        this.accountService = accountService;
    }

//    public CustomRoleAccessVoter(ResourceService resourceService,AuthorizeService authorizeService,AccountService accountService){
//        this.resourceService = resourceService;
//        this.authorizeService = authorizeService;
//        this.accountService = accountService;
//    }

    @Override
    public boolean checkAuthorize(Authentication authentication, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        if(method == null){
            method = "GET";
        }
        ResourceOperate resourceOperate = resourceService.getResourceOperate(requestUri, method);
        if(resourceOperate == null){
            return true;
        }

        String[] roleCodes = getRoleCodes(authentication);

        Account account = accountService.getAccountByName(authentication.getName());
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthorizeOperate(resourceOperate.getResourceId(), resourceOperate.getGroupCode(), resourceOperate.getOperateId());
        List<String> authorizeUserList = new ArrayList();
        for (AuthorizeOperate authorizeOperate : authorizeOperateList) {
            if(authorizeOperate.getAuthorizeType().intValue() == AUTHORIZE_TYPE_USER &&
                    account.getUserId().equals(authorizeOperate.getAuthorizeObject())){
                return true;
            }else if(authorizeOperate.getAuthorizeType().intValue() == AUTHORIZE_TYPE_ROLE &&
                    StringUtils.contain(roleCodes,authorizeOperate.getAuthorizeObject())){
                return true;
            }else if(authorizeOperate.getAuthorizeType().intValue() == AUTHORIZE_TYPE_HIERARCHICAL){
                authorizeUserList.add(authorizeOperate.getAuthorizeObject());
            }
        }

        if(!authorizeUserList.isEmpty()){
            AuthUser authUser = AuthUserHolder.getAuthUser();
            String orgId = request.getParameter("authOrgId");
            orgId = orgId == null ? authUser.getAuthOrgId() : orgId;
            if(orgId != null){
                //根据当前用户及机构查询是否有分级授权的权限
                boolean permit = hierarchicalAuthorizeService.existAuthorizeUser(account.getUserId(), orgId, authorizeUserList.toArray(new String[0]));
                if(permit){
                    return true;
                }
            }else{
                logger.warn("当前登录用户对象AuthUser中没有赋予机构orgId属性，在访问授权时无法进行分级授权的判断");
                request.setAttribute(GlobalErrorController.GLOBAL_ERROR_MESSAGE,resourceOperate.getOperateName());
                return false;
            }

        }
        request.setAttribute(GlobalErrorController.GLOBAL_ERROR_MESSAGE,resourceOperate.getOperateName());
        return false;
    }

    private String[] getRoleCodes(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        //根据当前登录用户所拥有的角色编码查询所拥有的操作权限。
        String[] roleCodes = new String[authorities.size()];
        int i = 0;
        for (GrantedAuthority authority : authorities) {
            roleCodes[i] = authority.getAuthority();
            i++;
        }
        return roleCodes;
    }

//    @Override
//    public List<ProtectedResource> listResourceOperateByCode(Authentication authentication) {
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        //根据当前登录用户所拥有的角色编码查询所拥有的操作权限。
//        String[] roleCodes = new String[authorities.size()];
//        int i = 0;
//        for (GrantedAuthority authority : authorities) {
//            roleCodes[i] = authority.getAuthority();
//            i++;
//        }
//
//        ValueMapList valueMapList = resourceService.listResourceAndOperateByRoleCode(roleCodes);
//        List<ProtectedResource> resourceList= new ArrayList(valueMapList.size());
//        for (ValueMap valueMap : valueMapList) {
//            String resourcePath = valueMap.getValueAsString("resourcePath");
//            String operatePath = valueMap.getValueAsString("operatePath");
//            String operateMethod = valueMap.getValueAsString("method");
//            resourceList.add(new ProtectedResource(PathUtils.appendPath(resourcePath, operatePath),operateMethod));
//        }
//        return resourceList;
//    }
//
//    @Override
//    public List<ProtectedResource> listAllResourceOperate() {
//        List<String> fullUriList = resourceService.listResourceUri();
//        List<ProtectedResource> allResOptList = new ArrayList(fullUriList.size());
//        for (String fullUri : fullUriList) {
//            String[] uriSplit = fullUri.split(":");
//            if(uriSplit.length < 2){
//                throw new RuntimeException("路径格式错误，正确格式=method:uri，path="+fullUri);
//            }
//            allResOptList.add(new ProtectedResource(uriSplit[1],uriSplit[0]));
//        }
//        return allResOptList;
//    }
}
