package io.yanaga.exceptions;

public class NoSuchTaskException extends RuntimeException {

    public NoSuchTaskException() {
        super("Task can't be updated because it does not exist!");
    }

}
