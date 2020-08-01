CREATE TABLE `api_user` (
                            `id` varchar(32) NOT NULL,
                            `code` varchar(32) DEFAULT NULL,
                            `name` varchar(64) DEFAULT NULL,
                            `secret` varchar(255) DEFAULT NULL,
                            `system` varchar(32) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `api_user` VALUES ('1', 'test', '测试用户', 'test1234$#@!', null);
INSERT INTO `api_user` VALUES ('4028e3816bb74f92016bb764c9240001', 'test1', 'test1', 'sfasdf', null);
INSERT INTO `api_user` VALUES ('4028e3816d38a907016d38e78b080001', 'aaa', 'aaa', 'aaa', null);
