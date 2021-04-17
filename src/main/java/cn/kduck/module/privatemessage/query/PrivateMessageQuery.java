package cn.kduck.module.privatemessage.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import cn.kduck.module.privatemessage.service.MessageUser;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.privatemessage.service.PrivateMessageService.CODE_MESSAGE_USER;
import static cn.kduck.module.privatemessage.service.PrivateMessageService.CODE_PRIVATE_MESSAGE;

@Component
public class PrivateMessageQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        if(paramMap.containsKey("userId")){
            return createUserMessageQuery(paramMap,depository);
        }else{
            return createMessageQuery(paramMap,depository);
        }
    }

    private QuerySupport createUserMessageQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef userEntityDef = depository.getEntityDef(CODE_MESSAGE_USER);
        BeanEntityDef messageEntityDef = depository.getEntityDef(CODE_PRIVATE_MESSAGE);

        paramMap.put("isDelete",MessageUser.IS_DELETE_NO);

        SelectBuilder selectBuilder = new SelectBuilder(paramMap);
        selectBuilder.bindFields("m",messageEntityDef.getFieldList())
                .bindFields("u", BeanDefUtils.includeField(userEntityDef.getFieldList(),"isRead"));

        selectBuilder.from("m",messageEntityDef).innerJoinOn("u",userEntityDef,"messageId").where()
                .and("m.MESSAGE_ID", ConditionType.EQUALS,"messageId")
                .and("u.USER_ID", ConditionType.EQUALS,"userId")
                .and("u.IS_DELETE",ConditionType.EQUALS,"isDelete")
                .orderBy().asc("u.IS_READ").desc("m.SEND_DATE");

        return selectBuilder.build();
    }

    private QuerySupport createMessageQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(CODE_PRIVATE_MESSAGE);
        SelectBuilder selectBuilder = new SelectBuilder(entityDef);
        selectBuilder.where().and("MESSATE_ID", ConditionType.EQUALS,"messageId");
        return selectBuilder.build();
    }
}
