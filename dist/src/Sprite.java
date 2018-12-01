package shooter;

import javax.swing.*;
import java.awt.*;

public class Sprite {
    protected int x, y, width, height, speedX, speedY;
    protected Image img;
    
    Sprite(int x, int y, int speedX, int speedY, String img){
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        setImage(img);
    }
    
    public void drawImg(Graphics g){
        g.drawImage(img,x,y,null);
    }
    public void setImage(String imgDir){
        ImageIcon ico = new ImageIcon(imgDir);
        img = ico.getImage();
        width = img.getWidth(null);
        height = img.getHeight(null);
    }
    public void centreMX(){
        x -= width/2;
    }
    public void moveX(){
        x += speedX;
    }
    public void moveY(){
        y += speedY;
    }
    public int getXWidth(){
        return (x+width);
    }
    public int getYHeight(){
        return (y+height);
    }
    
    public void setX(int x) {
        this.x = x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY(){
        return y;
    }
    public void setSpeed(int x, int y){
        this.speedX = x;
        this.speedY = y;
    }
    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }
    public int getSpeedX(){
        return speedX;
    }
    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }
    public int getSpeedY(){
        return speedY;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

}
