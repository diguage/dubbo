#!/usr/bin/env bash

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

cd $BASEDIR/..

mvn clean package -pl :docs -Pasciidoc

cd $BASEDIR
