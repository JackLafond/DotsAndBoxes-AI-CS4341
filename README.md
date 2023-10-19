# CS4341_P1
Dots and Boxes Project
Jack Lafond, Sam Colebourn, and Aidan Macnevin

This project was made in correspondence with WPI Professor Carolina Ruiz's Intro to AI class.
For this project we were tasked with creating a dots and boxes AI that implemented the minimax algorithm with alpha beta pruning on a 9x9 board.
We also included a hueristic that limited the branching factor and only explored the msot promising nodes as we explored the game tree.
This allowed us to make more efficient moves while still within the ten second time constraint by ignoring moves an branches that seemed bad.

Some particular challenges came with adjusting the minimax algorithm according to the rules of dots and boxes. In many games players take turns making moves,
however in dots and boxes if a player creates a box they can go again. So the minimax tree did not always flip from on each level, instead we had to keep track to 
see if boxes were completed and as a result their could be strings of min nodes or max nodes. This also meant that when the recursion closed and returned to a parent,
we had to reset the "isMaxing" boolean to what it was before the childs were explored so that the last child did not change the search.

Another challenge came with deciding the hueristic. For our heurisitc we have a list of next moves (children) whose size is limited by a parameter. 
To prioritize the chidren we decided on the following list [3, 1, 0, 2]. This list represents the priority of boxes to be added, where the number is the number
of completed sides of a box. If a current box has 3 completed sides, then the player searching can complete that box and score points so those moves are stored first
in the list of children. Next was boxes with 1 completed side, since a move on this box would leave 2 completed sides, giving the oppoenent a chance to make it 3,
and thus a chance for this player to compelete that box. For 0 and 2 we decided that 2 was the worst case since making amove on a box of 2 give the opposing player
a chance to score that box. 

In the end our AI was able to complete games, without making illegal moves, and within the time limit. Our AI was also able to play in smart ways, 
beating our old versions (Ones that did not include the heuristic) consistenlty, one game being won 73-8.

For future work we would incorperate the idea of chaining into our hueristic. As the game comes to an end there are often long chains of moves, which a minimax algo could 
discover through its search, but using methods to identify values of chains and number of chains could prove to be much mroe efficient.
