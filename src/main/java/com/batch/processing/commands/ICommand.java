package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;

public interface ICommand {

    void execute(TBConfig tbConfig);

}
