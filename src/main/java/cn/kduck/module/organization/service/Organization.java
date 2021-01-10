package cn.kduck.module.organization.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

public class Organization extends ValueMap {

    public static final String ROOT_ID = "-1";

    /**机构ID*/
    public static final String ORG_ID = "orgId";
    /**上级机构主键*/
    public static final String PARENT_ID = "parentId";
    /**机构名称*/
    public static final String ORG_NAME = "orgName";
    /**机构编码*/
    public static final String ORG_CODE = "orgCode";
    /**树路径*/
    public static final String DATA_PATH = "dataPath";
    /**排序*/
    public static final String ORDER_NUM = "orderNum";
    /**简称*/
    public static final String SHORT_NAME = "shortName";
    /**机构类型*/
    public static final String ORG_TYPE = "orgType";
    /**机构性质*/
    public static final String ORG_NATURE = "orgNature";
    /**创建时间*/
    public static final String CRT_TIME = "crtTime";


    public Organization() {
    }

    public Organization(Map valueMap) {
        super(valueMap);
    }

    /**
     * 设置 机构ID
     *
     * @param orgId 机构ID
     */
    public void setOrgId(String orgId) {
        super.setValue(ORG_ID, orgId);
    }

    /**
     * 获取 机构ID
     *
     * @return 机构ID
     */
    public String getOrgId() {
        return super.getValueAsString(ORG_ID);
    }

    /**
     * 设置 上级机构主键
     *
     * @param parentId 上级机构主键
     */
    public void setParentId(String parentId) {
        super.setValue(PARENT_ID, parentId);
    }

    /**
     * 获取 上级机构主键
     *
     * @return 上级机构主键
     */
    public String getParentId() {
        return super.getValueAsString(PARENT_ID);
    }

    /**
     * 设置 机构名称
     *
     * @param orgName 机构名称
     */
    public void setOrgName(String orgName) {
        super.setValue(ORG_NAME, orgName);
    }

    /**
     * 获取 机构名称
     *
     * @return 机构名称
     */
    public String getOrgName() {
        return super.getValueAsString(ORG_NAME);
    }

    /**
     * 设置 机构编码
     *
     * @param orgCode 机构编码
     */
    public void setOrgCode(String orgCode) {
        super.setValue(ORG_CODE, orgCode);
    }

    /**
     * 获取 机构编码
     *
     * @return 机构编码
     */
    public String getOrgCode() {
        return super.getValueAsString(ORG_CODE);
    }

    /**
     * 设置 树路径
     *
     * @param dataPath 树路径
     */
    public void setDataPath(String dataPath) {
        super.setValue(DATA_PATH, dataPath);
    }

    /**
     * 获取 树路径
     *
     * @return 树路径
     */
    public String getDataPath() {
        return super.getValueAsString(DATA_PATH);
    }

    /**
     * 设置 排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        super.setValue(ORDER_NUM, orderNum);
    }

    /**
     * 获取 排序
     *
     * @return 排序
     */
    public Integer getOrderNum() {
        return super.getValueAsInteger(ORDER_NUM);
    }

    /**
     * 设置 机构类型
     *
     * @param orgType 机构类型
     */
    public void setOrgType(String orgType) {
        super.setValue(ORG_TYPE, orgType);
    }

    /**
     * 获取 机构类型
     *
     * @return 机构类型
     */
    public String getOrgType() {
        return super.getValueAsString(ORG_TYPE);
    }

    /**
     * 设置 机构性质
     *
     * @param orgNature 机构性质
     */
    public void setOrgNature(String orgNature) {
        super.setValue(ORG_NATURE, orgNature);
    }

    /**
     * 获取 机构性质
     *
     * @return 机构性质
     */
    public String getOrgNature() {
        return super.getValueAsString(ORG_NATURE);
    }

    /**
     * 设置 创建时间
     *
     * @param crtTime 创建时间
     */
    public void setCrtTime(Date crtTime) {
        super.setValue(CRT_TIME, crtTime);
    }

    /**
     * 获取 创建时间
     *
     * @return 创建时间
     */
    public Date getCrtTime() {
        return super.getValueAsDate(CRT_TIME);
    }


    /**
     * 设置 简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        super.setValue(SHORT_NAME, shortName);
    }

    /**
     * 获取 简称
     *
     * @return 简称
     */
    public String getShortName() {
        return super.getValueAsString(SHORT_NAME);
    }
}
