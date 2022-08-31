git pull
docker build -t reservation-backend .
docker tag reservation-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/reservation-backend:latest
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin 811288377093.dkr.ecr.us-west-2.amazonaws.com/
docker push 811288377093.dkr.ecr.us-west-2.amazonaws.com/reservation-backend:latest
kubectl delete -f manifests/reservation-deployment.yaml
kubectl apply -f manifests/reservation-deployment.yaml
kubectl get pod
kubectl apply -f manifests/reservation-service.yaml
kubectl get service