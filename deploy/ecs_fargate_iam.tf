 resource "aws_iam_role" "resource_aws_iam_role" {
  name = "${format("%s-%s",var.project_name, var.ecs_task_iam_role)}"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "resource_aws_iam_role_policy" {
  name = "${format("%s-%s",var.project_name, var.ecs_task_iam_policy)}"
  role = "${aws_iam_role.resource_aws_iam_role.id}"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ecr:GetAuthorizationToken",
                "ecr:BatchCheckLayerAvailability",
                "ecr:GetDownloadUrlForLayer",
                "ecr:BatchGetImage",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Resource": "*"
        }
    ]
}
EOF
}

resource "aws_iam_role_policy" "resource_aws_iam_role_policy_autoscaling" {
  name = "${format("%s-%s",var.project_name, var.ecs_task_iam_policy_autoscaling)}"
  role = "${aws_iam_role.resource_aws_iam_role.id}"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {            
            "Effect": "Allow",
            "Action": [
                "ecs:DescribeServices",
                "ecs:UpdateService"
            ],
            "Resource": [
                "*"
            ]
        },
        {            
            "Effect": "Allow",
            "Action": [
                "cloudwatch:DescribeAlarms"
            ],
            "Resource": [
                "*"
            ]
        }
    ]
}
EOF
}
 
resource "aws_iam_role" "resource_aws_iam_service_role" {
  name = "${format("%s-%s-%s",var.project_name, var.ecs_task_iam_role)}"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

/* resource "aws_iam_role_policy" "resource_aws_iam_service_role_policy" {
  name   = "${format("%s-%s-%s",var.project_name, var.ecs_task_iam_policy, var.env)}"
  role   = "${aws_iam_role.resource_aws_iam_service_role.id}"
  policy = "${file(var.module_ecs_task_role_policy_document)}"
} */