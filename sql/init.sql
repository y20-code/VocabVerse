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
-- 表结构`wordbook`(词书表）
-- ----------------------------
CREATE TABLE `wordbook` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '词书ID',
  `name` VARCHAR(255) NOT NULL COMMENT '词书名称',
  `description` TEXT COMMENT '词书描述',
  `word_count` INT DEFAULT 0 COMMENT '单词总数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='词书信息表';


-- ----------------------------
-- 表结构`word`(单词表）
-- ----------------------------
CREATE TABLE `word` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '单词ID',
  `wordbook_id` BIGINT NOT NULL COMMENT '所属词书ID',
  `word` VARCHAR(255) NOT NULL COMMENT '单词本身',
  `pronunciation` VARCHAR(255) COMMENT '音标',
  `definition` TEXT NOT NULL COMMENT '中文释义',
  `example_sentence` TEXT COMMENT '例句',
  PRIMARY KEY (`id`),
  INDEX `idx_wordbook_id` (`wordbook_id`) -- 为外键添加索引，提高查询效率
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单词信息表';

-- ----------------------------
-- 表结构`learning_record``(学习记录表）
-- ----------------------------
CREATE TABLE `learning_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `word_id` BIGINT NOT NULL COMMENT '单词ID',
  `is_known` BOOLEAN NOT NULL COMMENT '是否认识 (true/false)',
  `review_count` INT DEFAULT 1 COMMENT '复习次数',
  `last_review_time` DATETIME NOT NULL COMMENT '最后复习时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_word` (`user_id`, `word_id`), -- 确保一个用户对一个单词只有一条学习记录
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户学习记录表';


-- ----------------------------
-- (未来可以继续在这里添加新的表结构...)
-- ----------------------------