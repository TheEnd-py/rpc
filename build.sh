#!/bin/sh

mvn clean
mvn assembly:assembly
mv ./target/rpc-1.0-jar-with-dependencies.jar ./target/lib/
