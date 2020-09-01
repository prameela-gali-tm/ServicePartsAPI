# Following variables must be added to deploys/params/${env}/variables.tfvars
variable "ecs_cluster_id" {
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

