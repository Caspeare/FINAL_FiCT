import java.sql.SQLOutput;

public class Player {

    public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
    public static final int PlayerTypeSize = Player.PlayerType.values().length;

    private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
    private double maxHP;		//Max HP of this player
    private double currentHP;	//Current HP of this player
    private double atk;			//Attack power of this player

    /** Self-defined Attributes  */
//	private Arena.Team team;
//	private Arena.Row row;
    private	int SP_level;
    private int SP_count;
    /** Status Attributes  */
    private boolean cursed;
    private boolean sleep;
    private boolean taunt;
    /** Location Attributes */
    private Arena.Team team;
    private Arena.Row row;
    private int position;


    /**
     * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns,
     * as specified in the given table. It also reset the internal turn count of this player.
     * @param _type
     */
    public Player(PlayerType _type)
    {
        //INSERT YOUR CODE HERE
        if (_type == Player.PlayerType.Healer) {
            this.type = _type;
            this.maxHP = 4790;
            this.currentHP = 4790;
            this.atk = 238;
            this.SP_level = 4;
            this.SP_count = 4;
        }
        else if (_type == Player.PlayerType.Tank) {
            this.type = _type;
            this.maxHP = 5340;
            this.currentHP = 5340;
            this.atk = 255;
            this.SP_level = 4;
            this.SP_count = 4;
        }
        else if (_type == Player.PlayerType.Samurai) {
            this.type = _type;
            this.maxHP = 4005;
            this.currentHP = 4005;
            this.atk = 368;
            this.SP_level = 3;
            this.SP_count = 3;
        }
        else if (_type == Player.PlayerType.BlackMage) {
            this.type = _type;
            this.maxHP = 4175;
            this.currentHP = 4175;
            this.atk = 303;
            this.SP_level = 4;
            this.SP_count = 4;
        }
        else if (_type == Player.PlayerType.Phoenix) {
            this.type = _type;
            this.maxHP = 4175;
            this.currentHP = 4175;
            this.atk = 209;
            this.SP_level = 8;
            this.SP_count = 8;
        }
        else if (_type == Player.PlayerType.Cherry) {
            this.type = _type;
            this.maxHP = 3560;
            this.currentHP = 3560;
            this.atk = 198;
            this.SP_level = 4;
            this.SP_count = 4;
        }

    }

    /**
     * Returns the current HP of this player
     * @return
     */
    public double getCurrentHP()
    {
        //INSERT YOUR CODE HERE
        return this.currentHP;
    }

    /**
     * Returns type of this player
     * @return
     */
    public Player.PlayerType getType()
    {
        //INSERT YOUR CODE HERE
        return this.type;
    }

    /**
     * Returns max HP of this player.
     * @return
     */
    public double getMaxHP()
    {
        //INSERT YOUR CODE HERE
        return this.maxHP;
    }

    /**
     * Returns whether this player is sleeping.
     * @return
     */
    public boolean isSleeping()
    {
        //INSERT YOUR CODE HERE
        return this.sleep;
    }

    /**
     * Returns whether this player is being cursed.
     * @return
     */
    public boolean isCursed()
    {
        //INSERT YOUR CODE HERE
        return this.cursed;
    }

    /**
     * Returns whether this player is alive (i.e. current HP > 0).
     * @return
     */
    public boolean isAlive()
    {
        //INSERT YOUR CODE HERE
        if (this.currentHP > 0) return true;
        else  return false;
    }

    /**
     * Returns whether this player is taunting the other team.
     * @return
     */
    public boolean isTaunting()
    {
        //INSERT YOUR CODE HERE
        return this.taunt;
    }


    public void attack(Player target)
    {
        //INSERT YOUR CODE HERE
        target.dealdamage(this.atk);
        if (target.getCurrentHP() <= 0) {
            target.setZeroHP();
            target.setSleep(false);
            target.setCursed(false);
            target.setTaunt(false);
            target.resetSPcooldown();
        }

    }

