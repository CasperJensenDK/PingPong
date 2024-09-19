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
    
    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        
        backgroundMusic = new GreenfootSound("pingworldmusic.mp3");
        backgroundMusic.playLoop();
        
        if (gameStarted)
        {
            GreenfootImage background = getBackground();
            background.setColor(Color.BLACK);
            // Create a new world with WORLD_WIDTHxWORLD_HEIGHT cells with a cell size of 1x1 pixels.
            addObject(new Ball(), WORLD_WIDTH/2, WORLD_HEIGHT/2);
            addObject(new Paddle(100,20, true), 60, WORLD_HEIGHT - 50);
        }
        else
        {
            Greenfoot.setWorld(new IntroWorld());
        }
        
        spawnPaddle((Greenfoot.getRandomNumber(2) == 0) ? -1 : 1);
        
        playerScore = 0;
        level = 1;
        //ballScore = 0;
        updateScoreboard(level);
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
    
    public void terminatePaddle(PaddleCPU kurt, int direction) {
        removeObject(kurt);
        spawnPaddle(direction);
    }

    //Scoreboard stuff
    public void increasePlayerScore() {
        playerScore++;
        updateScoreboard(level);
    }
    
    /*public void increaseBallScore() {
        ballScore++;
        updateScoreboard();
    }*/
    
    public void updateScoreboard(int gameLevel) {
    setBackground(Paddle.levelList[(gameLevel - 1) % Paddle.levelList.length]);
    GreenfootImage background = getBackground();
    background.setColor(Paddle.colorList[(gameLevel - 1) % Paddle.levelList.length]);
    greenfoot.Font fontScore = new greenfoot.Font("OCR A Extended", true, false, 20);
    background.setFont(fontScore);
    level = gameLevel;
    background.drawString("Player: " + playerScore, 10, 30);
    background.drawString("life: " + getObjects(Paddle.class).get(0).getALife(), 10, 50);
    //background.drawString("CPU: " + ballScore, 10, 60);
    background.drawString("Level: " + gameLevel,350 ,30);
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
