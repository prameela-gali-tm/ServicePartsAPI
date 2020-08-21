resource "aws_iam_role" "default_role" {
  name = "default_role"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF

  
}
resource "aws_iam_role_policy" "default" {
  name = "default"
  role = aws_iam_role.default_role.id

  policy = <<-EOF
  {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Action": [
          "ec2:Describe*"
        ],
        "Effect": "Allow",
        "Resource": "*"
      }
    ]
  }
  EOF
}
resource "aws_ecs_service" "default" {
  name            = "${var.env}-opa"
  cluster         = "${var.env}-main"
  task_definition = aws_ecs_task_definition.default.arn
  desired_count   = 1
  iam_role        = aws_iam_role.task_role.arn
  depends_on      = [aws_iam_role_policy.default]

  ordered_placement_strategy {
    type  = "binpack"
    field = "cpu"
  }

  load_balancer {
   # target_group_arn = aws_lb_target_group.default.arn
    container_name   = "opa-container"
    container_port   = 8080
  }
 
}
resource "aws_ecr_repository" "default" {
  name = "tscs/opa"
}
resource "aws_ecs_task_definition" "default" {
  family        = "${var.env}-opa-task"
  task_role_arn = aws_iam_role.task_role.arn

  container_definitions = <<EOF
[
  {
    "name": "opa-container",
    "image": "${aws_ecr_repository.default.repository_url}:${var.image_tag}",
    "essential": true,
    "memoryReservation": 128,
    "memory": 128,
    "portMappings":[
      {
        "containerPort": 8181,
        "hostPort": 8181
      }
    ],
    "logConfiguration": {
      "logDriver": "awslogs",
      "options": {
        "awslogs-group": "${var.env}-opa-log-group",
        "awslogs-region": "${var.aws_region}",
        "awslogs-stream-prefix": "${var.env}-opa"
      }
    }
  }
]
  EOF

  
}
resource "aws_cloudwatch_log_group" "default" {
  name              = "${var.env}-scs-log-group"
  retention_in_days = 14                                                # Retain logs only for XX days
  
}

########################################################################################################################
# Load Balancer
########################################################################################################################

#############################
# Load Balancer Listener Rule
/* resource "aws_alb_listener_rule" "https_private_route_path" {
  listener_arn = data.aws_lb_listener.private_alb_443_listener.arn
  priority     = 16

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.default.arn
  }

  condition {
    host_header {
      values = ["opa.*"]
    }
  }
}

#############################
# Load Balancer Target Group
resource "aws_lb_target_group" "default" {
  name     = "${var.env}-opa-tg"
  port     = 5000
  protocol = "HTTP"
  vpc_id   = data.aws_vpc.default.id

  deregistration_delay = 60

  health_check {
    timeout             = 5
    interval            = 15
    healthy_threshold   = 2
    unhealthy_threshold = 2
    path                = "/health"
    matcher             = "200,302"
  }

 
}

#############################
# R53 enrty

resource "aws_route53_record" "default" {
  zone_id = data.aws_route53_zone.private.zone_id
  name    = "opa.${var.tools_domain_name}"
  type    = "A"

  alias {
    name                   = "dualstack.${data.aws_lb.private.dns_name}"
    zone_id                = data.aws_lb.private.zone_id
    evaluate_target_health = false
  }
} */