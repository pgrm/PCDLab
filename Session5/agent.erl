-module(agent).
-export([start_auction/2, start_auction_proccess/2, count_down/2]).

start_auction(MinVal, T) ->
    spawn(agent, start_auction_proccess, [MinVal, T]).

start_auction_proccess(MinVal, T) ->
    Counter = reset_counter(T),
    wait_for_bidders([], MinVal,  T, Counter).

wait_for_bidders(Bidders, MinVal, T, LastCounter) ->
    io:format("DEBUG: Bidders: <<~w>> MinVal: <<~w>> T: <<~w>> LastCounter: <<~w>>~n", [Bidders, MinVal, T, LastCounter]),
    receive
        {bid, Bidder, Bid} when Bid >= MinVal->
            case (lists:keysearch(Bidder, 1, Bidders)) of
                {value, {Bidder, LastBid}} ->
                    if
                        LastBid < Bid ->
                            new_valid_bid(Bidder, Bid, lists:keydelete(Bidder, 1, Bidders), MinVal, T, LastCounter);
                        true ->
                            Bidder ! {error, "Bid is not higher than your last bid.", LastBid}
                    end;
                false ->
                    new_valid_bid(Bidder, Bid, Bidders, MinVal, T, LastCounter)
            end;
        {bid, Bidder, _} ->
            Bidder ! {error, "Bid is less than the MinVal.", MinVal},
            wait_for_bidders(Bidders, MinVal, T, LastCounter);
        {leave, Bidder} ->
            NewBidders = lists:keydelete(Bidder, 1, Bidders),
            announce_bidder_removed(Bidder, NewBidders),
            wait_for_bidders(NewBidders, MinVal, T, LastCounter);
        close_auction ->
            end_auction(Bidders, MinVal, T)
    end.

new_valid_bid(NewBidder, NewBid, Bidders, MinVal, T, LastCounter) ->
    NewCounter = reset_counter(T, LastCounter),

    NewBidders = insert_new_bid(NewBidder, NewBid, Bidders),
    
    announce_new_bid(NewBidder, NewBid, NewBidders),
    wait_for_bidders(NewBidders, MinVal, T, NewCounter).

insert_new_bid(NewBidder, NewBid, []) ->
    [{NewBidder, NewBid}];
insert_new_bid(NewBidder, NewBid, [{HighestBidder, HighestBid} | Bidders]) ->
    if
        NewBid > HighestBid ->
            [{NewBidder, NewBid}, {HighestBidder, HighestBid} | Bidders];
        true ->
            [{HighestBidder, HighestBid} | insert_new_bid(NewBidder, NewBid, Bidders)]
    end.

announce_new_bid(NewBidder, NewBid, [{HighestBidder, HighestBid} | Bidders]) ->
    broadcast_message(
        [{HighestBidder, HighestBid} | Bidders],
        {{new, {bidder, NewBidder}, {bid, NewBid}}, {highest, {bidder, HighestBidder}, {bid, HighestBid}}}).

announce_bidder_removed(_, []) ->
    no_bidders_left_to_notify;
announce_bidder_removed(Bidder, [{HighestBidder, HighestBid} | Bidders]) ->
    broadcast_message(
        [{HighestBidder, HighestBid} | Bidders],
        {{left, {bidder, Bidder}}, {highest, {bidder, HighestBidder}, {bid, HighestBid}}}).

end_auction([], MinVal, T) ->
    if 
        MinVal < 0.1 ->
            io:format("~nAuction Finished.~nNo bidders bid the minimum value of ~w.~nAuction will end now since the minimum possible value has been reached.~n", [MinVal]);
        true ->
            io:format("~nAuction Finished.~nNo bidders bid the minimum value of ~w.~nAuction is restarting ...~n", [MinVal]),
            start_auction_proccess(MinVal/2, T)
    end;
end_auction([{HighestBidder, HighestBid} | Bidders], _, _) ->
    io:format("~nAuction Finished.~nHighest bidder is ~w with a bid of ~w.~n", [HighestBidder, HighestBid]),
    broadcast_message(Bidders, {finished, {winner, HighestBidder}, {bid, HighestBid}}).


broadcast_message(Bidders, Message) ->
    lists:foreach(
        fun ({Bidder, _}) ->
            Bidder ! Message
        end,
        Bidders).

reset_counter(T, LastCounter) ->
    LastCounter ! stop,
    reset_counter(T).

reset_counter(T) ->
    io:format("~nStarting counter to stop auction after ~wms.~n", [T]),
    spawn(agent, count_down, [T, self()]).


count_down(T, Agent) ->
    receive
        stop ->
            was_stopped
        after T ->
            Agent ! close_auction
    end.