    public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
    {
        //INSERT YOUR CODE HERE
         if (this.type == PlayerType.Cherry) {
             if (!teamAlive(theirTeam)) return;

             //Print out this Player location
             System.out.print(this.SendMeYourLocation(this));

                    for (int i = 0; i < theirTeam.length; i++) {
                        for (int j = 0; j < theirTeam[i].length; j++) {

                            if (theirTeam[i][j].isAlive() == true) {
                                theirTeam[i][j].setSleep(true);
                                System.out.print(" Feeds a Fortune Cookie to ");
                                System.out.println(SendMeYourLocation(theirTeam[i][j]));
                            }


                        }
                    }
                }
        if (this.type == PlayerType.Healer) {

            //Print out this Player location
            System.out.print(this.SendMeYourLocation(this));

            Player target = getHealingTarget(myTeam);
            double heal = target.getMaxHP()*0.25;
            if (target.getCurrentHP()+heal > target.getMaxHP()) target.setCurrentHP(target.getMaxHP());
            else target.setCurrentHP(target.getCurrentHP()+heal);
            System.out.print(" Heals ");
            System.out.println(SendMeYourLocation(target));
            return;
        }
        else if (this.type == PlayerType.Tank) {
            System.out.println(" is Taunting ");
            this.setTaunt(true);
            return;
        }
        else if (this.type == PlayerType.Samurai) {
            //check if other team still alive
            if (!teamAlive(theirTeam)) return;

            //Print out this Player location
            System.out.print(this.SendMeYourLocation(this));

            Player target = getTarget(theirTeam);
            attack(target);
            attack(target);
            System.out.print(" Double-Slashes ");
            System.out.println(SendMeYourLocation(target));
            return;

        }
        else if (this.type == PlayerType.BlackMage) {
            //check if other team still alive
            if (!teamAlive(theirTeam)) return;

            //Print out this Player location
            System.out.print(this.SendMeYourLocation(this));

            Player target = getTarget(theirTeam);
            target.setCursed(true);
            System.out.print(" Curses ");
            System.out.println(SendMeYourLocation(target));
            return;
        }

        else if (this.type == PlayerType.Phoenix) {
            if (anyDeath(myTeam)) {
                Player target = getTargetRevive(myTeam);
                double reviveHP = target.getMaxHP() * 0.3;
                target.setCurrentHP(reviveHP);
                System.out.print(" Revives ");
                System.out.println(SendMeYourLocation(target));
                return;
            }


        }


    }


    /**
     * This method is called by Arena when it is this player's turn to take an action.
     * By default, the player simply just "attack(target)". However, once this player has
     * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
     * where each player type performs his own special move.
     * @param arena
     */
    public void takeAction(Arena arena)
    {
        //INSERT YOUR CODE HERE
        /**Pre-Battle State Phase*/
        //Determine Ally team and Enemy team
        Player[][] myTeam = null;
        Player[][] theirTeam = null;
        if (arena.isMemberOf(this, Arena.Team.A)) {
            myTeam = arena.getTeam(Arena.Team.A);
            theirTeam = arena.getTeam(Arena.Team.B);
        }
        else {
            myTeam = arena.getTeam(Arena.Team.B);
            theirTeam = arena.getTeam(Arena.Team.A);
        }


//        if (this.type == PlayerType.Tank)
//        {
//            System.out.println("Tank taunt = " + this.taunt);
//            System.out.println("Tank SP = " + this.SP_count   );
//        }

        /**Pre-Battle Removing Negative Effect Phase*/
        //Remove Taunt status
        if (this.isTaunting()) this.setTaunt(false);
        //Remove theirTeam NegativeEffect from last round
        if (this.type == PlayerType.BlackMage || this.type == PlayerType.Cherry) {
            if (this.SP_count == this.SP_level) {
                removeNegativeEffect(theirTeam, this.getType());
            }
        }

        /**Pre-State Phase*/
        //Check if Player is still Alive
        if (this.isAlive() == false) return;
        //If Player sleep, don't do anything
        if (this.isSleeping() == true) return;



        /**Battling Phase*/
//        if (arena.getNumRounds()%this.SP_level==0) {
        if (this.SP_count == 1) {
            //Use SpecialAbility


            useSpecialAbility(myTeam, theirTeam);
            resetSPcooldown();

        }
        else {
            //Use Normal Attack
            if (anyTaunt(theirTeam)) {
               //attack 1st taunt

                //check if other team still alive
                if (!teamAlive(theirTeam)) return;
                Player target = getTargetTaunt(theirTeam);
                attack(target);

                //Print out this Player location
                System.out.print(this.SendMeYourLocation(this));

                System.out.print(" Attacks ");
                System.out.println(SendMeYourLocation(target));
                this.SP_count--;

            }
            else {
                //attack 1st least HP

                //check if other team still alive
                if (!teamAlive(theirTeam)) return;
                Player target = getTarget(theirTeam);
                attack(target);

                //Print out this Player location
                System.out.print(this.SendMeYourLocation(this));

                System.out.print(" Attacks ");
                System.out.println(SendMeYourLocation(target));
                this.SP_count--;

            }

        }



    }

    /**
     * This method overrides the default Object's toString() and is already implemented for you.
     */
    @Override
    public String toString()
    {
        return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
                +((this.isCursed())?"C":"")
                +((this.isTaunting())?"T":"")
                +((this.isSleeping())?"S":"")
                +"]";
    }

    /**
     *
     *
     * Self-defined Methods
     *
     *
     */



    /** Getter % Setter Methods */

    public void setLocation(Arena.Team team, Arena.Row row, int position)
    {
        this.team = team;
        this.row = row;
        this.position = position;
    }

    public void setCursed(boolean _CURSE) { this.cursed = _CURSE;}

    public void setSleep(boolean _SLEEP) { this.sleep = _SLEEP;}

    public void setTaunt(boolean _TAUNT) { this.taunt = _TAUNT;}

