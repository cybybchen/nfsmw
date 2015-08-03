-- MySQL dump 10.13  Distrib 5.5.22, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: nfsmw
-- ------------------------------------------------------
-- Server version	5.5.22-0ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_ghost`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ghost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mode_id` int(11) NOT NULL,
  `event_name` varchar(64) NOT NULL,
  `race_time` float NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `car_id` varchar(64) NOT NULL,
  `car_color_index` int(11) NOT NULL,
  `eol` int(11) NOT NULL,
  `average_speed` float NOT NULL,
  `car_score` int(11) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mode_id_2` (`mode_id`,`user_id`),
  KEY `mode_id` (`mode_id`),
  KEY `user_id` (`user_id`),
  KEY `mode_id_3` (`mode_id`,`user_id`,`eol`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='用户ghost信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_ghost_mod`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ghost_mod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ghost_id` bigint(20) NOT NULL,
  `mode_type` int(11) NOT NULL,
  `mode_value` float NOT NULL,
  `mode_level` int(11) NOT NULL DEFAULT '0',
  `mode_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ghost_id` (`ghost_id`)
) ENGINE=InnoDB AUTO_INCREMENT=475 DEFAULT CHARSET=utf8 COMMENT='用户ghost部件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leaderboard`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leaderboard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mode_type` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(128) NOT NULL,
  `head_index` tinyint(4) DEFAULT '0',
  `head_url` varchar(255) DEFAULT '',
  `result` float NOT NULL COMMENT '成绩',
  PRIMARY KEY (`id`),
  KEY `mode_type` (`mode_type`),
  KEY `mode_type_2` (`mode_type`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_ghost`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_ghost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tournament_online_id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT '0',
  `event_name` varchar(64) NOT NULL,
  `race_time` float NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `car_id` varchar(64) NOT NULL,
  `car_color_index` int(11) NOT NULL,
  `eol` int(11) NOT NULL,
  `average_speed` float NOT NULL,
  `car_score` int(11) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`),
  KEY `tournament_online_id` (`tournament_online_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_ghost_mod`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_ghost_mod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tournament_ghost_id` bigint(20) NOT NULL,
  `mode_type` int(11) NOT NULL,
  `mode_value` float NOT NULL,
  `mode_level` int(11) NOT NULL DEFAULT '0',
  `mode_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `tournament_ghost_id` (`tournament_ghost_id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_user` (
  `tournament_online_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  `result` float NOT NULL COMMENT '成绩',
  `lefttimes` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `is_get_reward` int(11) NOT NULL,
  `average_speed` float NOT NULL DEFAULT '0',
  UNIQUE KEY `user_id` (`user_id`,`tournament_online_id`,`class_id`,`result`),
  KEY `tournamnet_online_id` (`tournament_online_id`,`user_id`),
  KEY `result` (`user_id`,`result`),
  KEY `tournamnet_online_id_2` (`tournament_online_id`,`user_id`,`class_id`,`average_speed`),
  KEY `tournamnet_online_id_3` (`tournament_online_id`,`group_id`),
  KEY `tournamnet_online_id_4` (`tournament_online_id`,`result`),
  KEY `tournament_online_id` (`tournament_online_id`,`result`,`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联赛参加用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `level` int(11) NOT NULL,
  `eol` int(11) NOT NULL DEFAULT '0',
  `last_win_or_not` int(11) NOT NULL DEFAULT '0',
  `dura_match_num` int(11) NOT NULL DEFAULT '0',
  `max_match_win_num` int(11) NOT NULL DEFAULT '0',
  `max_match_lose_num` int(11) NOT NULL DEFAULT '0',
  `money` int(11) NOT NULL,
  `energy` int(11) NOT NULL,
  `star_num` int(11) NOT NULL DEFAULT '0' COMMENT '获得的星星数',
  `head_index` tinyint(4) NOT NULL DEFAULT '0',
  `head_url` varchar(255) DEFAULT NULL,
  `tier` int(11) NOT NULL DEFAULT '0' COMMENT '考级执照级别',
  `daily_reward_date` date DEFAULT NULL COMMENT '每日挑战奖励时间',
  `last_regain_energy_date` int(11) NOT NULL,
  `rp_num` int(11) NOT NULL,
  `gold` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_race_start` int(11) NOT NULL DEFAULT '0',
  `is_old_user` int(11) NOT NULL DEFAULT '0',
  `is_nickname_changed` tinyint(1) NOT NULL DEFAULT '0',
  `is_write_jaguar` int(11) NOT NULL DEFAULT '0',
  `willowtree_token` varchar(255) NOT NULL DEFAULT '',
  `event_option_version` int(11) NOT NULL DEFAULT '0',
  `is_get_tutorial_reward` int(11) NOT NULL DEFAULT '0',
  `is_rewarded_bind` int(11) NOT NULL DEFAULT '0',
  `tier_status` int(11) NOT NULL DEFAULT '3',
  `cert_type` tinyint(4) DEFAULT NULL,
  `account_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `willowtree_token_2` (`willowtree_token`),
  KEY `version` (`version`),
  KEY `rp_num` (`rp_num`)
) ENGINE=InnoDB AUTO_INCREMENT=12048 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_car`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `car_id` varchar(64) NOT NULL,
  `score` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `chartlet_id` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=610 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_track`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_track` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `track_id` varchar(64) NOT NULL,
  `mode_id` int(11) NOT NULL,
  `value` int(11) NOT NULL COMMENT '完成度数值',
  `is_new` tinyint(4) NOT NULL COMMENT '是否新解锁赛道',
  `is_finish` tinyint(4) NOT NULL COMMENT '是否完成 即是否给了星星奖励',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_2` (`user_id`,`mode_id`),
  KEY `user_id` (`user_id`,`track_id`),
  KEY `user_id_3` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1220 DEFAULT CHARSET=utf8 COMMENT='用户赛道表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_session`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session` (
  `user_id` bigint(20) NOT NULL,
  `session` varchar(128) NOT NULL,
  `up_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `session` (`session`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_car_slot`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_car_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_car_id` bigint(20) NOT NULL COMMENT '用户车唯一id',
  `slot_id` int(11) NOT NULL,
  `create_time` int(11) NOT NULL COMMENT '购买/时间',
  `consumable_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_car_id` (`user_car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=587 DEFAULT CHARSET=utf8 COMMENT='用户车辆插槽表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_chartlet`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_chartlet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `chartlet_id` int(11) NOT NULL,
  `is_owned` int(11) NOT NULL DEFAULT '0',
  `rent_time` timestamp NULL DEFAULT NULL COMMENT '租赁开始时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_2` (`user_id`,`chartlet_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='用户购买的贴图表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `career_best_racetime_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `career_best_racetime_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mode_type` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `race_time` float NOT NULL,
  `average_speed` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `mode_id` (`mode_type`,`user_id`),
  KEY `mode_id_2` (`mode_type`,`race_time`,`user_id`),
  KEY `mode_id_3` (`mode_type`,`average_speed`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_leaderboard`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_leaderboard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tournament_online_id` int(11) NOT NULL,
  `race_time` float NOT NULL,
  `class_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `average_speed` float NOT NULL DEFAULT '0',
  `result` float NOT NULL DEFAULT '0',
  `user_name` varchar(64) NOT NULL DEFAULT '',
  `head_index` tinyint(4) NOT NULL DEFAULT '0',
  `head_url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `tournament_online_id` (`tournament_online_id`,`class_id`),
  KEY `tournament_online_id_2` (`tournament_online_id`,`class_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_daily_race`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_daily_race` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `last_match_date` int(11) NOT NULL,
  `left_times` int(11) NOT NULL,
  `dura_day_num` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=727 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `buy_time` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`purchase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `version` tinyint(4) NOT NULL,
  `md5` varchar(64) NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `order_id` int(11) NOT NULL,
  `platform` int(11) NOT NULL,
  `frequency` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `version` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource_version`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '版本状态:beta/abtest/live',
  `create_time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `version` (`version`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源发布记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `daily_race_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_race_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mode_index` int(11) NOT NULL,
  `car_index` int(11) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `tier` int(11) NOT NULL,
  `car_id` varchar(255) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `create_time` (`create_time`,`tier`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `request_name` varchar(255) NOT NULL,
  `create_time` int(11) NOT NULL,
  `param` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12581 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iap_check_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iap_check_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `create_time` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `rmb_num` int(11) NOT NULL DEFAULT '0',
  `payment_mode` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_id` (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_online`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_online` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tournament_id` int(11) NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `is_finish` int(11) NOT NULL,
  `info` varchar(255) NOT NULL DEFAULT '00000',
  `order_id` int(11) NOT NULL,
  `start_content` varchar(255) NOT NULL,
  `end_content` varchar(255) NOT NULL,
  `weibo_share_content` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `end_time` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_group_class`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_group_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_count` int(11) NOT NULL,
  `tournament_online_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`,`tournament_online_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_lbs`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_lbs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `country` varchar(255) NOT NULL,
  `locality` varchar(255) NOT NULL,
  `sub_locality` varchar(255) NOT NULL,
  `thoroughfare` varchar(255) NOT NULL,
  `sub_thoroughfare` varchar(255) NOT NULL,
  `device_name` varchar(64) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mac` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1190 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `news`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL,
  `content` text NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='msgofday';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jaguar_rent_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jaguar_rent_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `mobile` varchar(20) COLLATE utf8_bin NOT NULL,
  `email` varchar(128) COLLATE utf8_bin NOT NULL,
  `ip` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `device` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`),
  KEY `name` (`name`),
  KEY `device` (`device`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jaguar_own_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jaguar_own_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `mobile` varchar(20) COLLATE utf8_bin NOT NULL,
  `email` varchar(128) COLLATE utf8_bin NOT NULL,
  `province` varchar(32) COLLATE utf8_bin NOT NULL,
  `city` varchar(32) COLLATE utf8_bin NOT NULL,
  `buy_year` int(11) NOT NULL,
  `buy_season` tinyint(4) NOT NULL,
  `test_area` varchar(64) COLLATE utf8_bin NOT NULL,
  `budget` int(11) NOT NULL,
  `ip` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` bigint(20) NOT NULL COMMENT 'save second',
  `device` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`),
  KEY `name` (`name`),
  KEY `device` (`device`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cheat_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cheat_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `mode_id` int(11) NOT NULL,
  `race_time` float NOT NULL,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `is_race_start` int(11) NOT NULL,
  `reason` varchar(255) COLLATE utf8_bin NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `game_mode` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feed_content`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feed_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(512) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `censor_word`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `censor_word` (
  `content` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='敏感词';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cta_content`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cta_content` (
  `id` int(11) NOT NULL,
  `content` varchar(64) COLLATE utf8_bin NOT NULL,
  `comments` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leaderboard_change_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leaderboard_change_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `mode_id` int(11) NOT NULL,
  `race_time` float NOT NULL,
  `average_speed` float NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `first_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `second_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `third_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mode_id` (`mode_id`),
  KEY `user_id` (`user_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournament_leaderboard_change_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tournament_leaderboard_change_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tournament_online_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `race_time` float NOT NULL,
  `average_speed` float NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `second_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `third_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `first_consumble_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tournament_online_id` (`tournament_online_id`,`group_id`,`class_id`),
  KEY `user_id` (`user_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_init_gold`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_init_gold` (
  `level` int(11) NOT NULL AUTO_INCREMENT,
  `user_count` int(11) NOT NULL,
  `gold` int(11) NOT NULL,
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `race_start_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `race_start_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `first_consumeble_id` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `second_consumeble_id` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `third_consumeble_id` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=432 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_add_gold_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_add_gold_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `add_gold` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `garage_leaderboard`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `garage_leaderboard` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `weibo_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `head_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `car_num` int(11) NOT NULL,
  `car_total_score` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `rank` (`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_expense_rec`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_expense_rec` (
  `user_id` bigint(20) NOT NULL,
  `gold` int(11) NOT NULL DEFAULT '0',
  `r1` tinyint(4) NOT NULL DEFAULT '0',
  `r2` tinyint(4) NOT NULL DEFAULT '0',
  `r3` tinyint(4) NOT NULL DEFAULT '0',
  `r4` tinyint(4) NOT NULL DEFAULT '0',
  `r5` tinyint(4) NOT NULL DEFAULT '0',
  `r6` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operate_batch`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operate_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operate_change_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operate_change_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  `add_money` int(11) NOT NULL,
  `add_gold` int(11) NOT NULL,
  `add_energy` int(11) NOT NULL,
  `add_car` varchar(255) COLLATE utf8_bin NOT NULL,
  `batch_num` int(11) NOT NULL,
  `current_money` int(11) NOT NULL DEFAULT '0',
  `original_money` int(11) NOT NULL DEFAULT '0',
  `current_gold` int(11) NOT NULL DEFAULT '0',
  `original_gold` int(11) NOT NULL DEFAULT '0',
  `current_energy` int(11) NOT NULL DEFAULT '0',
  `original_energy` int(11) NOT NULL DEFAULT '0',
  `add_frag_count` int(11) NOT NULL,
  `fragment_id` int(11) NOT NULL,
  `original_frag_count` int(11) NOT NULL,
  `current_frag_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iap_failture_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iap_failture_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receipt_data` text COLLATE utf8_bin NOT NULL,
  `result` text COLLATE utf8_bin NOT NULL,
  `reason` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user_id` bigint(20) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car_ext`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_ext` (
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `visible` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `price_type` int(11) NOT NULL,
  PRIMARY KEY (`car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operate_activity`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operate_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `career_rp_times` float NOT NULL,
  `tournament_rp_times` float NOT NULL,
  `career_gold_times` float NOT NULL,
  `tournament_gold_times` float NOT NULL,
  `career_money_times` float NOT NULL,
  `tournament_money_times` float NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `is_qa_can_see` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_version_update_reward`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_version_update_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_config`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `content` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_type` (`user_id`,`type`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_refresh_time`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_refresh_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `time` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car_change_time`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_change_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `time` int(11) NOT NULL,
  PRIMARY KEY (`car_id`),
  KEY `id` (`id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spend_activity`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spend_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spend_activity_reward`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spend_activity_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spend_activity_id` int(11) NOT NULL,
  `spend_reward_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `spend_activity_id` (`spend_activity_id`,`spend_reward_id`),
  KEY `spend_activity_id_2` (`spend_activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spend_reward`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spend_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gold_amount` int(11) NOT NULL,
  `add_gold` int(11) NOT NULL,
  `add_money` int(11) NOT NULL,
  `add_energy` int(11) NOT NULL,
  `display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_get_spend_reward_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_get_spend_reward_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `spend_activity_id` int(11) NOT NULL,
  `spend_reward_id` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`spend_activity_id`),
  KEY `user_id_2` (`user_id`,`spend_reward_id`,`spend_activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_spend_activity_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_spend_activity_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `spend_activity_id` int(11) NOT NULL,
  `spend_gold_num` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`spend_activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_car_like`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_car_like` (
  `user_car_id` bigint(20) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_car_like_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_car_like_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_car_id` bigint(20) NOT NULL,
  `liked_user_id` bigint(20) NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `like_id` (`user_id`,`user_car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_report_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_report_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `reported_id` bigint(20) NOT NULL,
  `report_date` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `report_id` (`user_id`,`reported_id`,`report_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_report`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `report_count` int(11) NOT NULL DEFAULT '0',
  `reported_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_race_action`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_race_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `value_id` int(11) NOT NULL DEFAULT '0',
  `value` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `race_action_id` (`user_id`,`value_id`),
  KEY `action_index` (`user_id`,`value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_gotcha`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_gotcha` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `gotcha_id` int(11) NOT NULL,
  `gold` int(11) NOT NULL COMMENT 'total',
  `miss_frag_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_gid` (`user_id`,`gotcha_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_pay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_pay` (
  `user_id` bigint(20) NOT NULL,
  `payment` int(11) NOT NULL,
  `expense_gold` int(11) NOT NULL,
  `expense_money` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_car_fragment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_car_fragment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `fragment_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_fid` (`user_id`,`fragment_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_free_frag_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_free_frag_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `level` int(11) NOT NULL,
  `last_use_time` int(11) NOT NULL,
  `left_times` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`car_id`,`level`)
) ENGINE=InnoDB AUTO_INCREMENT=824 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `buy_car_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buy_car_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `spend_gold_num` int(11) NOT NULL,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `frag_num` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `spend_gold_num` (`spend_gold_num`),
  KEY `car_id` (`car_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gotcha_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gotcha_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `gotcha_id` int(11) NOT NULL,
  `spend_gold_num` int(11) NOT NULL,
  `get_gold_num` int(11) NOT NULL,
  `get_money_num` int(11) NOT NULL,
  `get_gas_num` int(11) NOT NULL,
  `frag_id` int(11) NOT NULL,
  `frag_num` int(11) NOT NULL,
  `time` int(11) NOT NULL,
  `car_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `is_bingo_car` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `gotcha_id` (`gotcha_id`),
  KEY `is_bingo_car` (`is_bingo_car`),
  KEY `car_id` (`car_id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `career_ghost`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `career_ghost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mode_type` int(11) NOT NULL,
  `event_name` varchar(64) NOT NULL,
  `race_time` decimal(10,1) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `car_id` varchar(64) NOT NULL,
  `car_color_index` int(11) NOT NULL,
  `average_speed` float NOT NULL,
  `car_score` int(11) NOT NULL DEFAULT '100',
  `is_gold_car` tinyint(4) NOT NULL,
  `is_has_consumble` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mode_id_2` (`mode_type`,`user_id`),
  KEY `mode_id` (`mode_type`),
  KEY `user_id` (`user_id`),
  KEY `is_gold_car` (`is_gold_car`),
  KEY `mode_id_3` (`mode_type`,`is_gold_car`),
  KEY `id` (`id`,`mode_type`),
  KEY `is_gold_car_2` (`is_gold_car`,`is_has_consumble`),
  KEY `car_color_index` (`car_color_index`,`is_gold_car`),
  KEY `mode_type` (`mode_type`,`race_time`),
  KEY `race_time` (`race_time`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户ghost信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `career_ghost_mod`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `career_ghost_mod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ghost_id` bigint(20) NOT NULL,
  `mode_type` int(11) NOT NULL,
  `mode_value` float NOT NULL,
  `mode_level` int(11) NOT NULL DEFAULT '0',
  `mode_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ghost_id` (`ghost_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile_track_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_track_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `viewed_user_id` bigint(20) NOT NULL,
  `page_id` int(11) NOT NULL,
  `source_id` int(11) DEFAULT NULL,
  `tournament_online_id` int(11) DEFAULT NULL,
  `tournament_group_id` int(11) DEFAULT NULL,
  `car_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cheat_user_info_record`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cheat_user_info_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `cheat_type` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `car_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `middle_gear_speed` int(11) NOT NULL,
  `top_gear_speed` int(11) NOT NULL,
  `md5` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_select_car_id` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `cheat_type` (`cheat_type`),
  KEY `version` (`version`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_config`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '变量名',
  `value` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-04 11:37:22
