aws_region = "us-west-2"

ecs_cluster_id = "service-parts"

application_id = "BS201828014"

application_name = "SCS Service Parts"

project_name = "SCS"

bu = "ATD"

dept_id = "00001"

core_id = "00001"

project_id = "00001"

cost_center = "00001"

created_by = "TDP"

terraform_scriptversion = "NA"

env = "dev"

tools_domain_name = "dev-tmna-devops.com"

aws_profile = "AWS-TMNA-SubProd"

vpc_id="vpc-0369d880f6debee29"

load_balance_subnet_id = ["subnet-0159ce27d8fdc7583", "subnet-07c1afe035fc98aa4"]
rds_private_subnet_id = ["subnet-0985d3868f57106a7", "subnet-0b4e04f041a715e5f"]
app_private_subnet_id = ["subnet-0c1e341b5321d36dc", "subnet-0fe5c390ba31d5f3c"]
alb_internal = "false"

alb_idle_timeout = "50"

#RDS Properties
rds_instance_class = "db.r5.4large"

rds_instance_engine = "aurora"

rds_instance_engine_version = "11.6"

rds_instance_database_name = "scsdevdb"

rds_instance_username = "root"
rds_instance_password ="password"

rds_instance_preferred_backup_window = "04:00-05:00"

rds_instance_preferred_maintenance_window = "Sun:00:00-Sun:03:00"

rds_instance_port = "3306"

rds_instance_apply_immediately = "true"

rds_instance_parameter_group_name = "default.aurora11.6"

rds_instance_count = "2"

rds_instance_iam_database_authentication_enabled = "false"

rds_instance_cluster_identifier = "mtmus-sbox-usw2-rdscr"

rds_instance_skip_final_snapshot = "true"