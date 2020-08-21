@Library('tmna-job-library@release/2.0') _
ciMavenDocker {
  docker                  = [file: "Dockerfile", tag: "lios/service-parts-api"]
  deploymentScripts       = [type: "deployTerraformArtifact", location: "deploy"]
  envMapping = ["feature":["subprod"], "develop":["subprod"], "release":["subprod"], "required":false]
  buildNotificationEmails = "LIOS-DevOpsEngineer@internal.toyota.com"
}