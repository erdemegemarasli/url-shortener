REPLACE INTO `roles` VALUES (1, 'ADMIN');
REPLACE INTO `roles` VALUES (2, 'USER');
REPLACE INTO `businesses` VALUES (1, 'Anonymous', 'Trial', 50, 0);
REPLACE INTO `users` VALUES (1, '$2a$10$xq.zWjR8hvxp1H5hX0T.qO0AMWUmO/iZaeYzvTdsCVByOLwsLV7oa', 0, 'abcabcabc' , 1);
REPLACE INTO `user_role` VALUES (1, 1);