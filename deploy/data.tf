data "aws_vpc" "default" {
  tags = {
    Env = var.env
  }
}

data "aws_lb" "public" {
  name = "${var.env}-${var.aws_region}"
}

data "aws_route53_zone" "public" {
  name         = "${var.tools_domain_name}."
  private_zone = false
}

data "aws_ssm_parameter" "aws_lb_listener_https_arn" {
  name = "/${var.env}/aws_lb_listener_https_arn"
}

data "aws_caller_identity" "current" {
}

