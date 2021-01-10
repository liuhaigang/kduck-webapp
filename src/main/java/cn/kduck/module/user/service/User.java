package cn.kduck.module.user.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

/**
 * LiuHG
 */
public class User extends ValueMap {

    /**用户ID*/
    private static final String USER_ID = "userId";
    /**姓名*/
    private static final String USER_NAME = "userName";
    /**性别*/
    private static final String GENDER = "gender";
    /**出生日期*/
    private static final String BIRTHDAY = "birthday";
    /**手机号*/
    private static final String PHONE = "phone";
    /**邮箱*/
    private static final String EMAIL = "email";
    /**用户编码*/
    private static final String USER_CODE = "userCode";
    /**证件类型*/
    private static final String ID_TYPE = "idType";
    /**证件号*/
    private static final String ID_CARD_NUM = "idCardNum";
    /**政治面貌*/
    private static final String POLITICAL = "political";
    /**婚姻状况*/
    private static final String MARITAL_STATE = "maritalState";
    /**国籍*/
    private static final String NATIONALITY = "nationality";
    /**民族*/
    private static final String NATION = "nation";
    /**籍贯*/
    private static final String NATIVE_PLACE = "nativePlace";


    public User() {}

    public User(Map<String,Object> map){
        super(map);
    }

    /**
     * 设置 用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取 用户ID
     *
     * @return 用户ID
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置 姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        super.setValue(USER_NAME, userName);
    }

    /**
     * 获取 姓名
     *
     * @return 姓名
     */
    public String getUserName() {
        return super.getValueAsString(USER_NAME);
    }

    /**
     * 设置 性别
     *
     * @param gender 性别
     */
    public void setGender(Integer gender) {
        super.setValue(GENDER, gender);
    }

    /**
     * 获取 性别
     *
     * @return 性别
     */
    public Integer getGender() {
        return super.getValueAsInteger(GENDER);
    }

    /**
     * 设置 出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        super.setValue(BIRTHDAY, birthday);
    }

    /**
     * 获取 出生日期
     *
     * @return 出生日期
     */
    public Date getBirthday() {
        return super.getValueAsDate(BIRTHDAY);
    }

    /**
     * 设置 手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        super.setValue(PHONE, phone);
    }

    /**
     * 获取 手机号
     *
     * @return 手机号
     */
    public String getPhone() {
        return super.getValueAsString(PHONE);
    }

    /**
     * 设置 邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        super.setValue(EMAIL, email);
    }

    /**
     * 获取 邮箱
     *
     * @return 邮箱
     */
    public String getEmail() {
        return super.getValueAsString(EMAIL);
    }

    /**
     * 设置 用户编码
     *
     * @param userCode 用户编码
     */
    public void setUserCode(String userCode) {
        super.setValue(USER_CODE, userCode);
    }

    /**
     * 获取 用户编码
     *
     * @return 用户编码
     */
    public String getUserCode() {
        return super.getValueAsString(USER_CODE);
    }

    /**
     * 设置 证件类型
     *
     * @param idType 证件类型
     */
    public void setIdType(String idType) {
        super.setValue(ID_TYPE, idType);
    }

    /**
     * 获取 证件类型
     *
     * @return 证件类型
     */
    public String getIdType() {
        return super.getValueAsString(ID_TYPE);
    }

    /**
     * 设置 证件号
     *
     * @param idCardNum 证件号
     */
    public void setIdCardNum(String idCardNum) {
        super.setValue(ID_CARD_NUM, idCardNum);
    }

    /**
     * 获取 证件号
     *
     * @return 证件号
     */
    public String getIdCardNum() {
        return super.getValueAsString(ID_CARD_NUM);
    }

    /**
     * 设置 政治面貌
     *
     * @param political 政治面貌
     */
    public void setPolitical(String political) {
        super.setValue(POLITICAL, political);
    }

    /**
     * 获取 政治面貌
     *
     * @return 政治面貌
     */
    public String getPolitical() {
        return super.getValueAsString(POLITICAL);
    }

    /**
     * 设置 婚姻状况
     *
     * @param maritalState 婚姻状况
     */
    public void setMaritalState(Integer maritalState) {
        super.setValue(MARITAL_STATE, maritalState);
    }

    /**
     * 获取 婚姻状况
     *
     * @return 婚姻状况
     */
    public Integer getMaritalState() {
        return super.getValueAsInteger(MARITAL_STATE);
    }

    /**
     * 设置 国籍
     *
     * @param nationality 国籍
     */
    public void setNationality(String nationality) {
        super.setValue(NATIONALITY, nationality);
    }

    /**
     * 获取 国籍
     *
     * @return 国籍
     */
    public String getNationality() {
        return super.getValueAsString(NATIONALITY);
    }

    /**
     * 设置 民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        super.setValue(NATION, nation);
    }

    /**
     * 获取 民族
     *
     * @return 民族
     */
    public String getNation() {
        return super.getValueAsString(NATION);
    }

    /**
     * 设置 籍贯
     *
     * @param nativePlace 籍贯
     */
    public void setNativePlace(String nativePlace) {
        super.setValue(NATIVE_PLACE, nativePlace);
    }

    /**
     * 获取 籍贯
     *
     * @return 籍贯
     */
    public String getNativePlace() {
        return super.getValueAsString(NATIVE_PLACE);
    }
}
