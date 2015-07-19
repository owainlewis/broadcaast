echo 'Deploying application'

sbt docker:stage

cd target/docker/stage
zip -r discusslr.zip *

echo 'Done'
