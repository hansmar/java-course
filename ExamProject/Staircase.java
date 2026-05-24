public class Staircase extends Entity {

    public Staircase(int x, int y) {
        super(x, y);
    }

    @Override
    protected char getSymbol() { return '>'; }

    @Override
    protected Color getFg() { return Color.CYAN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player walks onto the staircase to descend
    }

    @Override
    public String interactWith(Player player) {
        // We only signal intent. Game owns the actual level transition logic —
        // same indirection as Treasure -> setWon. Staircase doesn't know what
        // level it leads to, doesn't know there's a Game, doesn't even know
        // there's a Dungeon list. It just raises a flag.
        //
        // Note: deliberately no consume() call. Once the player leaves this
        // level, the dungeon's entity list is no longer rendered or ticked,
        // so cleanup is unnecessary. Keeping the staircase intact also leaves
        // the door open for backtracking ("go back up") in a future iteration.
        player.requestNextLevel();
        return "You descend the staircase...";
    }
}
