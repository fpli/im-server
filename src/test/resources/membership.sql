drop database if exists `membership`;
create database if not exists `membership` default charset utf8 collate utf8_general_ci;
-- 会员信息表
drop table if exists `slj_user_tbl`;
create table `slj_user_tbl`(
   `id` int(11) unsigned not null auto_increment comment '用户id',
   `username` varchar(30) not null comment '用户登录名',
   `password` varchar(128) not null comment '密码',
   `cardno`  varchar(32) not null comment '卡号',
   `realname` varchar(10) not null comment '真实姓名',
   `telephone` varchar(15) not null comment '固定电话',
   `mobilephone` varchar(15) not null comment '手机号码',
   `email` varchar(20) not null comment '电子邮箱',
   `address` varchar(100) not null comment '住址',
   `zipcode` varchar(10) not null comment '邮政编码',
   `jsr_name` varchar(30) not null comment '介绍人姓名',
   `jsr_cardno` varchar(32) not null comment '介绍人卡号',
   `jxs_cardno` varchar(32) not null comment '经销商会员卡号',
   `status` int(1) not null comment '状态 1待审核 0禁用 2正常',
   `regtime` timestamp null default current_timestamp comment '激活时间',
   `modifytime` timestamp null default current_timestamp on update current_timestamp comment '最后修改时间',
   `usertype`  int(2) null comment '会员类型',
   `integral` int(11) comment '会员积分',
   `enough` int(1) comment '积分是否达到过3000 （0未达到过 1已达到过）[超过3000可以兑换商品]',
   `logincount` int(11) default 0 comment '登陆次数',
   `lastlogintime` timestamp null default current_timestamp comment '最后登录时间',
   `loginip` varchar(15) null comment '登录ip',
   primary key ( `id` )
)engine=innodb default charset=utf8;
-- 会员信息扩展表
drop table if exists `slj_users_extension`;
create table `slj_users_extension`(
    `userid` int(11) comment '客户id',
    `sex` int(1) comment '用户性别[男女]',
    `birthday` varchar(15) comment '出生日期',
    `identitycardno` varchar(20) comment '身份证号码',
    `education` int(1) comment '教育程度 1初中及以下 2高中（中专） 3大学（大专） 4硕士及以上',
    `occupation` int(2) comment '职业1学生 2工人 3服务业 4教育 5文化艺术 6法律 7金融 8经济 9科研 10营销 11一般管理 12企业高层管理 13行政事业单位管理 14其他',
    `salary` int(11) comment '月收入 1(3000元以下) 2(3000-6000元) 3(6000-10000元) 4(10000元以上)',
    `access` int(1) comment '产品或企业信息获取途径 1电视 2报纸 3杂志 4企业网站 5其他网站 6健康尊享会员介绍 7其他途径',
    `onoursite` int(1) comment '是否经常登录网站 是 否',
    `usedproduct` varchar(200) comment '曾使用的产品',
    `proposal` varchar(100) comment '对产品的建议',
    `servicerequest` varchar(200) comment '对服务的请求',
    `applytime` timestamp comment '申请日期'
) engine=innodb default charset=utf8;
-- 会员卡表
drop table if exists `slj_usercard`;
create table `slj_usercard`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `cardno` varchar(32) not null comment '会员卡号',
    `password` varchar(8) not null comment '初始密码',
    `state` int(1) not null comment '是否激活（0未激活 1已激活）',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- 会员类型表
drop table if exists `slj_users_type`;
create table `slj_users_type`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `typename` varchar(50) not null comment '类型名称',
    `integral` int(8) not null comment '诚信积分',
    `content` varchar(100) not null comment '备注',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- 积分规则表
drop table if exists `slj_integral_rule`;
create table `slj_integral_rule`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `mark` varchar(20) not null comment '标识',
    `integral` int(8) not null comment '诚信积分',
    `content` varchar(100) not null comment '备注',
    `state` int(1) not null comment '是否在用0过期 1在用',
    `overtime` date not null comment '过期时间',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- 积分变化记录表
