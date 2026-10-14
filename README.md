# Client-Server Subsystems (Audio Streaming Backend)

A distributed backend for an audio streaming service, built for the
*Information Systems 1* course at ETF Belgrade. A central REST server routes
client requests to three independent subsystems over **JMS** queues; each
subsystem owns its own MySQL database and accesses it through **JPA**.

## Architecture

```
Client (console)
     │  HTTP
     ▼
Central server (JAX-RS, Payara/GlassFish)
     │  JMS queues
     ├──► Subsystem 1 — users and cities
     ├──► Subsystem 2 — audio recordings and categories
     └──► Subsystem 3 — subscription packages, subscriptions, listening history,
                         ratings and favourites
```

## Tech

Java EE 8, JAX-RS, JMS, JPA (EclipseLink), MySQL, Maven / Ant, NetBeans

## Structure

```
javaaplikacije/ServerCentralni   REST endpoints, one resource per subsystem
javaaplikacije/Podsistem1,2,31   JMS application clients with JPA entities
javaaplikacije/Klijent           console client
baze/                            SQL schema
umlklase/, umlsekvenca/          UML class and sequence diagrams
```

## Setup

1. Create the databases from `baze/baze.sql`.
2. Set the MySQL user and password in each `persistence.xml` (placeholders in the repo).
3. Create the JMS connection factory and queues on the application server,
   deploy `ServerCentralni` and start the subsystem clients.
