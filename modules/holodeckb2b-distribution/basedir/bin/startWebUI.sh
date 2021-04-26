#!/bin/sh

# ----------------------------------------------------------------------------
# Holodeck B2B web UI start script
#
# Environment Variable Prequisites
#
#   H2B_HOME   Home of the Holodeck B2B installation. If not set I will try
#              to figure it out.
#
#   JAVA_HOME  Must point at your Java Runtime Environment
#
# -----------------------------------------------------------------------------

# Get the context and from that find the location of setenv.sh
. `dirname $0`/setenv.sh

cd "$HB2B_HOME"

$JAVA_HOME/bin/java" -jar ./webui/holodeckb2b-webui.jar --server.port=8088
