@Library('tmna-job-library@release/2.0') _
ciMavenDocker {
  docker                  = [file: "Dockerfile", tag: "scs/service-parts-api"]
  deploymentScripts       = [type: "deployTerraformArtifact", location: "deploy"]
  envMapping = ["feature":["dev"], "develop":["subprod"], "release":["subprod"], "required":true]
  buildNotificationEmails = "LIOS-DevOpsEngineer@internal.toyota.com"
}