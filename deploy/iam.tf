# IAM Role for Task
resource "aws_iam_role" "task_role" {
  name               = "${var.env}-${var.aws_region}-opa-task-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
  
}

# IAM Policy Document for Assuming Role
data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      identifiers = [
        "ecs-tasks.amazonaws.com",
      ]

      type = "Service"
    }

    actions = [
      "sts:AssumeRole",
    ]
  }
}