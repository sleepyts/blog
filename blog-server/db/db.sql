create table tb_admin
(
    id       int unsigned auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(20) not null
);

create table tb_app
(
    id      int unsigned auto_increment
        primary key,
    title   varchar(20)  null,
    content varchar(20)  null,
    url     varchar(100) null
);

create table tb_blog
(
    id          int unsigned auto_increment
        primary key,
    title       varchar(50)                        not null comment '标题',
    img         varchar(500)                       null comment '图片资源路径',
    content     text                               not null,
    description varchar(200)                       null,
    category_id bigint                             not null,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null
);

create table tb_category
(
    id   int unsigned auto_increment
        primary key,
    name varchar(20) null
);

create table tb_comment
(
    id          int unsigned auto_increment
        primary key,
    name        varchar(30)           null,
    content     varchar(1000)         not null,
    url         varchar(50)           null,
    blog_id     bigint                not null,
    reply_id    bigint     default -1 not null,
    is_admin    tinyint(1) default 0  not null,
    create_time datetime              not null
);

create table tb_exception
(
    id            int unsigned auto_increment
        primary key,
    method_name   varchar(30)  null,
    error_message varchar(500) null,
    error_type    varchar(200) null,
    create_time   datetime     null
);

create table tb_friend
(
    id          int auto_increment
        primary key,
    name        varchar(20)  not null,
    url         varchar(100) not null,
    description varchar(100) not null,
    avatar      varchar(200) not null,
    create_time datetime     not null,
    update_time datetime     not null
);

create table tb_log
(
    id             int auto_increment
        primary key,
    ip             varchar(100)                       null,
    address        varchar(30)                        not null,
    method_name    varchar(30)                        not null,
    args_and_value varchar(100)                       not null,
    os             varchar(30)                        not null,
    browser        varchar(30)                        not null,
    process_time   int                                not null,
    create_time    datetime default CURRENT_TIMESTAMP not null
);

create table tb_moment
(
    id          int auto_increment
        primary key,
    content     text                     not null,
    visible     tinyint(1)               not null,
    create_time datetime default (now()) not null,
    update_time datetime default (now()) not null
);

create table tb_operation
(
    id             int auto_increment
        primary key,
    user_name      varchar(20) null,
    method_name    varchar(20) null,
    operation_time datetime    null
);

create table tb_record
(
    id          int unsigned auto_increment,
    blog_id     bigint                   not null,
    title       varchar(40)              not null,
    date        date                     null,
    create_time datetime default (now()) null,
    update_time datetime default (now()) null,
    constraint id
        unique (id)
);

create table tb_sensitive_word
(
    word varchar(255) not null comment '敏感词'
)
    comment '敏感词库' collate = utf8mb4_unicode_ci;

create table tb_settings
(
    about_me              varchar(1000) null,
    ICP                   varchar(100)  null,
    about_me_page_song_id varchar(30)   null,
    index_name            varchar(20)   null,
    index_url             varchar(70)   null,
    description           varchar(50)   null,
    logo_url              varchar(100)  null,
    announcement          varchar(50)   null
);

create table tb_uvpv
(
    id   int auto_increment
        primary key,
    uv   int  null,
    pv   int  null,
    date date null
);

create table tb_visitor
(
    id               int auto_increment
        primary key,
    ip               varchar(40)  not null,
    address          varchar(40)  not null,
    user_agent       varchar(400) null,
    first_visit_time datetime     null,
    last_visit_time  datetime     null,
    constraint tb_visitor_pk
        unique (ip)
);

create index ipIdx
    on tb_visitor (ip);

