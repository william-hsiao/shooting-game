package shooter;

public class Menu extends Sprite {
    private final String[] imgSet;
    Menu(int x, int y, int speedX, int speedY, String img, String[] imgSet){
        super(x, y, speedX, speedY, img);
        this.imgSet = imgSet;
        centreCoord();
    }
    public void centreCoord(){
        x -= width/2;
        y -= height/2;
    }
    public void updateMenuImg(int i){
        setImage(imgSet[i-1]);
    }
}
