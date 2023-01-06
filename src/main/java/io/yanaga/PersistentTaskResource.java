package io.yanaga;

import io.yanaga.dtos.TaskName;
import io.yanaga.entities.Task;
import io.yanaga.exceptions.NoSuchTaskException;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/persistent-task")
public class PersistentTaskResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTasks() {
        Stream<Task> tasks = Task.streamAll();
        return tasks.map(Task::getName).collect(Collectors.toList());
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Task addTask(TaskName dto) {
        return Task.of(dto.getName());
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Task finishTask(@QueryParam("name") String name) {
        Optional<Task> task = Task.find("name", name).singleResultOptional();
        if (task.isPresent()) {
            task.get().setDone(true);
            return task.get();
        }
        else {
            throw new NoSuchTaskException();
        }
    }

    @DELETE
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTask(@QueryParam("name") String name) {
        Optional<Task> task = Task.find("name", name).singleResultOptional();
        if (task.isPresent()) {
           task.get().delete();
            return "Task was successfully deleted!";
        }
        else {
            throw new NoSuchTaskException();
        }
    }



}
