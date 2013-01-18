monitor.erl contains the code for the monitor.
init_monitor initializes the module, creates 2 lists (rows and columns of the matrix) and iterates through them (like 2 for-loops in a procedural programming language) and spawns a process for each cell in the matrix.
Afterwards in global_monitoring it listens to messages from the console (statistics) and processes.
In case a process fails, a new is started and number of failures raises.
If a process is finished and there are still tasks remaining, it is restarted and number for remaining tasks and finished raises
If no more tasks are remaining the process is stopped and number of finished raises while number of running gets less.
When receiving statistics, a simple statistic is printed of processes failed and finished so far.
If 0 processes are remaining and 0 are running, the same statistic is printed and the program stops executing.

device.erl contains the code for the devices (slaves).
init_device initializes the module and the random:seed. Afterwards the process is waiting for messages.
If it gets the message start or restart, it starts working on the initialized cell (I,J). In case I equals a random value from 1 to 1000 the process "fails" and notifies the Monitor.
Otherwise the process notifies the monitor that it finished and is waiting for a new message.
If the monitor has no tasks left, it can stop the device with a stop message.