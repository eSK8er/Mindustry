package mindustry.game;

import arc.struct.*;
import mindustry.*;
import mindustry.world.*;

/** Class for various team-based utilities. */
public class Teams{
    private TeamData[] map = new TeamData[256];

    /**
     * Register a team.
     * @param team The team type enum.
     */
    public void add(Team team){
        map[team.id] = new TeamData(team);
    }

    /** Returns team data by type. */
    public TeamData get(Team team){
        if(map[team.id] == null){
            add(team);
        }
        return map[team.id];
    }

    /** Returns whether a team is active, e.g. whether it has any cores remaining. */
    public boolean isActive(Team team){
        //the enemy wave team is always active
        return team == Vars.waveTeam || get(team).cores.size > 0;
    }

    /** Returns whether {@param other} is an enemy of {@param #team}. */
    public boolean areEnemies(Team team, Team other){
        //todo what about derelict?
        return team != other;
    }

    /** Allocates a new array with the active teams.
     * Never call in the main game loop.*/
    public Array<TeamData> getActive(){
        return Array.select(map, t -> t != null);
    }

    public static class TeamData{
        public final ObjectSet<Tile> cores = new ObjectSet<>();
        public final Team team;
        public Queue<BrokenBlock> brokenBlocks = new Queue<>();

        public TeamData(Team team){
            this.team = team;
        }
    }

    /** Represents a block made by this team that was destroyed somewhere on the map.
     * This does not include deconstructed blocks.*/
    public static class BrokenBlock{
        public final short x, y, rotation, block;
        public final int config;

        public BrokenBlock(short x, short y, short rotation, short block, int config){
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.block = block;
            this.config = config;
        }
    }
}
