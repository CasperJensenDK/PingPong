import greenfoot.*;
import java.util.*;


/**
 * A Ball is a thing that bounces of walls and paddles (or at least i should).
 * 
 * @author The teachers 
 * @version 1
 */
public class Ball extends Actor
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;

    private int speed = 2;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;
    private boolean hasBouncedPaddle;
    private String ballImage = "ball0.png";
    private float[] dir; // enhedsvektor
    private int[] dxdy = new int[]{0, 0};
    private int besureSureCounter = 0;

    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball()
    {
        createImage();
        init(speed, ballImage);
    }

    /**
     * Creates and sets an image of a black ball to this actor.
     */
    private void createImage()
    {
        GreenfootImage ballImage = new GreenfootImage(this.ballImage);
        setImage(ballImage);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            move(speed);
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkPaddles();
            checkRestart();
        }
        //stops ball from being stuck 
        if (dxdy[0] - getX() == 0 && dxdy[1] - getY() == 0){
            besureSureCounter++;
        }
        else{
            dxdy[0] = getX();
            dxdy[1] = getY();
        }
        if (besureSureCounter >= 200){
            init(speed, ballImage);
            besureSureCounter = 0;
        }
        
    }
    
    private float[] crossvector(float[] input, Object pad, boolean isPlayer){
        final float minY = 0.5f;
        float x = input[0];
        float y = (input[1] < minY && input[1] > -minY) ? (input[1] >= 0) ? minY : -minY: input[1];
        
        double tanInputAngle = Math.atan2(y, x); // tan^-1(y/x) - to degrees
        double tanB = 0;
        
        Paddle player = null;
        PaddleCPU ai = null;
        //koordinater, samt længde
        float length = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        
        if (isPlayer && pad != null){
           player = (Paddle) pad;
        }
        else if (!isPlayer && pad != null){
           ai = (PaddleCPU) pad;
        }
        
        boolean posY = y > 0;
        if (pad == null){
           if (!isTouchingCeiling()){
                tanB = (posY) ? Math.PI - tanInputAngle : -Math.PI - tanInputAngle;
                x = length * (float) Math.cos(tanB);
                y = length * (float) Math.sin(tanB);
           }
           else{
                tanB = (posY) ? -Math.abs(tanInputAngle) : Math.abs(tanInputAngle);
                x = length * (float) Math.cos(tanB);
                y = length * (float) Math.sin(tanB);
                PingWorld såvs = (PingWorld)getWorld();
                såvs.increasePlayerScore();
           }
        }
        // mangler opdatering fra player - burde være samme som ceiling
        else if (player != null){
            // hvis spiller
            // betingelse
            tanB = (posY) ? -Math.abs(tanInputAngle) : Math.abs(tanInputAngle);;
            // nye koordinater sumvektor
            Random jesus = new Random();
            x = length * (float) Math.cos(tanB) + ((player.getDir()[0]/2) + jesus.nextFloat());
            y = length * (float) Math.sin(tanB) + player.getDir()[1];
            
            if (y < minY && y > -minY ){
                if ((y >= 0)){
                    y = minY;
                }
                else{
                    y = -minY;
                }
            }
            // new angle
            tanB = Math.atan2(y, x);
            // ny længde siden koordinaterne bliver ændret
            length = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
        else if (ai != null){
            // hvis ai
            // betingelse
            tanB = (posY) ? -Math.abs(tanInputAngle) : Math.abs(tanInputAngle);;
            // nye koordinater sumvektor
            Random jesus = new Random();
            x = length * (float) Math.cos(tanB) + (ai.getDir()[0] + jesus.nextFloat());
            y = length * (float) Math.sin(tanB) + ai.getDir()[1];
            
            if (y < minY && y > -minY){
                if ((y >= 0)){
                    y = minY;
                }
                else{
                    y = -minY;
                }
            }
            // new angle
            tanB = Math.atan2(y, x);
            // ny længde siden koordinaterne bliver ændret
            length = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
        //do the dreging
        setRotation((int) Math.round(Math.toDegrees(tanB)));
        // enhedsvektor
        return new float[]{x / length, y / length}; // måske tjek at y ikke bliver 0 så den kører sidelænds
    }
    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }
    
    private void checkPaddles(){
        Actor pad = getOneIntersectingObject(Paddle.class);
        
        if (pad != null){
            if (!hasBouncedPaddle){
                // dette gøres for at være sikker, jeg ved ikke om man kunne gøre det omvendte, men og lave PaddleCPU til Paddle og stadig gøre det vi vil gøre
                try {
                    PaddleCPU ai = (PaddleCPU) pad;
                    if (dir[1] < 0){
                        Greenfoot.playSound("hitcpu.wav");
                        dir = crossvector(dir, ai, false);
                    }
                }
                catch (java.lang.ClassCastException e)
                {
                    Greenfoot.playSound("hitplayer.wav");
                    Paddle player = (Paddle) pad;
                    dir = crossvector(dir, player, true);
                }
                hasBouncedPaddle = true;
            }
        }
        else{
            hasBouncedPaddle = false;
        }
    }

    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball is bouncing off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingSides())
        {
            if (! hasBouncedHorizontally)
            {
                Greenfoot.playSound("hitwall.wav");
                revertHorizontally();
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }

    /**
     * Check to see if the ball should bounce off the ceiling.
     * If touching the ceiling the ball is bouncing off.
     */
    private void checkBounceOffCeiling()
    {
        if (isTouchingCeiling())
        {
            if (! hasBouncedVertically)
            {
                Greenfoot.playSound("getPoint.wav");
                revertVertically();
            }
        }
        else
        {
            hasBouncedVertically = false;
        }
    }
    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkRestart()
    {
        
        // genstarte/tabe
        if (isTouchingFloor())
        {
            Greenfoot.playSound("loselife.mp3");
            getWorld().getObjects(Paddle.class).get(0).subtractLife();
            init(speed, ballImage);
        }
    
    }

    
    private void revertHorizontally()
    {
        /*
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((180 - getRotation()+ randomness + 360) % 360);
        */
       // new code
       dir = crossvector(dir, null, false);
       hasBouncedHorizontally = true;
    }
    private void revertVertically()
    {
        /*
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((360 - getRotation()+ randomness + 360) % 360);
        */
       // new code
       dir = crossvector(dir, null, false);
       hasBouncedVertically = true;
    }

    /**
     * Initialize the ball settings.
     */
    public void init(int speed, String image)
    {
        this.speed = speed;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        hasBouncedPaddle = false;
        int num = 1;
        int rot = 45;
        ballImage = image;
        createImage();
        // måske gør sådan at den kan falde lige ned
        if (Greenfoot.getRandomNumber(2) == 1){
            num = -1;
            rot += 90;
        }
        if (getWorld() != null)
        {
        setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);    
        }
        
        dir = new float[]{num, 1};
        setRotation(rot);
    }

}
