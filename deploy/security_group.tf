resource "aws_security_group" "alb" {
  name   = "${var.env}-sg-alb-${var.app_name}"
  vpc_id = var.vpc_id

  ingress {
    protocol         = "tcp"
    from_port        = 80
    to_port          = 80
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  ingress {
    protocol         = "tcp"
    from_port        = 443
    to_port          = 443
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    protocol         = "-1"
    from_port        = 0
    to_port          = 0
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

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

resource "aws_security_group" "ecs_tasks" {
  name   = "${var.env}-sg-task-${var.app_name}"
  vpc_id = var.vpc_id

  ingress {
    protocol         = "tcp"
    from_port        = 8080
    to_port          = 8080
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    protocol         = "-1"
    from_port        = 0
    to_port          = 0
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

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

output "alb" {
  value = aws_security_group.alb.id
}

output "ecs_tasks" {
  value = aws_security_group.ecs_tasks.id
}