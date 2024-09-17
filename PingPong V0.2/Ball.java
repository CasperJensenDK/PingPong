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

    private int speed;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;
    private boolean hasBouncedPaddle;
    private int tempCounter = 0;
    
    private float[] dir; // enhedsvektor
    
    float lookX;
    float lookY;

    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball()
    {
        createImage();
        init();
    }

    /**
     * Creates and sets an image of a black ball to this actor.
     */
    private void createImage()
    {
        GreenfootImage ballImage = new GreenfootImage(BALL_SIZE,BALL_SIZE);
        ballImage.setColor(Color.BLACK);
        ballImage.fillOval(0, 0, BALL_SIZE, BALL_SIZE);
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
            ///move(speed);
            moveBall();
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkPaddles();
            checkRestart();
        }
        lookX = dir[0];
        lookY = dir[1];
    }
    
    private float[] crossvector(float[] input, Object pad, boolean isPlayer){
        /*
        float[] vandret = {1,0};
        double vinkelMVandret = Math.acos((input[0] * vandret[0] + input[1] * vandret[1])/(Math.sqrt(Math.pow(input[0], 2) + Math.pow(input[1], 2)) * Math.sqrt(Math.pow(vandret[0], 2) + Math.pow(vandret[1], 2))));
        double andenvektorVinkel = (180 - (vinkelMVandret + 90)) + 90;
        */
       Paddle player = null;
       PaddleCPU ai = null;
       float x = input[0];
       float y = input[1];
    
       if (isPlayer && pad != null){
           player = (Paddle) pad;
       }
       else if (!isPlayer && pad != null){
           ai = (PaddleCPU) pad;
       }
       
       boolean sammeFortegn = (x > 0 && y > 0) || (x < 0 && y < 0);
       //boolean erBeggePos = (input[0] > 0 && input[1] > 0);
       if (pad == null){
           if (!isTouchingCeiling()){
               y *= (sammeFortegn) ?  -1 : 1;
               x *= (sammeFortegn) ?  1 : -1;
           }
           else{
               // hvis x og y har samme fortegn så skal x skifte eller skal y skifte
               x *= (sammeFortegn) ? -1 : 1;
               y *= (sammeFortegn) ? 1 : -1;
           }
       }
       else if (player != null){
           x *= (sammeFortegn) ? -1 : 1;
           y *= (sammeFortegn) ? 1 : -1;
           
           // omvendt fordi den netop vendes om, fordi sumvektoren skal lægges til den anden vektor
           y += player.getDir()[0];
           x += player.getDir()[1];
       }
       else if (ai != null){
           // hvis den kommer oppefra lad bolden komme igennem (hvis y er pos ig)
           x *= (sammeFortegn) ? -1 : 1;
           y *= (sammeFortegn) ? 1 : -1;
           
           // omvendt fordi den netop vendes om
           y += ai.getDir()[0];
           x += ai.getDir()[1];
       }
       float length = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
       return new float[]{y / length, x/ length};
    }
    private void moveBall(){
        int x = Math.round(dir[0] * speed);
        int y = Math.round(dir[1] * speed);
        if (x == 0){
            if (dir[0] < 0){
                x = -1;
            }
            else{
                x = 1;
            }
        }
        
        if (y == 0){
            if (dir[1] < 0){
                y = -1;
            }
            else{
                y = 1;
            }
        }
        setLocation(getX() + x, getY() + y);
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
                        dir = crossvector(dir, ai, false);
                    }
                }
                catch (java.lang.ClassCastException e)
                {
                    Paddle player = (Paddle) pad;
                    dir = crossvector(dir, player, true);
                }
                hasBouncedPaddle = true;
                tempCounter++;
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
            init();
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
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
    private void init()
    {
        speed = 2;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        hasBouncedPaddle = false;
        int num = 1;
        
        // måske gør sådan at den kan falde lige ned
        if (Greenfoot.getRandomNumber(2) == 1){
            num = -1;
        }
        
        dir = new float[]{num, 1};
        //setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH)+STARTING_ANGLE_WIDTH/2);
    }

}
