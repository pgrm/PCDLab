-module(monitor).
-export([init_monitor/3]).

init_monitor(N, M, NumberOfTasks) ->
    List1 = lists:seq(1, N),
    List2 = lists:seq(1, M),
    lists:foreach(
        fun (I) ->
            lists:foreach(
                fun (J) ->
                    DevicePid = spawn(device, init_device, [I, J, self()]),
                    DevicePid ! start
                end,
                List2)
        end,
        List1),
    global_monitoring(NumberOfTasks - (N*M), 0, 0, N*M).

global_monitoring(0, NumberOfFailures, NumberOfFinished, 0) ->
    io:format("~nExecution Finished.~nStatistics: ~w Failures, ~w Finished~n", [NumberOfFailures, NumberOfFinished]);

global_monitoring(NumberOfRemaining, NumberOfFailures, NumberOfFinished, NumberOfRunning) ->
    receive
        {fail, I, J} ->
            DevicePid = spawn(device, init_device, [I, J, self()]),
            DevicePid ! start,
            global_monitoring(NumberOfRemaining, NumberOfFailures + 1, NumberOfFinished, NumberOfRunning);
        {finish, _, _, Pid} when NumberOfRemaining == 0 ->
            Pid ! stop,
            global_monitoring(NumberOfRemaining, NumberOfFailures, NumberOfFinished + 1, NumberOfRunning - 1);
        {finish, _, _, Pid} ->
            Pid ! restart,
            global_monitoring(NumberOfRemaining - 1, NumberOfFailures, NumberOfFinished + 1, NumberOfRunning);
        statistics ->
            io:format("Statistics: ~w Failures, ~w Finished~n", [NumberOfFailures, NumberOfFinished]),
            global_monitoring(NumberOfRemaining, NumberOfFailures, NumberOfFinished, NumberOfRunning)
    end.
