# GDsmith

We propose GDsmith, to the best of our knowledge the first black-box approach for testing graph database engines.

# Getting Started

Requirements:
* Java 11
* [Maven](https://maven.apache.org/)
* The graph database engines that you want to test (now supporting Neo4j, RedisGraph, and Memgraph)

# Using GDsmith

The configuration file is: 
```
config.json
```
The example args are:
```
--num-threads 1 --num-queries 10000 composite
```