    public void setCurrentHP(double HP) { this.currentHP = HP;}

    public void dealdamage(double damage) { this.currentHP -= damage; }

    public void setZeroHP(){ this.currentHP = 0;}

    public void resetSPcooldown() { this.SP_count = this.SP_level;}

    /**
     * this method check if other team still alive
     */
    public boolean teamAlive( Player[][] team)
    {
        //Check alive player in both team
        if (AnyStillAlive(team, Arena.Row.Front) || AnyStillAlive(team, Arena.Row.Back)){
            return true;
        }
        else return false;
    }

    /**
     * This method get the percentage value of currentHP of the Player
     */
    public double getPercentageHealth() { return (this.getCurrentHP()/this.getMaxHP()) * 100;}

    /**
     * This method return the Player which has the lowest Percentage HP
     */
    public Player getHealingTarget(Player[][] _ourteam)
    {
        Player target = _ourteam[1][_ourteam.length-1];
        for (int i = 1; i >= 0; i--) {
            for (int position = _ourteam[i].length-1; position >= 0; position--) {
                if (_ourteam[i][position].isAlive()) {
                    if (_ourteam[i][position].getPercentageHealth() < target.getPercentageHealth()) {
                        target = _ourteam[i][position];
                    }
                }
            }
        }

        return target;
    }

    /**
     * This method remove negative effect of the opposite team after a round pass
     */
    public void removeNegativeEffect(Player[][] oppositeTeam, PlayerType _type)
    {
        if (_type == PlayerType.BlackMage) {
            for ( int i = 0; i < oppositeTeam.length; i++) {
                for (Player each : oppositeTeam[i]) {
                    each.setCursed(false);
                }
            }
        }
        else if (_type == PlayerType.Cherry) {
            for ( int i = 0; i < oppositeTeam.length; i++) {
                for (Player each : oppositeTeam[i]) {
                    each.setSleep(false);
                }
            }
        }

    }

    /**
     * this method check if there is any taunt in the enemy team
     * @param team
     * @return
     */
    public boolean anyTaunt(Player[][] team)
    {
        for (int i = 0; i < team.length; i++) {
            for (Player each : team[i]) {
                if (each.isTaunting()) return true;
            }
        }

        return  false;
    }

    /**
     * this method return target as the Plaayer with Taunt status
     * @param team
     * @return
     */
    public Player getTargetTaunt(Player[][] team)
    {
        Player target = null;
        boolean check = false;
        for (int i = 0; i < team.length; i++) {
            for (int j = 0; j < team[i].length; j++) {
                if (team[i][j].isAlive()) {
                    if (team[i][j].isTaunting()) {
                        target = team[i][j];
                        check = true;
                        break;
                    }
                }

            }
            if (check) break;
        }

        return target;
    }

    /**
     * this method set default target for selecting target
     * @param team
     * @param row
     * @return
     */
    public Player setDefaultTarget(Player[][] team, int row)
    {
        for (int i = team[row].length-1; i >= 0; i--) {
            if (team[row][i].isAlive()) return team[row][i];
        }

        return null;
    }

    /**
     * this method find the Player with the least HP, and return as a target for being attacked
     * @param team
     * @return
     */
    public Player getTarget(Player[][] team)
    {
        int row;
        if (AnyStillAlive(team, Arena.Row.Front)) row = 0;
        else row = 1;

        Player target = setDefaultTarget(team, row);
        for (int i = team[row].length-1; i >= 0; i--) {
            if (team[row][i].isAlive()) {
                if (team[row][i].getCurrentHP() <= target.getCurrentHP()) target = team[row][i];
            }
        }

        return target;
    }

    /**
     * this method return target player to be revived
     * @param team
     * @return
     */
    public Player getTargetRevive(Player[][] team)
    {
        Player target = null;
        boolean check = false;

        for (int i = 0; i < team.length; i++) {
            for (Player each : team[i]) {
                if (each.isAlive() == false) {
                    target = each;
                    check = true;
                    break;
                }
            }
            if (check == true) break;
        }
        return target;
    }

    /**
     * this method check if there's death allies in the team
     */
    public boolean anyDeath(Player[][] team)
    {
        for (int i = 0; i < team.length; i++) {
            for (Player each : team[i]) {
                if (each.isAlive() == false) return true;
            }
        }
        return false;
    }

    /**
     * This method check whether any of Players in this row still alive or not
     * @param row
     * @param team
     * @return
     */
    public boolean AnyStillAlive(Player[][] team, Arena.Row row)
    {
        int line;
        if (row == Arena.Row.Front) line = 0;
        else line = 1;

        for (Player each : team[line]) {
            if (each.isAlive() == true) return true;
        }

        return false;
    }

    /**
     * this method return String of Player location for print
     */
    public String SendMeYourLocation(Player player)
    {
        return  player.team + "[" + player.row + "][" + player.position + "] {" + player.getType() + "}" ;
    }


}
