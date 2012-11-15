in 50PrisonersProblem.lts you can find the solution to the initial 50 prisoners problem, and also to the one where it's not clear if the light was turned on or off initially.
Since it's not possible to model an unknown state, you can check the correctness by changing the state of the light and checking if the result is the same (it is, and it is the same as with the simpler version and know state of light).

in src/session3 you can find the implementation
Room is a singleton, checking if the prisoner is in the room is done by synchronized(room). With this method only one prisoner at a time can be in the room.
CountingPrisoner is equalent to Alice - one prisoner which is counting the lights turned on.
In session3.Main.main() the prisoners are initialized and started (one of them is a CountingPrisoner). From there on they go one by one into the room until they are free. When the CountingPrisoner has finished Counting he tells everybody that they are free.