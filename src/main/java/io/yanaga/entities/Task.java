package io.yanaga.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.transaction.Transactional;

@Entity
public class Task extends PanacheEntity {

    public Task() {

    }

    @Transactional
    public static Task of(String name) {
        Task task = new Task(name);
        task.persistAndFlush();
        return task;
    }
    private Task(String name) {
        this.name = name;
    }

    private String name;

    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
