**Cassandra REST API for Grafana dashboard**

[![Build Status](https://travis-ci.org/2dmitrypavlov/cassandra2grafana.svg?branch=master)](https://travis-ci.org/2dmitrypavlov/cassandra2grafana)
 
 # Env
 
 You can configure app by setting following environment variables:

 	# `SERVER_HOST` – Server host (default: 'localhost')
 	# `SERVER_PORT` – Server port (default: 8080)
    # `CASSANDRA_USERNAME`  – Cassandra user name (default: `cassandra`)
 	# `CASSANDRA_PASSWROD` – Cassandra user password (default: 'cassandra')
 	# `CASSANDRA_HOSTS` – Cassandra hosts in one string separated by ','. Example 'localhost1, localhost2' (default: `localhost`)
 	# 'CASSANDRA_PORT' - Cassandra port (default: 9042)
 	# 'CASSANDRA_KEYSPACE' - Cassandra keyspaces (default: 'keyspace')