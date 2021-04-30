--
-- TABLE: k_account
--

CREATE TABLE k_account(
    account_id                  VARCHAR(50)     NOT NULL,
    account_name                VARCHAR(100),
    password                    VARCHAR(100)    NOT NULL,
    account_state               INT,
    active_date                 DATETIME,
    locked_date                 DATETIME,
    create_date                 DATETIME,
    expired_date                DATETIME,
    credentials_expired_date    DATETIME,
    last_login_date             DATETIME,
    change_password_date        DATETIME,
    user_id                     VARCHAR(50)     NOT NULL,
    PRIMARY KEY (account_id)
)ENGINE=INNODB
;



--
-- TABLE: k_audit_log
--

CREATE TABLE k_audit_log(
    log_id          VARCHAR(50)     NOT NULL,
    method          VARCHAR(20),
    module_name     VARCHAR(200),
    operate_name    VARCHAR(200),
    url             VARCHAR(200),
    user_id         VARCHAR(50),
    user_name       VARCHAR(100),
    remote_host     VARCHAR(20),
    create_time     DATETIME,
    PRIMARY KEY (log_id)
)ENGINE=INNODB
;



--
-- TABLE: k_authorize
--

CREATE TABLE k_authorize(
    authorize_id        VARCHAR(50)     NOT NULL,
    authorize_object    VARCHAR(50),
    authorize_type      INT,
    operate_object      VARCHAR(500),
    operate_type        INT,
    resource_id         VARCHAR(50)     NOT NULL,
    PRIMARY KEY (authorize_id)
)ENGINE=INNODB
;

--
-- TABLE: k_config_store
--

CREATE TABLE k_config_store(
    config_id         VARCHAR(50)     NOT NULL,
    config_code       VARCHAR(100),
    config_explain    VARCHAR(100),
    is_major          INT,
    PRIMARY KEY (config_id)
)ENGINE=INNODB
;



--
-- TABLE: k_config_store_item
--

CREATE TABLE k_config_store_item(
    config_item_id    VARCHAR(50)     NOT NULL,
    config_id         VARCHAR(50)     NOT NULL,
    item_name         VARCHAR(100),
    item_value        VARCHAR(500),
    PRIMARY KEY (config_item_id)
)ENGINE=INNODB
;



--
-- TABLE: k_delete_archive
--

CREATE TABLE k_delete_archive(
    archive_id       VARCHAR(50)     NOT NULL,
    archive_code     VARCHAR(50),
    delete_date      DATETIME,
    user_id          BIGINT,
    user_name        VARCHAR(100),
    table_name       VARCHAR(50)     NOT NULL,
    archive_data     TEXT            NOT NULL,
    resume_script    VARCHAR(500),
    PRIMARY KEY (archive_id)
)ENGINE=INNODB
;



--
-- TABLE: k_ENTITY_DEF_INFO
--

CREATE TABLE k_ENTITY_DEF_INFO(
    ENTITY_DEF_ID    VARCHAR(50)     NOT NULL,
    NAMESPACE        VARCHAR(100),
    ENTITY_NAME      VARCHAR(100),
    TABLE_NAME       VARCHAR(100),
    ENTITY_CODE      VARCHAR(100),
    PRIMARY KEY (ENTITY_DEF_ID)
)ENGINE=INNODB
;



--
-- TABLE: k_ENTITY_FIELD_DEF_INFO
--

CREATE TABLE k_ENTITY_FIELD_DEF_INFO(
    ENTITY_FIELD_DEF_ID    VARCHAR(50)     NOT NULL,
    FIELD_NAME             VARCHAR(100),
    ATTRIBUTE_NAME         VARCHAR(100),
    JAVA_TYPE              VARCHAR(100),
    JDBC_TYPE              INT,
    REMARKS                VARCHAR(200),
    IS_PK                  INT,
    ENTITY_DEF_ID          VARCHAR(50)     NOT NULL,
    PRIMARY KEY (ENTITY_FIELD_DEF_ID)
)ENGINE=INNODB
;



--
-- TABLE: k_ENTITY_RELATION
--

