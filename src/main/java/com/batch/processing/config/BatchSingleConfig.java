package com.batch.processing.config;

import java.util.Objects;

public class BatchSingleConfig {

    private String name;
    private String scheduling;

    public BatchSingleConfig() {}

    public BatchSingleConfig(String name, String scheduling) {
        this.name = name;
        this.scheduling = scheduling;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduling() {
        return scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchSingleConfig that = (BatchSingleConfig) o;
        return name.equals(that.name) && scheduling.equals(that.scheduling);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scheduling);
    }

    @Override
    public String toString() {
        return "BatchSingleConfig{" +
                "name='" + name + '\'' +
                ", scheduling='" + scheduling + '\'' +
                '}';
    }
}
