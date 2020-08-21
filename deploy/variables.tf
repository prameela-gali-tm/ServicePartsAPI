variable "aws_profile" {
  description = "AWS Credentials Profile"
}

variable "aws_region" {
  description = "EC2 Region for the VPC"
}

variable "env" {
  description = "Environment name"
}

variable "tools_domain_name" {
  description = "Tools base domain"
}

variable "image_tag" {}
variable "artifactory_user" {}
variable "artifactory_password" {}