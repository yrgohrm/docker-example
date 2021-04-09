
FROM mcr.microsoft.com/mssql/server:2019-latest

USER root

RUN mkdir -p /opt/setup
WORKDIR /opt/setup

COPY . /opt/setup

RUN chmod +x /opt/setup/import-data.sh
RUN chmod +x /opt/setup/entrypoint.sh

EXPOSE 1433

USER mssql
ENTRYPOINT /bin/bash ./entrypoint.sh
