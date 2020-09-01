provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile
}

# Backend configurations are loaded from deploys/params/${env}/backend.tfvars
# Please ensure to add 'bucket', 'region' and 'key' in backend.tfvars
terraform {
  required_version = ">= 0.12.20"
  backend "s3" {
  }
}

# The following module will download Docker image from Artifactory and 
module "ecr_sync" {
  source               = "git::https://bitbucket.sdlc.toyota.com/scm/tdp/terraform-module-ecr-copy.git?ref=0.12"
  artifactory_user     = var.artifactory_user
  artifactory_password = var.artifactory_password
  source_image_url     = "docker-dev.artifactory.tmna-devops.com/${var.source_image_path}"
  target_image_url     = "${data.aws_caller_identity.current.account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.target_image_path}"
  image_tag            = var.image_tag
  aws_region           = var.aws_region
}

resource "aws_cloudwatch_log_group" "scs_service_parts_api" {
  name              = "${var.env}-scs-service-parts-api-log-group"
  retention_in_days = 1 # Retain samples log for just one day

  tags = {
    ApplicationId          = var.application_id
    ApplicationName        = var.application_name
    ProjectName            = var.project_name
    BU                     = var.bu
    DeptID                 = var.dept_id
    CoreID                 = var.core_id
    ProjectID              = var.project_id
    CostCenter             = var.cost_center
    CreatedBy              = var.created_by
    TerraformScriptVersion = var.terraform_scriptversion
    Env                    = var.env
  }
}

resource "aws_ecs_task_definition" "scs_service_parts_api" {
  family = "maven-docker-hello-world"

  depends_on = [aws_cloudwatch_log_group.scs_service_parts_api]

  tags = {
    ApplicationId          = var.application_id
    ApplicationName        = var.application_name
    ProjectName            = var.project_name
    BU                     = var.bu
    DeptID                 = var.dept_id
    CoreID                 = var.core_id
    ProjectID              = var.project_id
    CostCenter             = var.cost_center
    CreatedBy              = var.created_by
    TerraformScriptVersion = var.terraform_scriptversion
    Env                    = var.env
  }

  container_definitions = <<EOF
[
  {
    "name": "maven-docker-hello-world",
    "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.target_image_path}:${var.image_tag}",
    "essential": true,
    "memoryReservation": 256,
    "portMappings":[
      {
        "containerPort": 8080,
        "hostPort": 8085
      }
    ],
    "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "${var.env}-scs-service-parts-api-log-group",
          "awslogs-region": "${var.aws_region}",
          "awslogs-stream-prefix": "scs-service-parts-api-tf"
        }
    }
  }
]
  
EOF

}

resource "aws_ecs_service" "scs_service_parts_api" {
  name            = "${var.env}-maven-docker-hello-world"
  cluster         = var.ecs_cluster_id
  task_definition = aws_ecs_task_definition.scs_service_parts_api.arn
  desired_count   = "1"

  tags = {
    ApplicationId          = var.application_id
    ApplicationName        = var.application_name
    ProjectName            = var.project_name
    BU                     = var.bu
    DeptID                 = var.dept_id
    CoreID                 = var.core_id
    ProjectID              = var.project_id
    CostCenter             = var.cost_center
    CreatedBy              = var.created_by
    TerraformScriptVersion = var.terraform_scriptversion
    Env                    = var.env
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.scsserviceparts_tg.arn
    container_name   = "maven-docker-hello-world"
    container_port   = 8080
  }
}

resource "aws_ecr_repository" "scs_service_parts_api" {
  name = var.target_image_path

  tags = {
    ApplicationId          = var.application_id
    ApplicationName        = var.application_name
    ProjectName            = var.project_name
    BU                     = var.bu
    DeptID                 = var.dept_id
    CoreID                 = var.core_id
    ProjectID              = var.project_id
    CostCenter             = var.cost_center
    CreatedBy              = var.created_by
    TerraformScriptVersion = var.terraform_scriptversion
    Env                    = var.env
  }
}

########################################################################################################################
# Load Balancer Target Groups
########################################################################################################################
#############################
# Port Based Load Balancer Target Group
resource "aws_lb_target_group" "scsserviceparts_tg" {
  name     = "maven-docker-hello-world-tgt"
  port     = "8085"
  protocol = "HTTP"
  vpc_id   = data.aws_vpc.default.id

  tags = {
    ApplicationId          = var.application_id
    ApplicationName        = var.application_name
    ProjectName            = var.project_name
    BU                     = var.bu
    DeptID                 = var.dept_id
    CoreID                 = var.core_id
    ProjectID              = var.project_id
    CostCenter             = var.cost_center
    CreatedBy              = var.created_by
    TerraformScriptVersion = var.terraform_scriptversion
    Env                    = var.env
  }

  health_check {
    timeout             = 5
    interval            = 15
    path                = "/"
    port                = 8085
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  deregistration_delay = 60
}

#############################
resource "aws_alb_listener_rule" "https_route_path" {
  listener_arn = data.aws_ssm_parameter.aws_lb_listener_https_arn.value
  priority     = 51

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.scsserviceparts_tg.arn
  }

  /* condition {
    field = "host-header"

    values = [
      "tdp-sample-apiapp.*",
    ]
  } */
}

#############################
# R53 enrty
resource "aws_route53_record" "api_scsserviceparts_dns" {
  zone_id = data.aws_route53_zone.public.zone_id
  name    = "tdp-sample-apiapp.${var.tools_domain_name}"
  type    = "A"

  alias {
    name                   = "dualstack.${data.aws_lb.public.dns_name}"
    zone_id                = data.aws_lb.public.zone_id
    evaluate_target_health = false
  }
}

