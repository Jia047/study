CREATE TABLE permission (
    pid INT(11) NOT NULL AUTO_INCREMENT,
    pname VARCHAR(255) NOT NULL DEFAULT '',
    url VARCHAR(255) NOT NULL DEFAULT '',
    PRIMARY KEY (pid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO permission VALUES ('1', 'add', '');
INSERT INTO permission VALUES ('2', 'delete', '');
INSERT INTO permission VALUES ('3', 'edit', '');
INSERT INTO permission VALUES ('4', 'query', '');

CREATE TABLE user (
    uid INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL DEFAULT '',
    password VARCHAR(255) NOT NULL DEFAULT '',
    PRIMARY KEY (uid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO user VALUES ('1', 'admin', '123');
INSERT INTO user VALUES ('2', 'jack', '123');

CREATE TABLE role (
    rid INT(11) NOT NULL AUTO_INCREMENT,
    rname VARCHAR(255) NOT NULL DEFAULT '',
    PRIMARY KEY (rid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO role VALUES ('1', 'admin');
INSERT INTO role VALUES ('2', 'customer');

# 角色权限表
CREATE TABLE permission_role (
    pid INT(11) NOT NULL ,
    rid INT(11) NOT NULL ,
    PRIMARY KEY (pid, rid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

# 用户角色关系表
CREATE TABLE user_role (
    uid INT(11) NOT NULL,
    rid INT(11) NOT NULL,
    PRIMARY KEY (uid, rid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO permission_role VALUES ('1', '1');
INSERT INTO permission_role VALUES ('1', '2');
INSERT INTO permission_role VALUES ('1', '3');
INSERT INTO permission_role VALUES ('1', '4');
INSERT INTO permission_role VALUES ('2', '1');
INSERT INTO permission_role VALUES ('2', '4');

INSERT INTO user_role VALUES ('1','1');
INSERT INTO user_role VALUES ('2','2');
