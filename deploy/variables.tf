# Following variables must be added to deploys/params/${env}/variables.tfvars
variable "ecs_cluster_id" {
}
variable "app_name" {
  default="scs-parts"
}

variable "aws_region" {
}

variable "aws_profile" {
}

# Following variables are injected by the pipeline code and need not be defined in deploys/params/${env}/variables.tfvars
variable "image_tag" {
}

variable "artifactory_user" {
}

variable "artifactory_password" {
}

variable "env" {
}

# The value of source_image_path will be the same which is passed in Jenkinsfile (ciMavenDocker entrypoint) docker.tag
variable "source_image_path" {
  description = "The value of source_image_tag will be the same which is passed in Jenkinsfile (ciMavenDocker entrypoint) docker.tag"
  default     = "tdp/tdp-samples-maven-docker-tf"
}

# Pass the ECR repo path relative to the ecr url. For eg if the repo url is 1234567890.dkr.ecr.us-west-2.amazonaws.com/tdp/samples/maven-docker-hello-world, target_image_path will be tdp/samples/maven-docker-hello-world
variable "target_image_path" {
  description = "Pass the ECR repo path relative to the ecr url. For eg if the repo url is 1234567890.dkr.ecr.us-west-2.amazonaws.com/tdp/samples/maven-docker-hello-world, target_image_path will be tdp/samples/maven-docker-hello-world"
  default     = "tdp/maven-docker-hello-world"
}

variable "tools_domain_name" {
  description = "Tools base domain"
}

variable "application_id" {
}

variable "application_name" {
}

variable "project_name" {
}

variable "bu" {
}

variable "dept_id" {
}

variable "core_id" {
}

variable "project_id" {
}

variable "cost_center" {
}

variable "created_by" {
}

variable "terraform_scriptversion" {
}

variable "vpc_id" {
}

variable "load_balance_subnet_id" {
   type        = "list"
  default     = []
}
variable "rds_private_subnet_id" {
   type        = "list"
  default     = []
}
variable "app_private_subnet_id" {
   type        = "list"
  default     = []
}
variable "alb_internal" {
  default     = ""
  description = "the loadbalancer is internal or external"
}

variable "alb_idle_timeout" {
  default     = ""
  description = "the idle timeout for loadbalancer"
}

############################################################

#RDS postgresql settings

#RDS Properties
variable "rds_instance_class" {
  default     = ""
  description = "Instance class type of database"
}

variable "rds_instance_engine" {
  description = "Instance engine type of database"
  default     = ""
}

variable "rds_instance_engine_version" {
  description = "Instance engine version of database"
  default     = ""
}

variable "rds_instance_database_name" {
  description = "Instance database name "
  default     = ""
}

variable "rds_instance_username" {
  description = "Instance database username "
  default     = ""
}
variable "rds_instance_password" {
  description = "Instance database username "
  default     = ""
}

variable "rds_instance_cluster_identifier" {
  description = "Whether the DB should have a public IP address"
  default     = ""
}

variable "rds_instance_skip_final_snapshot" {
  default     = ""
  description = "Should a final snapshot be created on cluster destroy"
}

variable "rds_instance_preferred_backup_window" {
  default     = ""
  description = "When to perform DB backups"
}

variable "rds_instance_preferred_maintenance_window" {
  default     = ""
  description = "When to perform DB maintenance"
}

variable "rds_instance_port" {
  default     = ""
  description = "The port on which to accept connections"
}

variable "rds_instance_apply_immediately" {
  default     = ""
  description = "Determines whether or not any DB modifications are applied immediately, or during the maintenance window"
}

variable "rds_instance_parameter_group_name" {
  default     = ""
  description = "The name of a DB Cluster parameter group to use"
}

variable "rds_instance_count" {
  default     = ""
  description = "Specifies the database instance count"
}

variable "rds_instance_iam_database_authentication_enabled" {
  default     = ""
  description = "Whether to enable IAM database authentication for the RDS Cluster"
}

variable "backup_retention_period" {
  default     = ""
  description = "backup retention perioda in days"
}

variable "deletion_protection" {
  default     = ""
  description = "boolean for deletion protection"
}