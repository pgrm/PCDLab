-module(bidder).
-export([init_bidder/1, waiting/2]).

init_bidder(Agent) ->
    spawn(bidder, waiting, [Agent, 0]).

waiting(Agent, LastBid) ->
    receive
        {bid, Bid} when Bid > LastBid ->
            Agent ! {bid, self(), Bid},
            waiting(Agent, Bid);
        {bid, _} ->
            io:format("Cannot bid less or equal as the last bid (~w).~n", [LastBid]),
            waiting(Agent, LastBid);
        leave ->
            Agent ! {leave, self()},
            bidder_finished;
        %% -- messages from agent below
        {error, Msg, Val} ->
            io:format("[Error]: ~s -- Value is ~w~n", [Msg, Val]),
            waiting(Agent, LastBid);
        {finished, {winner, Winner}, {bid, HighestBid}} ->
            io:format("Auction finished with the winner ~w and a bid of ~w.~n", [Winner, HighestBid]);
        Message ->
            io:format("[Agent]: ~w~n", [Message]),
            waiting(Agent, LastBid)
    end.
