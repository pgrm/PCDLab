Here can be only the programm code found.

The tasks I did after the session are in src/session2.after*

There are 3 important classes already in the package session 2:
 - Stopable, an interface which allows me to call stop on a runnable class
 - SleepyClass, which implements runable and stopable and runs forever until stoped, with a sleep in between
 - Tuple, simple class to be able to pass around 2 values together. It is used to pass the office worker together with the current number

in session2.after there is another more often used class: NamedSleepyClass - the same as SleepyClass, just that it can have a name and a report function (which writes the name + reported message to stdout). Client and OfficeWorker extend NamedSleepyClass

In all other packages (session2.after.*) there are 5 classes which work more or less the same always:
 1. Client - the client which goes into the town office and takes a ticket
 2. OfficeWorker - the worker which handles clients requests
 3. TicketMachine - just creates tickets (can also create tickets for different services), returns them to the client and notifies the TicketPanel about them
 4. TicketPanel - has a queue of tickets per service. The office worker can say that it wayts for another client, the ticketpanel will notify the clients that the current ticketnumber (or ticketnumbers if there are more workers) has changed and the client can check if it's his turn.
    If the client decides to access the OfficeWorker even it's not his turn, the office will throw an exception.
 5. Main class - which initializes and start all threads (Client and OfficeWorker each extend NamedSleepyClass and are therefore threads which run individually and synchronize each other based on the TicketPanel and TicketMachine classes)