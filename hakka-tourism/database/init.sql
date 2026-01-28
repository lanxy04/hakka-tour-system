-- 创建数据库
CREATE DATABASE IF NOT EXISTS hakka_tourism DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hakka_tourism;

-- 用户表
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `role` INT DEFAULT 0 COMMENT '角色:0-普通用户,1-管理员',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 资源表
CREATE TABLE `resource` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资源ID',
    `name` VARCHAR(200) NOT NULL COMMENT '资源名称',
    `region` VARCHAR(100) NOT NULL COMMENT '地区',
    `type` VARCHAR(50) NOT NULL COMMENT '类型:景点/美食/住宿/文化体验',
    `theme` VARCHAR(100) DEFAULT NULL COMMENT '主题特色',
    `description` TEXT COMMENT '描述信息',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
    `score` DOUBLE DEFAULT 0.0 COMMENT '评分',
    `hot_count` INT DEFAULT 0 COMMENT '热度',
    `price` DOUBLE DEFAULT 0.0 COMMENT '价格',
    `images` TEXT COMMENT '图片URL',
    `tags` VARCHAR(500) DEFAULT NULL COMMENT '标签',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_region` (`region`),
    KEY `idx_type` (`type`),
    KEY `idx_theme` (`theme`),
    KEY `idx_hot_count` (`hot_count`),
    KEY `idx_score` (`score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅游资源表';

-- 路线表
CREATE TABLE `route` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '路线ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(200) NOT NULL COMMENT '路线名称',
    `description` TEXT COMMENT '路线描述',
    `days` INT DEFAULT 1 COMMENT '天数',
    `budget` DOUBLE DEFAULT 0.0 COMMENT '预算',
    `people_count` INT DEFAULT 1 COMMENT '人数',
    `route_data` TEXT COMMENT '路线数据(JSON格式)',
    `status` INT DEFAULT 0 COMMENT '状态:0-草稿,1-已保存',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅游路线表';

-- 订单表
CREATE TABLE `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `route_id` BIGINT NOT NULL COMMENT '路线ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
    `amount` DOUBLE DEFAULT 0.0 COMMENT '金额',
    `status` INT DEFAULT 0 COMMENT '状态:0-待支付,1-已支付,2-已取消',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 统计表
CREATE TABLE `statistics` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '统计ID',
    `statistic_type` VARCHAR(50) NOT NULL COMMENT '统计类型:resource_view/route_generate/order_create',
    `statistic_value` BIGINT DEFAULT 0 COMMENT '统计值',
    `record_date` DATE NOT NULL COMMENT '记录日期',
    `remarks` VARCHAR(200) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_type_date` (`statistic_type`, `record_date`),
    KEY `idx_statistic_type` (`statistic_type`),
    KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统计表';

-- 插入示例数据
INSERT INTO `user` (`username`, `password`, `email`, `phone`, `real_name`, `role`) VALUES
('admin', 'admin123', 'admin@example.com', '13800138000', '管理员', 1),
('user', 'user123', 'user@example.com', '13800138001', '普通用户', 0);

INSERT INTO `resource` (`name`, `region`, `type`, `theme`, `description`, `address`, `score`, `hot_count`, `price`, `tags`) VALUES
('梅州客家围龙屋', '梅州', '景点', '历史文化', '客家围龙屋是客家民居的典型代表,展现了客家独特的建筑文化', '梅州市梅县区雁洋镇', 4.5, 100, 50, '历史,建筑,客家文化'),
('客家酿豆腐', '梅州', '美食', '美食体验', '客家传统名菜,鲜嫩可口', '梅州市梅江区', 4.8, 200, 30, '美食,传统,豆腐'),
('客家土楼', '龙岩', '景点', '历史文化', '世界文化遗产,独特的圆形建筑', '龙岩市永定区', 4.9, 500, 80, '历史,建筑,世界遗产'),
('客家山歌', '赣州', '文化体验', '民俗风情', '客家传统山歌表演', '赣州市章贡区', 4.6, 150, 40, '文化,音乐,民俗'),
('客家黄酒', '龙岩', '美食', '美食体验', '传统客家黄酒,口感醇厚', '龙岩市连城县', 4.7, 180, 60, '美食,酒类,传统'),
('客家大浦温泉', '梅州', '住宿', '休闲度假', '温泉度假村,环境优美', '梅州市大埔县', 4.4, 90, 300, '温泉,度假,住宿');
