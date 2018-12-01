package shooter;
import java.util.*;

public class Enemy extends Sprite{
    private final int smode, rand;
    private final String type;
    private int spawn, lives, points, spawnTimer;
    private String msImg;
    public ArrayList<Missile> missile = new ArrayList<>();
    
    Enemy(String type, int x, int y, int speedX, int speedY, String img,
            int lives, int sMode, String msImg, int points, int spawnTimer, int rand){
        super(x, y, speedX, speedY, img);
        this.type = type;
        this.smode = sMode;
        this.spawn = 0;
        this.lives = lives;
        this.msImg = msImg;
        this.points = points;
        this.spawnTimer = spawnTimer;
        this.rand = rand;
    }
    
    public void hit(LevelGenerator level, Player player){
        lives--;
        if (lives==0){
            spawn = 0;
            player.addScore(points);
            level.setPUSpawned(0);
        }
    }
    
    public void shoot(){
        switch(smode){
            case 1:
                if((y+rand)%150==0){
                    missile.add(new Missile(x+width/2-1,y+height,0,3,msImg));
                    missile.get(missile.size()-1).centreMX();
                }
                break;
            case 2:
                int i;
                if((y+rand)%150==0){
                    for (i=0;i<3;i++){
                        missile.add(new Missile(x+width/2-1,y,2*(i-1),5, msImg));
                        missile.get(missile.size()-1).centreMX();
                    }
                }
                break;
        }
    }
    public void moveMissile(int ResY){
        int i;
        for (i=0;i<missile.size();i++){
            missile.get(i).moveX();
            missile.get(i).moveY();
            if(ResY < missile.get(i).getY()){
                missile.remove(i);
                i--;
            }
        }
    }
    

    public String getType(){
        return type;
    }
    public void setSpawn(int spawn){
        this.spawn = spawn;
    }
    public int getSpawn(){
        return spawn;
    }
    public void setLives(int lives){
        this.lives = lives;
    }
    public int getLives(){
        return lives;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public int getPoints(){
        return points;
    }
    public void setspwnTimer(int spawnTimer){
        this.spawnTimer = spawnTimer;
    }
    public int getSpawnTimer(){
        return spawnTimer;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }    
}