aws_region = "us-west-2"

ecs_cluster_id = "service-parts"

application_id = "BS201828014"

application_name = "SCS Service Parts"

project_name = "SCS"
ecs_task_iam_role="ecs-role"
ecs_task_iam_policy="ecs-policy"
ecs_task_iam_policy_autoscaling="ecs-autoscale-policy"
bu = "ATD"

dept_id = "955042"

core_id = "00001"

project_id = "NIS01338"

cost_center = "00001"

created_by = "SCS"

terraform_scriptversion = "NA"

env = "dev"

tools_domain_name = "dev-tmna-devops.com"

aws_profile = "AWS-TMNA-SubProd"
fargate_cpu=256
fargate_memory=512
source_image_path="lios/service-parts-api"
vpc_id="vpc-0369d880f6debee29"


load_balance_subnet_id = ["subnet-0159ce27d8fdc7583", "subnet-07c1afe035fc98aa4"]
rds_private_subnet_id = ["subnet-0985d3868f57106a7", "subnet-0b4e04f041a715e5f"]
app_private_subnet_id = ["subnet-0c1e341b5321d36dc", "subnet-0fe5c390ba31d5f3c"]

app_cidr = ["10.57.163.64/26", "10.57.163.64/26"]
lb_cidr = ["10.57.163.192/27", "10.57.163.224/27"]
rds_cidr = ["10.57.163.0/27", "10.57.163.32/27"]

alb_internal = "false"

alb_idle_timeout = "50"

#RDS Properties
rds_instance_class = "db.r4.large"

rds_instance_engine = "aurora-postgresql"

rds_instance_engine_version = "10.4"

rds_instance_database_name = "SCSServicePartsDB"

rds_instance_username = "root"
rds_instance_password ="password"

rds_instance_preferred_backup_window = "04:00-05:00"

rds_instance_preferred_maintenance_window = "Sun:00:00-Sun:03:00"

rds_instance_port = "5432"

rds_instance_apply_immediately = "true"

rds_instance_parameter_group_name = "default.aurora11.6"

rds_instance_count = "1"

rds_instance_iam_database_authentication_enabled = "false"

rds_instance_cluster_identifier = "tscs-dev-rdscr"

rds_instance_skip_final_snapshot = "true"
rds_instance_publicly_accessible="true"

backup_retention_period			  = "5"
deletion_protection				  = "false"