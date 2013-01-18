agent.erl launches a process where the auction is hold. The arguments of the process are:
    - the minimal value for to bid
    - the time after which the auction should be closed. this time is counted from the last valid bid
all bids including the bidders are stored in an sorted list. It is assumed that the bidder wouldn't bit a lower value than before, but never checked. If the value is less than the minimal value the bid is ignored. After the time runs out the bidder is announced or the auction restarted with half the minimal value. If no winner was found and the minimal value is less than 0.1 the auction stops as well (to prevent it from an endless loop).

bidder.erl
a simple process which can get commands from the user (bid, leave) and prints messages from the agent. the bidder doesn't allow the user to bid less than before (initial value is 0).