package com.maxrocky.gundam.coreservice.model.biz;

/**
 * Created by yuer5 on 16/6/28.
 */
public class RobotDownStreamInfo {

    private int type;

    private DownStreamCommand command;

    private DownStreamTasks tasks;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DownStreamCommand getCommand() {
        return command;
    }

    public void setCommand(DownStreamCommand command) {
        this.command = command;
    }

    public DownStreamTasks getTasks() {
        return tasks;
    }

    public void setTasks(DownStreamTasks tasks) {
        this.tasks = tasks;
    }
}
