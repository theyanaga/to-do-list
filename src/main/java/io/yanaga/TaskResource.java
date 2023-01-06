package io.yanaga;

import io.yanaga.dtos.POJOTask;
import io.yanaga.dtos.TaskName;
import io.yanaga.exceptions.NoSuchTaskException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

@Path("/task")
public class TaskResource {

    private Map<String, POJOTask> tasks = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTasks() {
        return tasks.values().stream().filter(pojoTask -> !pojoTask.isDone()).map(POJOTask::getName).collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public POJOTask addTask(TaskName dto) {
        POJOTask task = new POJOTask(dto.getName(), false);
        tasks.put(task.getName(), task);
        return task;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public POJOTask finishTask(@QueryParam("name") String name) {
        if (tasks.containsKey(name)) {
            POJOTask task = tasks.get(name);
            task.setDone(true);
            return task;
        }
        else {
            throw new NoSuchTaskException();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTask(@QueryParam("name") String name) {
        tasks.remove(name);
        return "Task successfully finished!";
    }



}
