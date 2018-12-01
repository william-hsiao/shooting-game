package shooter;
import java.util.*;

public class Player extends Sprite{
    private int spawn, lives=3, ResX, ResY, hitReg, hitTimer, shootCounter=0, shootDelay=1, score=0;
    private String baseSMode, tempSMode="";
    private final String[] imgSet, msImgSet;
    public ArrayList<Missile> missile = new ArrayList<>();
    
    Player (int x, int y, int speedX, int speedY, String img, String[] imgSet, String[] msImgSet){
        super(x, y, speedX, speedY, img);
        this.spawn = 1;
        this.baseSMode = "single";
        this.imgSet = imgSet;
        this.msImgSet = msImgSet;
    }
    
    public void moveMissile(){
        int i;
        for (i=0;i<missile.size();i++){
            missile.get(i).moveX();
            missile.get(i).moveY();
            if(0 > missile.get(i).getY()){
                missile.remove(i);
                i--;
            }
        }
    }
    public void shoot(){
        if (shootCounter == 0){
            int i;
            if (!"".equals(tempSMode)){
                switch(tempSMode){
                    case "fire5":
                        for (i=0;i<5;i++){
                            missile.add(new Missile(x+width/2-1,y,2*(i-2),-5, msImgSet[0]));
                        }
                        break;
                    default:
                        break;
                }
            }
            else{
                switch(baseSMode){
                    case "single":
                        missile.add(new Missile(x+width/2-1,y,0,-8, msImgSet[0]));
                        break;
                    case "singleL":
                        missile.add(new Missile(x+width/2-25,y,0,-8, msImgSet[1]));
                        break;
                    case "double":
                        for (i=0;i<2;i++){
                            missile.add(new Missile(x+width/4+i*width/2-1,y,0,-8, msImgSet[0]));
                        }
                        break;
                    case "triple":
                        for (i=0;i<3;i++){
                            missile.add(new Missile(x+width/2+(i-1)*width/3-1,y,0,-8, msImgSet[0]));
                        }
                        break;
                    default:
                        break;
                }
            }
            shootCounter++;
        }
    }
    public void incSCounter(){
        if (shootCounter != 0){
            shootCounter++;
            shootCounter%=shootDelay;
        }
    }
    public void hit(){
        if (hitReg != 1){
            hitReg = 1;
            setBaseSMode("single");
            if (--lives>0){
                spawn = 2;
                hitTimer = 0;
            }
            else{
                spawn = 0;
                hitReg = 0;
            }
        }
    }
    public void hitFlash(){
        if (hitReg == 1){
            hitTimer++;
            switch (hitTimer) {
                case 10: case 30: case 50: case 70:
                    spawn = 3;
                    break;
                case 20: case 40: case 60: case 80:
                    spawn = 2;
                    break;
                case 90:
                    spawn = 1;
                    hitReg = 0;
                    break;
                default:
                    break;
            }
        }
    }
    
    public void inputLeft(){
        if(spawn >= 1){
            if(x-speedX > 0){ x -= speedX; }
        }
    }
    public void inputRight(){
        if(spawn >= 1){
            if(x+width+speedX < ResX){ x += speedX; }
        }
    }
    public void inputUp(){
        if(spawn >= 1){
            if(y-speedY > 0){ y -= speedY; }
        }
    }
    public void inputDown(){
        if(spawn >= 1){
            if(y+height+speedY < ResY){ y += speedY; }
        }
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
    public void setBaseSMode(String sMode){
        this.baseSMode = sMode;
        switch(baseSMode){
            case "single": 
                shootDelay = 10;
                setImage(imgSet[0]);
                break;
            case "singleL":
                shootDelay = 60;
                setImage(imgSet[0]);
                break;
            case "double":
                shootDelay = 10;
                setImage(imgSet[1]);
                break;
            case "triple":
                shootDelay = 10;
                setImage(imgSet[2]);
                break;
            default:
                break;
        }
    }
    public String getBaseSMode(){
        return baseSMode;
    }
    public void setTempSMode(String sMode){
        this.tempSMode = sMode;
        switch(tempSMode){
            case "fire5":
                shootDelay = 10;
                setImage(imgSet[1]);
            default:
                break;
        }
    }
    public String getTempSMode(){
        return tempSMode;
    }
    public void setRes(int width, int height){
        this.ResX = width;
        this.ResY = height;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
    public void addScore(int points){
        score += points;
    }
}