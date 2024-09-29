FROM ubuntu:latest
LABEL authors="dalv2"

ENTRYPOINT ["top", "-b"]