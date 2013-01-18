-module(device).
-export([init_device/3]).

init_device(I, J, MonitorPid) ->
    {A1, A2, A3} = now(),
    random:seed(A1, A2, A3),
    waiting(I, J, MonitorPid).

waiting(I, J, MonitorPid) ->
    receive
        start ->
            work(I, J, MonitorPid);
        restart ->
            work(I, J, MonitorPid);
        stop ->
            ok
    end.

work(I, J, MonitorPid) ->
    Chance = random:uniform(1000),
    if
        Chance == I ->
            MonitorPid ! {fail, I, J},
            failed;
        true ->
            MonitorPid ! {finish, I, J, self()},
            waiting(I,J,MonitorPid)
    end.
