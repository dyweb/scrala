#!/bin/bash

username=$1

sbt "run-main Main $1"