CREATE TABLE k_ENTITY_RELATION(
    RELATION_ID        VARCHAR(50)    NOT NULL,
    RELATION_DEF_ID    VARCHAR(50),
    ENTITY_DEF_ID      CHAR(10),
    PRIMARY KEY (RELATION_ID)
)ENGINE=INNODB
;



--
-- TABLE: k_hierarchical_authorize
--

CREATE TABLE k_hierarchical_authorize(
    authorize_id    VARCHAR(50)    NOT NULL,
    org_id               VARCHAR(50),
    user_id              VARCHAR(50),
    create_time          DATETIME,
    PRIMARY KEY (authorize_user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_menu
--

CREATE TABLE k_menu(
    menu_id      VARCHAR(50)     NOT NULL,
    parent_id    VARCHAR(50)     NOT NULL,
    menu_name    VARCHAR(100),
    menu_code    VARCHAR(100),
    menu_type    INT,
    data_path    VARCHAR(500),
    icon         VARCHAR(100),
    order_num    INT,
    PRIMARY KEY (menu_id)
)ENGINE=INNODB
;



--
-- TABLE: k_menu_authorize
--

CREATE TABLE k_menu_authorize(
    menu_authorize_id    VARCHAR(50)    NOT NULL,
    menu_id              VARCHAR(50)    NOT NULL,
    is_public            INT,
    resource_id          VARCHAR(50),
    operate_type         INT,
    operate_object       VARCHAR(50),
    PRIMARY KEY (menu_authorize_id)
)ENGINE=INNODB
;



--
-- TABLE: k_organization
--

CREATE TABLE k_organization(
    org_id         VARCHAR(50)      NOT NULL,
    parent_id      VARCHAR(50)      NOT NULL,
    org_name       VARCHAR(200),
    org_code       VARCHAR(200),
    short_name     VARCHAR(255),
    org_type       VARCHAR(50),
    org_nature     VARCHAR(50),
    create_time    DATETIME,
    data_path      VARCHAR(1000),
    order_num      INT,
    PRIMARY KEY (org_id)
)ENGINE=INNODB
;



--
-- TABLE: k_organization_user
--

CREATE TABLE k_organization_user(
    org_user_id    VARCHAR(50)    NOT NULL,
    org_id         VARCHAR(50)    NOT NULL,
    user_id        VARCHAR(50)    NOT NULL,
    user_type      INT,
    PRIMARY KEY (org_user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_position
--

CREATE TABLE k_position(
    position_id       VARCHAR(50)     NOT NULL,
    position_name     VARCHAR(200),
    position_type     INT,
    order_num         INT,
    create_user_id    VARCHAR(50),
    create_time       DATETIME,
    org_id            VARCHAR(50)     NOT NULL,
    PRIMARY KEY (position_id)
)ENGINE=INNODB
;



--
-- TABLE: k_position_user
--

CREATE TABLE k_position_user(
    position_user_id    VARCHAR(50)     NOT NULL,
    position_id         VARCHAR(50)     NOT NULL,
    user_id             VARCHAR(50)     NOT NULL,
    user_name           VARCHAR(255),
    office_name         VARCHAR(255),
    office_type         VARCHAR(50),
    order_num           INT,
    create_user_id      VARCHAR(50),
    create_time         DATETIME,
    PRIMARY KEY (position_user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_resource
--

CREATE TABLE k_resource(
    resource_id      VARCHAR(50)     NOT NULL,
    resource_name    VARCHAR(100),
    resource_code    VARCHAR(100),
    resource_path    VARCHAR(200),
    md5              VARCHAR(50),
    PRIMARY KEY (resource_id)
)ENGINE=INNODB
;



--
-- TABLE: k_resource_operate
--

CREATE TABLE k_resource_operate(
    operate_id      VARCHAR(50)     NOT NULL,
    resource_id     VARCHAR(50)     NOT NULL,
    operate_name    VARCHAR(100),
    operate_code    VARCHAR(100),
    group_code      VARCHAR(100),
    operate_path    VARCHAR(200),
    method          VARCHAR(20),
    is_enable       INT,
    PRIMARY KEY (operate_id)
)ENGINE=INNODB
;



--
-- TABLE: k_role
--

CREATE TABLE k_role(
    role_id      VARCHAR(50)     NOT NULL,
    role_name    VARCHAR(100),
    role_code    VARCHAR(100),
    role_type    INT,
    remark       VARCHAR(500),
    PRIMARY KEY (role_id)
)ENGINE=INNODB
;



--
-- TABLE: k_role_object
--

CREATE TABLE k_role_object(
    object_id      VARCHAR(50)    NOT NULL,
    role_id        VARCHAR(50)    NOT NULL,
    role_object    BIGINT,
    object_type    INT,
    PRIMARY KEY (object_id)
)ENGINE=INNODB
;



--
-- TABLE: k_role_user
--

CREATE TABLE k_role_user(
    role_user_id    VARCHAR(50)    NOT NULL,
    user_id         VARCHAR(50)    NOT NULL,
    role_id         VARCHAR(50)    NOT NULL,
    PRIMARY KEY (role_user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_user
--

CREATE TABLE k_user(
    user_id          VARCHAR(50)     NOT NULL,
    user_name        VARCHAR(200),
    gender           INT,
    birthday         DATE,
    phone            VARCHAR(20),
    email            VARCHAR(100),
    user_code        VARCHAR(50),
    id_type          VARCHAR(50),
    id_card_num      VARCHAR(50),
    political        VARCHAR(50),
    marital_state    INT,
    nationality      VARCHAR(255),
    nation           VARCHAR(50),
    native_place     VARCHAR(255),
    photo_id         VARCHAR(50),
    PRIMARY KEY (user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_account
--

ALTER TABLE k_account ADD CONSTRAINT Refk_user30
    FOREIGN KEY (user_id)
    REFERENCES k_user(user_id)
;


--
-- TABLE: k_authorize
--

ALTER TABLE k_authorize ADD CONSTRAINT Refk_resource54
    FOREIGN KEY (resource_id)
    REFERENCES k_resource(resource_id)
;


--
-- TABLE: k_config_store_item
--

ALTER TABLE k_config_store_item ADD CONSTRAINT Refk_config_store51
    FOREIGN KEY (config_id)
    REFERENCES k_config_store(config_id)
;


--
-- TABLE: k_ENTITY_FIELD_DEF_INFO
--

ALTER TABLE k_ENTITY_FIELD_DEF_INFO ADD CONSTRAINT Refk_ENTITY_DEF_INFO50
    FOREIGN KEY (ENTITY_DEF_ID)
    REFERENCES k_ENTITY_DEF_INFO(ENTITY_DEF_ID)
;


--
-- TABLE: k_menu
--

ALTER TABLE k_menu ADD CONSTRAINT Refk_menu55
    FOREIGN KEY (parent_id)
    REFERENCES k_menu(menu_id)
;


--
-- TABLE: k_menu_authorize
--

ALTER TABLE k_menu_authorize ADD CONSTRAINT Refk_menu56
    FOREIGN KEY (menu_id)
    REFERENCES k_menu(menu_id)
;


--
-- TABLE: k_organization
--

ALTER TABLE k_organization ADD CONSTRAINT Refk_organization33
    FOREIGN KEY (parent_id)
    REFERENCES k_organization(org_id)
;


--
-- TABLE: k_organization_user
--

ALTER TABLE k_organization_user ADD CONSTRAINT Refk_organization35
    FOREIGN KEY (org_id)
    REFERENCES k_organization(org_id)
;

ALTER TABLE k_organization_user ADD CONSTRAINT Refk_user37
    FOREIGN KEY (user_id)
    REFERENCES k_user(user_id)
;


--
-- TABLE: k_position
--

ALTER TABLE k_position ADD CONSTRAINT Refk_organization34
    FOREIGN KEY (org_id)
    REFERENCES k_organization(org_id)
;


--
-- TABLE: k_position_user
--

ALTER TABLE k_position_user ADD CONSTRAINT Refk_position36
    FOREIGN KEY (position_id)
    REFERENCES k_position(position_id)
;

ALTER TABLE k_position_user ADD CONSTRAINT Refk_user38
    FOREIGN KEY (user_id)
    REFERENCES k_user(user_id)
;


--
-- TABLE: k_resource_operate
--

ALTER TABLE k_resource_operate ADD CONSTRAINT Refk_resource29
    FOREIGN KEY (resource_id)
    REFERENCES k_resource(resource_id)
;


--
-- TABLE: k_role_object
--

ALTER TABLE k_role_object ADD CONSTRAINT Refk_role28
    FOREIGN KEY (role_id)
    REFERENCES k_role(role_id)
;


--
-- TABLE: k_role_user
--

ALTER TABLE k_role_user ADD CONSTRAINT Refk_user52
    FOREIGN KEY (user_id)
    REFERENCES k_user(user_id)
;

ALTER TABLE k_role_user ADD CONSTRAINT Refk_role53
    FOREIGN KEY (role_id)
    REFERENCES k_role(role_id)
;


create table oauth_access_token (
	authentication_id VARCHAR(255) NOT NULL,
  token_id VARCHAR(255),
  token BLOB,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255),
	PRIMARY KEY (authentication_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table oauth_refresh_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table oauth_client_details (
  client_id VARCHAR(255) NOT NULL,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INT,
  refresh_token_validity INT,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255),
	PRIMARY KEY (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




--
-- TABLE: k_work_calendar
--

CREATE TABLE k_work_calendar(
    calendar_id      VARCHAR(50)     NOT NULL,
    calendar_name    VARCHAR(200),
    calendar_code    VARCHAR(100),
    calendar_year    INT,
    description      VARCHAR(500),
    PRIMARY KEY (calendar_id)
)ENGINE=INNODB
;

--
-- TABLE: k_holiday_day
--

CREATE TABLE k_holiday_day(
    holiday_id       VARCHAR(50)     NOT NULL,
    calendar_id      VARCHAR(50)     NOT NULL,
    holiday_name     VARCHAR(200),
    holiday_type     INT,
    description      VARCHAR(500),
    holiday_date     DATE,
    holiday_month    INT,
    holiday_day      INT,
    PRIMARY KEY (holiday_id)
)ENGINE=INNODB
;



--
-- TABLE: k_message_group
--

CREATE TABLE k_message_group(
    group_id        VARCHAR(50)    NOT NULL,
    message_id      VARCHAR(50)    NOT NULL,
    relation_id     VARCHAR(50),
    group_type      VARCHAR(50),
    expired_date    DATETIME,
    PRIMARY KEY (group_id)
)ENGINE=INNODB
;



--
-- TABLE: k_message_user
--

CREATE TABLE k_message_user(
    message_user_id    VARCHAR(50)    NOT NULL,
    message_id         VARCHAR(50),
    user_id            VARCHAR(50),
    is_read            INT,
    is_delete          INT,
    PRIMARY KEY (message_user_id)
)ENGINE=INNODB
;



--
-- TABLE: k_private_message
--

CREATE TABLE k_private_message(
    message_id         VARCHAR(50)     NOT NULL,
    message_title      VARCHAR(100),
    message_content    VARCHAR(500),
    send_date          DATETIME,
    sender_id          VARCHAR(50),
    sender_name        VARCHAR(100),
    PRIMARY KEY (message_id)
)ENGINE=INNODB
;





INSERT INTO `k_user`(`user_id`, `user_name`, `gender`) VALUES ('1', '管理员', 1);
INSERT INTO `k_account`(`account_id`, `account_name`, `password`, `account_state`, `user_id`) VALUES ('1', 'admin', '$2a$10$xHP1ILUirEK8M53aLUIdeu1qM3UCYs8L8MgtUv7SaCaI8YBSr8HdG', 1, '1');

INSERT INTO `k_role`(`role_id`, `role_name`, `role_code`, `role_type`) VALUES ('1', '管理员', 'CODE_ADMIN', NULL);
INSERT INTO `k_role_object`(`object_id`, `role_id`, `role_object`, `object_type`) VALUES ('1', '1', 1, 1);

INSERT INTO `k_menu`(`menu_id`, `parent_id`, `menu_name`, `menu_code`, `order_num`) VALUES ('-1', '-1', '菜单管理', 'ROOT', 0);
INSERT INTO `k_organization`(`org_id`, `parent_id`, `org_name`, `org_code`, `short_name`, `data_path`, `order_num`) VALUES ('-1', '-1', '组织机构', 'ROOT', '组织机构', '/', 0);

INSERT INTO `k_hierarchical_authorize`(`authorize_id`, `org_id`, `user_id`) VALUES ('1', '-1', '1');