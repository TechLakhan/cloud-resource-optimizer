# Cloud Resource Optimizer (WIP)

Microservice-based platform for monitoring cloud & Kubernetes resources with
secure access, cost estimation, and observability.

## Architecture (In Progress)
- API Gateway (Spring Cloud Gateway)
- Auth Service (JWT + Role-based Authorization)
- Resource Monitoring Service
- Config Server
- Eureka Service Registry

## Features Implemented
- JWT authentication & role-based authorization
- Secure header propagation between services
- Cluster, Node, Pod, Namespace & Workload monitoring APIs
- Centralized configuration
- Service discovery

## Upcoming
- Cost estimation engine
- Kafka for async events
- Redis caching
- Prometheus & Grafana
- ELK stack
- Kubernetes deployment

> 🚧 Actively under development