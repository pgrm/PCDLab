The after-session source code can be found in src/session1/after/{one, two, tree} for the first second and thrid task.

Since theses tasks heavily depent upon the code we produced during the session, I also added thes to the zip (src/session1/during/{one, two/{one, two, three}}) for all the tasks.

Explenation of src/session1/during/one:

Main.java - the Class which runs and configures the whole simulation.

Stopable.java - since Thread.stop() is deprecated and threads should rather halt on their own, I created an interface Stopable, which afterwards will be implemented by Consumer and Producer for setting an internal flag and halt.

VisualizedStack.java - because we were supposed to display the changes in our stack, the VisualizedStack enables this without much effort. It's and abstract class which monitors the changes of the stack (push, pop) and calls on every change an abstract method "updateVisualization" which can be implemented in any way.

ConsoleVisualizedStack.java - is an implementation of VisualizedStack to display the stack on the console, simply by printing [XXXXX] ("X" - being one item on the stack) on System.out

Consumer.java - is Runnable and Stopable. It needs a parameter for the stack to use and a double, of how many seconds it can sleep maximum.
Once the Thread is started, the Consumer checks if the stack is empty, if so than it waits until it will be filled. If it's not empty it takes out an element (pop) and notifies everybody, since it could be that the producer was sleeping because the stack was full.
Afterwards the consumer goes to sleep for a random amount of time up to the maximum defined in the constructor.

Producer.java - is Runnable and Stopable. The principe is the same as with Consumer.java except that it gets an additonal parameter, of how many items it should produce max. Once this number is reached it waits until it gets notified by the consumer that items have been consumed and the stack is ready for production. On the other hand it notifies also after producing items, so sleeping consumers can wake up.

Changes made for src/session1/after/one
Both prducers and consumers have an additional parameter, how many items they can produce / consume at once maximum. The actual number is again random (same as for sleeping). So once the Thread wake up, it tries to produce / consume all the items. In case it's not possible because the Stack is full / empty, it waits.
In this implementation notifications are sent either when the stack became empty / full or otherwise after all the items have been produced / consumed at once.

Changes made for src/session1/after/two
The ConsoleVisualizedStack gets a new parameter, to display for each item. So instead of many XXXX to visualize the Stack we can have also YYYY and ZZZZ and so on, to visualize different stacks. The rest of the implementation of Consumer / Producer stays the same as during the session.
The major difference is in the configuration, that different consumer and producer get a different stack.

Changeds made for src/session1/after/three
Here a new Class is introduced "Hybrid". This is a consumer which can also start to produce.
The Class has the same paramters as the producer with the additional, of how many items it should produce maximum, once the stack is empty. (The actual number is again determined randomly to make it more interesting and dynamic).
The Hybrid consumes items, until the stack is empty and than it basically switchiches to the producer (with a very similar functionality). The only difference to the actual Producer is that the Hybrid only produces up to a specific number of items and than tries to consume again. Of course it sleeps in between of each item produced / consumed.

Short explenation to src/session1/during/two/*
(short, because I believe that my solution might be interesting but isn't necessary to upload)
Here the actual functionality lays in the different implementations of the ThreadSynchronizer class. Everything else stays the same, so that the Main classes in ./one, ./two and ./tree just call the GlobalMain with different configurations and differen ThreadSynchronizers which are defined in the specific packages.
