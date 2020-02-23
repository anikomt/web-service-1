#!/bin/sh -e

$GLASSFISH_HOME/bin/asadmin start-domain
cp /build/*.war $DOMAIN_HOME/autodeploy/
tail -f $DOMAIN_HOME/logs/server.log
