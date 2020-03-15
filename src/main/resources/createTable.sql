use stu;

drop table studentInfo;
create table stu.studentInfo(
  sid int(10)  primary key auto_increment comment '学号',
  sname varchar(30) not null comment '学员姓名',
  sgender  varchar(2) not null default '男' comment '学员性别',
  sage int(4) not null comment '学员年龄',
  saddress varchar(100)   comment '家庭住址',
  semail varchar(50) comment '电子邮件'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into studentInfo values
  (null,'张全芳','女',20,'北京市朝阳区','quanfang@163.com'),
  (null,'李思阳','男',19,'北京市崇文区','siyang@126.com'),
  (null,'侯全如','男',21,'江苏省南京市','quanru@126.com');

TRUNCATE TABLE studentInfo;

select * from studentInfo