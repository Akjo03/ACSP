FROM prom/prometheus:v2.47.2
ARG GRAFANA_METRICS_TOKEN
ARG PROMETHEUS_PASSWORD
COPY prometheus.yml /etc/prometheus/prometheus.yml
RUN echo "${GRAFANA_METRICS_TOKEN}" > /etc/prometheus/grafana_token.txt
RUN echo "${PROMETHEUS_PASSWORD}" > /etc/prometheus/prometheus_password.txt
EXPOSE 9090
ENTRYPOINT [ "/bin/prometheus" ]
CMD [ "--config.file=/etc/prometheus/prometheus.yml", \
      "--storage.tsdb.path=/prometheus", \
      "--storage.tsdb.retention.time=30d", \
      "--web.console.libraries=/usr/share/prometheus/console_libraries", \
      "--web.console.templates=/usr/share/prometheus/consoles", \
      "--log.level=info" ]