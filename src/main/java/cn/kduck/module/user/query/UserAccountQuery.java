package cn.kduck.module.user.query;

import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import cn.kduck.core.utils.ValueMapUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author LiuHG
 */
@Component
public class UserAccountQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef userDef = depository.getEntityDef(UserService.CODE_USER);
        BeanEntityDef accountDef = depository.getEntityDef(AccountService.CODE_ACCOUNT);
        SelectBuilder sqlBuilder = new SelectBuilder(paramMap);
        sqlBuilder.bindFields("u",depository.getFieldDefList(UserService.CODE_USER))
                .bindFields("a", BeanDefUtils.includeField(depository.getFieldDefList(AccountService.CODE_ACCOUNT), Account.ACCOUNT_ID,Account.ACCOUNT_NAME,Account.ACCOUNT_STATE,Account.EXPIRED_DATE));
        sqlBuilder.from("u", userDef).leftJoin("a", accountDef)
                .where()
                .and("u.USER_NAME", ConditionType.CONTAINS,"userName")
                .and("u.GENDER",ConditionType.EQUALS,"gender");

        Integer accountState = ValueMapUtils.getValueAsInteger(paramMap, "accountState");
        if(accountState != null){
            if(accountState.intValue() == Account.ACCOUNT_STATE_EXPIRED){
                sqlBuilder.get().and("a.EXPIRED_DATE",ConditionType.IS_NOT_NULL);
            }else{
                sqlBuilder.get().and("a.ACCOUNT_STATE",ConditionType.EQUALS,"accountState")
                        .and("a.EXPIRED_DATE",ConditionType.IS_NULL);
            }
        }
        QuerySupport query = sqlBuilder.build();

        Date date = new Date();
        query.addValueFormatter("accountState",(Object value, Map<String,Object> valueMap)->{
            Date expiredDate = ValueMapUtils.getValueAsDate(valueMap, "expiredDate");
            if(expiredDate != null && expiredDate.before(date)){
                return Account.ACCOUNT_STATE_EXPIRED;
            }else{
                return value;
            }
        });
        return query;
    }
}
