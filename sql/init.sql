-- VocabVerse 项目初始化 SQL 脚本
-- 版本: 1.0

-- ----------------------------
-- 推荐的 MyBatis-Plus 全局配置
-- 请在 application.yml 文件中添加:
--
-- mybatis-plus:
--   configuration:
--     map-underscore-to-camel-case: true
-- ----------------------------


-- ----------------------------
-- 数据库: `vocabverse`
-- (如果不存在，请先手动创建: CREATE DATABASE vocabverse CHARACTER SET utf8mb4;)
-- ----------------------------
USE vocabverse;


-- ----------------------------
-- 表结构: `user` (用户信息表)
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
  `username` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '加密后的密码',
  `role` VARCHAR(50) NOT NULL COMMENT '用户角色 (e.g., STUDENT, TEACHER, PARENT)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- (未来可以继续在这里添加新的表结构...)
-- ----------------------------