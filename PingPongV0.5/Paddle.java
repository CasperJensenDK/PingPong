import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int speed; // speed
    private boolean isPlayer = false;
    private int[] dir;
    private int bounceCount;
    private int gameLevel = 1;
    static final String[] levelList = new String[] {"pingbackground0.png","pingbackground1.png", "pingbackground2.png", "pingbackground3.png", "pingbackground4.png", "pingbackground5.png", "pingbackground6.png"};
    private String[] ballList;
    private String[] paddleList;
    public static final Color[] colorList = new Color[] {Color.BLACK, Color.PINK.darker(), Color.YELLOW, Color.MAGENTA, Color.GREEN.brighter(),Color.GREEN.darker(), Color.WHITE};
    private int delayCounter;
    private int delayStart;
    private static String imagePaddle = "paddle0.png";
    private PingWorld sovs;
    private int currentPlayerScore = 0;
    private int life;

    

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height, boolean isPlayer)
    {
        this.width = width;
        this.height = height;
        this.isPlayer = isPlayer;
        dir = new int[]{0, 0};
        speed = 2;
        createImage();    
        delayCounter = 0;
        delayStart = 50;
        paddleList = new String[] {"paddle0.png","paddle1.png","paddle2.png","paddle3.png","paddle4.png","paddle5.png","paddle6.png"};
        setImage(imagePaddle); 
        ballList = new String[] {"ball0.png","ball1.png", "ball2.png", "ball3.png", "ball4.png", "ball5.png", "ball6.png"};
        life = 3;
        if (isPlayer){
            imagePaddle = "paddle0.png";
        }
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // hvis vi vil kan vil tilføje acceleration
        if (getWorld() != null && isPlayer){
            if (Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left")){
                dir[0] = -speed;
            }
            else if (Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right")){
                dir[0] = speed;
            }
            else{
                dir = new int[]{0, 0};
            }
        }
        if (sovs == null){
            sovs = (PingWorld) getWorld();
        }
        
        setLocation(getX() + dir[0], getY() + dir[1]); 
        if (getX() + width/2 > getWorld().getWidth()){
            setLocation(getWorld().getWidth() - width/2, getY());
        }
        else if (getX() - width/2 < 0){
            setLocation(0 + width/2, getY());
        }
        delayCounter++;
        if (getOneIntersectingObject(Ball.class) != null && delayCounter>= delayStart) {
            changeLevel();
            delayCounter = 0;
        }
        else if(sovs.getPlayerScore()% 10 == 0 && currentPlayerScore != sovs.getPlayerScore())
        {
        changeLevel();
        currentPlayerScore = sovs.getPlayerScore();
            
        }
    }
    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    public void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.BLACK);
        image.fill();
        setImage(imagePaddle);
    }
    
    public void subtractLife()
    {
        life--;
        sovs.updateScoreboard(gameLevel);
        if(life == 0) //die
        {
            sovs.stopped();
            Greenfoot.setWorld(new Death());
        }
    }
    public int getALife()
    {
        return life;
    }
    public float getDx(){
        return speed;
    }
    public int[] getWidthHeight(){
        int[] jens = {width, height};
        return jens;
    }
    
    public int[] getDir(){
        return dir;
    }

    public void changeLevel() { //Skifte background
        if (getOneIntersectingObject(Ball.class) != null) {
         bounceCount++;
        }

        if ((bounceCount == 10 || sovs.getPlayerScore() == currentPlayerScore + 10 ) && bounceCount != 0 && delayCounter>= delayStart && sovs.getPlayerScore() !=0 && currentPlayerScore != sovs.getPlayerScore()) {
            gameLevel++;
            life = 3;
            bounceCount = 0;
            currentPlayerScore = sovs.getPlayerScore();
            String levelChanger = levelList[(gameLevel - 1) % levelList.length];
            String paddleChanger = paddleList[(gameLevel - 1) % paddleList.length];
            imagePaddle = paddleChanger;
            sovs.setBackground(levelChanger);
            String ballLevel = ballList[(gameLevel - 1) % ballList.length];
            sovs.updateScoreboard(gameLevel);
            sovs.getObjects(Ball.class).get(0).init(speed + (int)(gameLevel * 0.5f),ballLevel);
            for (Paddle jens : sovs.getObjects(Paddle.class)){
                jens.setImage(paddleChanger);
            }
            Greenfoot.playSound("levelup.mp3");
        }
    }
}