drop table if exists `slj_integral_record`;
create table `slj_integral_record`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `userid` int(11) not null comment '会员id',
    `integral` int(8) not null comment '数字积分',
    `content` varchar(100) not null comment '备注',
    `isadd` int(1) not null comment '1增加 2减少',
    `recordtime` timestamp default current_timestamp comment '积分变化时间',
    primary key (`id`)
) engine=innodb default charset=utf8;
-- 会员发展记录表
drop table if exists `slj_jsr_record`;
create table `slj_jsr_record`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `userid` int(11) not null comment '会员id',
    `jsr_userid` int(11) not null comment '介绍人id',
    primary key ( `id` )
) engine=innodb default charset=utf8;

--product integration
-- 产品对应积分表
drop table if exists `slj_project_integration`;
create table `slj_project_integration`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `title` varchar(200) not null comment '箱体材质',
    `sortnum` int(8) not null comment '排序序号（越大越靠前）',
    `third_id` int(3) not null comment '产品型号',
    `second_id` int(3) not null comment '类别',
    `base_id` int(3) not null comment '直营或经销',
    `price` int(10) not null comment '全国统一零售价',
    `price2` int(10) not null comment '经销商或零售价',
    `integralscale` int(8) not null comment '积分比例',
    `integral` int(8) not null comment '数字积分',
    `pic1` varchar(200) not null comment '产品缩略图',
    `url` varchar(200) not null comment '官方的对应产品链接',
    `num` int(10) comment '销售数量',
    primary key (`id`)
) engine=innodb default charset=utf8;

-- 会员购买产品记录表
drop table if exists `slj_users_product_record`;
create table `slj_users_product_record`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `userid` int(11) not null comment '会员id',
    `product_id` int(11) not null comment '购买产品id',
    `buytime` timestamp default current_timestamp comment '购买时间',
    primary key ( `id` )
) engine=innodb default charset=utf8;

-- gift 礼品表info
drop table if exists `slj_gift_info`;
create table `slj_gift_info`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `title` varchar(200) not null comment '箱体材质',
    `sortnum` int(8) not null comment '排序序号（越大越靠前）',
    `base_id` int(3) not null comment '礼品类',
    `second_id` int(3) not null comment '礼品细类',
    `price` int(10) not null comment '兑换所需积分',
    `pic1` varchar(200) not null comment '产品缩略图',
    `num` int(10) comment '兑换次数',
    primary key (`id`)
) engine=innodb default charset=utf8;

-- 礼品兑换记录表
drop table if exists `slj_users_gift_record`;
create table `slj_users_gift_record`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `userid` int(11) not null comment '会员id',
    `giftid` int(11) not null comment '兑换礼品id',
    `requesttime` timestamp default current_timestamp comment '请求兑换时间',
    `finishtime` timestamp default current_timestamp comment '兑换完成时间',
    `state` int(1) comment '0未确认 1处理中 2已完成',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- 管理员操作记录表
drop table if exists `slj_admin_operation_record`;
create table `slj_admin_operation_record`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `administratorid` int(11) not null comment '管理员id',
    `createtime` timestamp default current_timestamp comment '操作时间',
    `operation` varchar(100) not null comment '操作说明',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- 参数表（教育程度、职业、月收入、产品获取途径） slj_parameter_list
drop table if exists `slj_parameter_list`;
create table `slj_parameter_list`(
    `id` int(11) unsigned not null auto_increment comment '主键id',
    `sortnum` int(8) not null comment '排序序号（越大越靠前）',
    `tablefield` varchar(20) not null comment '类别（例：education 教育程度）',
    `param` varchar(50) not null comment '参数名称',
    primary key ( `id` )
) engine=innodb default charset=utf8;
-- education  教育程度 Occupation 职业  Exchange 最低兑换分值 Salary 月收入 Access 产品或企业信息获取路径
insert into slj_parameter_list(sortnum, tablefield, param) values (1, 'education', '教育程度'), (2, 'occupation', '职业'),
 (3, 'exchange', '最低兑换分值'), (4, 'salary', '月收入'), (5, 'access', '产品或企业信息获取路径');

select * from slj_parameter_list;