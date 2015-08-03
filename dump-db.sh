#!/bin/bash
if [ "$DUMP_OPTS" == "" ]
then
	echo -e "Please set DUMP_OPTS enviroument variable\n\teg. DUMP_OPTS=-h127.0.0.1 -P10000 -uxxx -pxxx database $0"
	exit 1
fi

FIND_MYSQLDUMP=$(which mysqldump)
if [ "$FIND_MYSQLDUMP" == "" ]
then
	#Try MacOSX
	export PATH=$PATH:/Applications/MySQLWorkbench.app/Contents/Resources
	FIND_MYSQLDUMP=$(which mysqldump)
fi
if [ "$FIND_MYSQLDUMP" == "" ]
then
	echo -e "Cannot find mysqldump command.\nPlease set PATH variables"
	exit 2
fi



dict='car finish_ratio race_mode race_mode_unlock race_reward reward rp_level tier_mode tournament tournament_group tournament_reward track track_car_type car_limit car_slot car_chartlet car_slot_consumable  daily_race_reward tournament_car_limit purchase tier_car_limit   event_option_override daily_race_car_id daily_race_mode_id   advertise  hints    purchase_gift  car_max_speed mode_distance career_standard_result expense_reward finish_ratio_addition gotcha_car gotcha_expense gotcha_formula gotcha_fragment gotcha_ratio iap_rmb_num profile_comparison mode_standard_result mode_modifier' 


mysqldump $DUMP_OPTS ${dict} > src/main/sql/dicttable.sql



usertable='user_ghost user_ghost_mod leaderboard tournament_ghost tournament_ghost_mod tournament_user user user_car user_track user_session user_car_slot user_chartlet career_best_racetime_record tournament_leaderboard user_daily_race purchase_record resource resource_version daily_race_record request_log iap_check_info tournament_online tournament_group_class user_lbs news jaguar_rent_info jaguar_own_info cheat_record feed_content censor_word cta_content leaderboard_change_record tournament_leaderboard_change_record user_init_gold  race_start_record user_add_gold_record garage_leaderboard user_expense_rec operate_batch operate_change_record iap_failture_record car_ext operate_activity user_version_update_reward user_config user_refresh_time car_change_time spend_activity spend_activity_reward spend_reward user_get_spend_reward_record user_spend_activity_record user_car_like user_car_like_log user_report_log user_report user_race_action user_gotcha user_pay user_car_fragment user_free_frag_record buy_car_record gotcha_record career_ghost career_ghost_mod profile_track_log cheat_user_info_record system_config'


mysqldump $DUMP_OPTS -d --add-drop-table=false ${usertable} > src/main/sql/usertable.sql 

