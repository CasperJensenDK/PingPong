import greenfoot.*;

/**
 * The Ping World is where Balls and Paddles meet to play pong.
 * 
 * @author The teachers 
 * @version 1
 */
public class PingWorld extends World
{
    private GreenfootSound backgroundMusic;
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    
    private int playerScore;
    private int level;
    //private int ballScore;
    private GreenfootImage playerScoreImage;
    //private GreenfootImage ballScoreImage;
    
    // multiplayer
    private boolean multiplayer = false;
    
    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted, boolean multiP)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        multiplayer = multiP;
        backgroundMusic = new GreenfootSound("pingworldmusic.mp3");
        backgroundMusic.playLoop();
        level = 1;
        if (!multiP){
            if (gameStarted)
            {
                GreenfootImage background = getBackground();
                background.setColor(Color.BLACK);
                // Create a new world with WORLD_WIDTHxWORLD_HEIGHT cells with a cell size of 1x1 pixels.
                addObject(new Paddle(100,20, true), 60, WORLD_HEIGHT - 50);
            }
            else
            {
                Greenfoot.setWorld(new IntroWorld());
            }
            
            spawnPaddle((Greenfoot.getRandomNumber(2) == 0) ? -1 : 1);
            
            playerScore = 0;
            //ballScore = 0;
            updateScoreboard(level);
        }
        else{
            // add players
            Paddle p1 = new Paddle(100,20, true);
            p1.constructPlayersForMultiplayer(1, true);
            Paddle p2 = new Paddle(100,20, true);
            p2.constructPlayersForMultiplayer(2, true);
            addObject(p1, 60, WORLD_HEIGHT - 50);
            addObject(p2, 60, 50);
            updateScoreboard(level);
        }
        
        addObject(new Ball(), WORLD_WIDTH/2, WORLD_HEIGHT/2);
    }
    // spawner en paddle et sted inden for skærmen
    private void spawnPaddle(int direction){
        // kan randomize længeden
        // randomize dir
        int paddleAfstandFraTop = 20;
        int x = (direction > 0) ? 1 : 499 - 5;
        //                                                                          der hvor spiller spawner, buffer + højden fra centrum, tilføjer et minimum
        addObject(new PaddleCPU(100, 20, false, direction), x, Greenfoot.getRandomNumber(((WORLD_HEIGHT - 50) - 50 + 20/2) - paddleAfstandFraTop) + paddleAfstandFraTop);
    }
    public int getPlayerScore(){
        return playerScore;
    }
    public boolean getIsMultiplayer(){
        return multiplayer;
    }
    public void terminatePaddle(PaddleCPU kurt, int direction) {
        removeObject(kurt);
        spawnPaddle(direction);
    }

    //Scoreboard stuff
    public void increasePlayerScore() {
        playerScore++;
        updateScoreboard(level);
    }
    
    public void updateScoreboard(int gameLevel) {
        setBackground(Paddle.levelList[(gameLevel - 1) % Paddle.levelList.length]);
        GreenfootImage background = getBackground();
        background.setColor(Paddle.colorList[(gameLevel - 1) % Paddle.levelList.length]);
        greenfoot.Font fontScore = new greenfoot.Font("OCR A Extended", true, false, 20);
        background.setFont(fontScore);
        level = gameLevel;
        if (!multiplayer){
            background.drawString("Player: " + playerScore, 10, 30);
            background.drawString("life: " + getObjects(Paddle.class).get(0).getALife(), 10, 50);
            //background.drawString("CPU: " + ballScore, 10, 60);
            background.drawString("Level: " + gameLevel,350 ,30);
        }
        else{
            background.drawString("life: " + getObjects(Paddle.class).get(0).getALife(), 10, WORLD_HEIGHT - 15);
            background.drawString("life: " + getObjects(Paddle.class).get(1).getALife(), 10, 30);
            background.drawString("Level: " + gameLevel,350 ,30);
        }
    }
    
    //Making sure music loops and stops when needed
    @Override
    public void stopped() {
        backgroundMusic.pause();
    }
    @Override
    public void started() {
        backgroundMusic.playLoop();
    }
}
