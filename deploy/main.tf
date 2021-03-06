provider "aws" {
  region  = var.aws_region
}

# Backend configurations are loaded from deploys/params/${env}/backend.tfvars
# Please ensure to add 'bucket', 'region' and 'key' in backend.tfvars
terraform {
  required_version = ">= 0.12.20"
  backend "s3" {
  }
}

# The following module will download Docker image from Artifactory and 
module "ecr_sync"  {
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
  family = "${var.env}-${var.app_name}"

  depends_on = [aws_cloudwatch_log_group.scs_service_parts_api,module.ecr_sync]
  requires_compatibilities= ["FARGATE"]
  task_role_arn            = "${aws_iam_role.resource_aws_iam_role.arn}"
  execution_role_arn       = "${aws_iam_role.resource_aws_iam_role.arn}"
  network_mode ="awsvpc"
  cpu                      = "${var.fargate_cpu}"
  memory                   = "${var.fargate_memory}"
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
    PUSHDate="12"
  }

  container_definitions = <<EOF
[
  {
    "name": "${var.env}-${var.app_name}-container",
    "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.target_image_path}:${var.image_tag}",
    "essential": true,
    "cpu": ${var.fargate_cpu},
    "memory": ${var.fargate_memory},
    "networkMode": "awsvpc",
    "compatibilities": "FARGATE",
    "portMappings":[
      {
        "containerPort": 8080,
        "hostPort": 8080
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
  name            = "${var.env}-${var.app_name}-maven"
  cluster         = aws_ecs_cluster.scs_service_parts_api.id
  task_definition = aws_ecs_task_definition.scs_service_parts_api.arn
  desired_count   = "1"
  launch_type     = "FARGATE"
  depends_on = [aws_cloudwatch_log_group.scs_service_parts_api,module.ecr_sync,aws_ecs_task_definition.scs_service_parts_api,aws_ecs_cluster.scs_service_parts_api,aws_security_group.ecs_tasks]
  /* tags = {
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
  } */

    load_balancer {
    target_group_arn = aws_alb_target_group.load_balancer.arn
    container_name   = "${var.env}-${var.app_name}-container"
    container_port   = 8080
  }  
  network_configuration{
    subnets=var.app_private_subnet_id
    security_groups=[aws_security_group.ecs_tasks.id]
  }
}
resource "aws_ecs_cluster" "scs_service_parts_api" {
  name = "${var.env}-cluster-${var.app_name}"
  depends_on=[]
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
resource "aws_ecr_lifecycle_policy" "scs_service_parts_api" {
  repository = aws_ecr_repository.scs_service_parts_api.name
 
  policy = jsonencode({
   rules = [{
     rulePriority = 1
     description  = "keep last 5 images"
     action       = {
       type = "expire"
     }
     selection     = {
       tagStatus   = "any"
       countType   = "imageCountMoreThan"
       countNumber = 5
     }
   }]
  })
}
resource "aws_appautoscaling_target" "ecs_target" {
  max_capacity       = 4
  min_capacity       = 1
  resource_id        = "service/${aws_ecs_cluster.scs_service_parts_api.name}/${aws_ecs_service.scs_service_parts_api.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

########################################################################################################################
# Load Balancer Target Groups
########################################################################################################################
#############################
  resource "aws_lb" "load_balancer" {
  name                              = "${var.env}-${var.app_name}-lb"
  load_balancer_type                = "application"
  subnets         = var.load_balance_subnet_id
  internal        = var.alb_internal
  security_groups = [aws_security_group.alb.id]

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

resource "aws_alb_target_group" "load_balancer" {
  name        = "${var.env}-tg-${var.app_name}"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"
 
  health_check {
   healthy_threshold   = "3"
   interval            = "90"
   protocol            = "HTTP"
   matcher             = "200-299"
   timeout             = "15"
   path                = "/"
   unhealthy_threshold = "2"
  }
  depends_on=[aws_lb.load_balancer]
}


resource "aws_alb_listener" "http" {
  load_balancer_arn = aws_lb.load_balancer.id
  port              = 80
  protocol          = "HTTP"
  default_action {
    target_group_arn = aws_alb_target_group.load_balancer.id
    type             = "forward"
  }
 /*  default_action {
   type = "redirect"
 
   redirect {
     port        = 443
     protocol    = "HTTPS"
     status_code = "HTTP_301"
   }
  } */
}
 
/* resource "aws_alb_listener" "https" {
  load_balancer_arn = aws_lb.load_balancer.id
  port              = 443
  protocol          = "HTTPS"
 
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.alb_tls_cert_arn
 
  default_action {
    target_group_arn = aws_alb_target_group.load_balancer.id
    type             = "forward"
  }
} */

# Port Based Load Balancer Target Group
/* resource "aws_lb_target_group" "scsserviceparts_tg" {
  name     = "${var.env}-${var.app_name}-tgt"
  port     = "8080"
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  target_type = "instance"
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
    port                = 8080
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  deregistration_delay = 60
}

#############################
resource "aws_lb_listener_rule" "https_route_path" { 
  listener_arn = aws_lb.load_balancer.arn
  
  depends_on =[aws_lb_target_group.scsserviceparts_tg,aws_lb.load_balancer]
  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.scsserviceparts_tg.arn
  }
condition {
    host_header {
      values = ["/*"]
    }
  }

  # condition {
   # field = "host-header"

    #values = [
    #  "tdp-sample-apiapp.*",
    #]
  #} 
}  
 */
#############################
# R53 enrty
/* resource "aws_route53_record" "api_scsserviceparts_dns" {
  zone_id = data.aws_route53_zone.public.zone_id
  name    = "scs-apiapp.${var.tools_domain_name}"
  type    = "A"

  alias {
    name                   = "dualstack.${data.aws_lb.public.dns_name}"
    zone_id                = data.aws_lb.public.zone_id
    evaluate_target_health = false
  }
} */

#RDS

resource "aws_db_subnet_group" "scsserviceparts-sbg" {
  subnet_ids = var.rds_private_subnet_id
  name       = "${var.env}-${var.app_name}-rds-subnet"

  
}

resource "aws_rds_cluster" "scsserviceparts-rdscr" {
  cluster_identifier = "${var.rds_instance_cluster_identifier}"

  #cluster_identifier      			= "aurora-cluster-mysql6"
  engine         = "${var.rds_instance_engine}"
  engine_version = "${var.rds_instance_engine_version}"

  #availability_zones     				= "${var.rds_instance_availability_zones}"
  database_name                       = "${var.rds_instance_database_name}"
  master_username                     = "${var.rds_instance_username}"
  master_password                     = "${var.rds_instance_password}"
 # master_password                     = "${data.aws_ssm_parameter.mtmus-sandbox-root.value}"
  db_subnet_group_name                = "${aws_db_subnet_group.scsserviceparts-sbg.name}"
  vpc_security_group_ids              = ["${aws_security_group.rds-sg.id}"]
  apply_immediately                   = "${var.rds_instance_apply_immediately}"
  preferred_backup_window             = "${var.rds_instance_preferred_backup_window}"
  preferred_maintenance_window        = "${var.rds_instance_preferred_maintenance_window}"
 # db_cluster_parameter_group_name     = "${var.rds_instance_parameter_group_name}"
  iam_database_authentication_enabled = "${var.rds_instance_iam_database_authentication_enabled}"
  skip_final_snapshot                 = "${var.rds_instance_skip_final_snapshot}"
  backup_retention_period			  = "${var.backup_retention_period}"
  deletion_protection				  = "${var.deletion_protection}"
  #final_snapshot_identifier 			= "${var.rds_instance_snapshot_identifier}"
  
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
  depends_on=[aws_security_group.rds-sg]
}

output "rds_endpoint" {
  value = "${aws_rds_cluster.scsserviceparts-rdscr.endpoint}"
}

# Aurora postgresql Creation
# 4.cluster instance creation
resource "aws_rds_cluster_instance" "scsserviceparts-rds" {
  count                        = "${var.rds_instance_count}"
  engine         = "${var.rds_instance_engine}"
  engine_version = "${var.rds_instance_engine_version}"
  identifier                   = "${var.rds_instance_cluster_identifier}-${count.index}"
  cluster_identifier           = "${aws_rds_cluster.scsserviceparts-rdscr.id}"
  instance_class               = "${var.rds_instance_class}"
  preferred_maintenance_window = "${var.rds_instance_preferred_maintenance_window}"
  publicly_accessible 				= "${var.rds_instance_publicly_accessible}"
  depends_on                   = [aws_rds_cluster.scsserviceparts-rdscr]
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


